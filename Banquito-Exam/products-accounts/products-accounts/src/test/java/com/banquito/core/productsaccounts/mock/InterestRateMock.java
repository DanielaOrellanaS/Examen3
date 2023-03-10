package com.banquito.core.productsaccounts.mock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.banquito.core.productsaccounts.model.InterestRate;

public class InterestRateMock {
    public static InterestRate mockInterestRate() {
        return InterestRate.builder()
                .id(1)
                .name("nombre")
                .interestRate(new BigDecimal(0.0))
                .state("N")
                .start(new Date())
                .end(new Date())
                .build();
    }

    public static List<InterestRate> mockListOfInterestRate() {
        return List.of(
                mockInterestRate(),
                mockInterestRate(),
                mockInterestRate());
    }
}
