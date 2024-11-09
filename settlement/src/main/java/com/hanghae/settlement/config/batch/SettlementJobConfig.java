package com.hanghae.settlement.config.batch;


import com.hanghae.settlement.domain.Settlement;
import com.hanghae.settlement.repository.SettlementRepository;
import com.hanghae.user.domain.User;
import com.hanghae.user.repository.UserRepository;
import com.hanghae.video.domain.Video;
import com.hanghae.video.repository.VideoRepository;
import com.hanghae.video.repository.VideoViewLogRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Configuration
public class SettlementJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final VideoViewLogRepository videoViewLogRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;

    public SettlementJobConfig(JobRepository jobRepository,
                               PlatformTransactionManager platformTransactionManager,
                               VideoViewLogRepository videoViewLogRepository,
                               VideoRepository videoRepository,
                               UserRepository userRepository,
                               SettlementRepository settlementRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.videoViewLogRepository = videoViewLogRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.settlementRepository = settlementRepository;
    }

    @Bean
    public Job settlementJob() {
        return new JobBuilder("settlementJob", jobRepository)
                .start(settlementStep())
                .build();
    }

    @Bean
    public Step settlementStep() {
        return new StepBuilder("settlementStep", jobRepository)
                .<User, Settlement>chunk(1, platformTransactionManager)
                .reader(settlementReader())
                .processor(settlementProcessor())
                .writer(settlementWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<User> settlementReader() {
        return new RepositoryItemReaderBuilder<User>()
                .repository(userRepository)
                .methodName("findAll")
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .name("userReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<User, Settlement> settlementProcessor() {
        return user -> {
            LocalDate settlementDate = LocalDate.now().minusDays(1);

            // 각 유저가 업로드한 비디오의 조회수 총합을 계산
            List<Video> userVideos = videoRepository.findByUserId(user.getId());
            Long videoViewsTotal = userVideos.stream()
                    .mapToLong(video -> videoViewLogRepository.countByVideoIdAndPlayedAtBetween(
                            video.getId(),
                            settlementDate.atStartOfDay(),
                            settlementDate.plusDays(1).atStartOfDay()))
                    .sum();

            // 정산 금액 계산
            double videoEarnings = calculateEarnings(videoViewsTotal);
            double adEarnings = 0.0; // 광고 수익 관련 로직은 추후 추가
            double totalEarnings = videoEarnings + adEarnings;

            return Settlement.builder()
                    .userId(user.getId())
                    .settlementDate(settlementDate)
                    .videoEarnings(videoEarnings)
                    .adEarnings(adEarnings)
                    .build();
        };
    }

    private double calculateEarnings(Long videoViewsTotal) {
        // 예시: 조회수 1건당 0.01달러로 계산
        return videoViewsTotal * 0.01;
    }

    @Bean
    public RepositoryItemWriter<Settlement> settlementWriter() {
        return new RepositoryItemWriterBuilder<Settlement>()
                .repository(settlementRepository)
                .methodName("save")
                .build();
    }
}
