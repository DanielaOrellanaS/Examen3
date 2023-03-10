package com.banquito.core.productsaccounts.mock;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.banquito.core.productsaccounts.model.ProductAccount;

public class ProductAccountMock {
    public static ProductAccount mockProductAccount() {
        return ProductAccount.builder()
                .id("1")
                .name("name")
                .description("description")
                .minimunBalance(new BigDecimal(0.0))
                .payInterest("N")
                .acceptsChecks("N")
                .state("ACT")
                .creationDate(new Date())
                .build();
    }

    public static List<ProductAccount> mockListOfProductAccount(){
        return List.of(
            mockProductAccount(),
            mockProductAccount(),
            mockProductAccount()
        );
    }
}
