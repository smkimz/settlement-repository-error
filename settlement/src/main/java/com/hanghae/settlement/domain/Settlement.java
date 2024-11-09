package com.hanghae.settlement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate settlementDate;

    private Double videoEarnings; // 동영상 정산 금액

    private Double adEarnings;     // 광고 정산 금액

    private Double totalEarnings;  // 총 정산 금액

    public Settlement(Long userId, LocalDate settlementDate, Double videoEarnings, Double adEarnings) {
        this.userId = userId;
        this.settlementDate = settlementDate;
        this.videoEarnings = videoEarnings;
        this.adEarnings = adEarnings;
        this.totalEarnings = videoEarnings + adEarnings;
    }

    public static SettlementBuilder builder() {
        return new SettlementBuilder();
    }

    public static class SettlementBuilder {
        private Long userId;
        private LocalDate settlementDate;
        private Double videoEarnings;
        private Double adEarnings;

        public SettlementBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public SettlementBuilder settlementDate(LocalDate settlementDate) {
            this.settlementDate = settlementDate;
            return this;
        }

        public SettlementBuilder videoEarnings(Double videoEarnings) {
            this.videoEarnings = videoEarnings;
            return this;
        }

        public SettlementBuilder adEarnings(Double adEarnings) {
            this.adEarnings = adEarnings;
            return this;
        }

        public Settlement build() {
            Settlement settlement = new Settlement();
            settlement.userId = this.userId;
            settlement.settlementDate = this.settlementDate;
            settlement.videoEarnings = this.videoEarnings;
            settlement.adEarnings = this.adEarnings;
            settlement.totalEarnings = this.videoEarnings + this.adEarnings;
            return settlement;
        }
    }
}