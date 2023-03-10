package com.banquito.core.productsaccounts.mock;

import java.math.BigDecimal;
import java.util.List;

import com.banquito.core.productsaccounts.controller.dto.ProductAccountRQRS;

public class ProductAccountRQRSMock {
    public static ProductAccountRQRS mockProductAccountRQRS(){
        return ProductAccountRQRS.builder()
        .id("1")
        .name("nombre")
        .description("descripcion")
        .minimunBalance(new BigDecimal(0.0))
        .payInterest("N")
        .acceptsChecks("N")
        .state("ACT")
        .build();
    }

    public static List<ProductAccountRQRS> mockListOfProductAccountRQRS(){
        return List.of(
            mockProductAccountRQRS(),
            mockProductAccountRQRS(),
            mockProductAccountRQRS()
        );
    }
}
