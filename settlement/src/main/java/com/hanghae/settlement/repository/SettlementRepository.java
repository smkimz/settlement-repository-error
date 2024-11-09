package com.hanghae.settlement.repository;

import com.hanghae.settlement.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    Optional<Settlement> findByUserIdAndSettlementDate(Long userId, LocalDate settlementDate);

}
