package com.banquito.core.productsaccounts.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestRateRQRS {
    
    private Integer id;
    private String name;
    private BigDecimal interestRate;
    private String state;
    private Date start;
    private Date end;
}
