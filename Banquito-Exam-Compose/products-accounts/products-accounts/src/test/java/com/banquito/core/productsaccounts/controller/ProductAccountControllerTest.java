package com.banquito.core.productsaccounts.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.banquito.core.productsaccounts.controller.dto.ProductAccountRQRS;
import com.banquito.core.productsaccounts.controller.mapper.ProductAccountMapper;
import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.mock.ProductAccountMock;
import com.banquito.core.productsaccounts.model.ProductAccount;
import com.banquito.core.productsaccounts.service.ProductAccountService;


public class ProductAccountControllerTest {


    @Mock
    private ProductAccountService productAccountService;

    @InjectMocks
    private ProductAccountController productAccountcontroller;

    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtainAll() throws Exception {
        List<ProductAccount> listProductAccount = ProductAccountMock.mockListOfProductAccount();

        when(productAccountService.listAllActives()).thenReturn(listProductAccount);
        ResponseEntity<List<ProductAccountRQRS>> response = productAccountcontroller.obtainAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testObtainByCode() {
        ProductAccount productAccount = ProductAccountMock.mockProductAccount();

        when(productAccountService.obtainById(any(String.class))).thenReturn(productAccount);
        ResponseEntity<ProductAccountRQRS> response = productAccountcontroller.obtainByCode("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testObtainByCodeThrowException() {
        when(productAccountService.obtainById(any(String.class))).thenReturn(null);
        ResponseEntity<ProductAccountRQRS> response = productAccountcontroller.obtainByCode("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate() {
        Mockito.doNothing().when(productAccountService).create(Mockito.any(ProductAccount.class));
        ResponseEntity<?> response = this.productAccountcontroller.create(ProductAccountMapper.mapToProductAccountRQRS(ProductAccountMock.mockProductAccount()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateWithException() throws CRUDException {
        Mockito.doThrow(CRUDException.class).when(productAccountService).create(Mockito.any(ProductAccount.class));
        ResponseEntity<?> response = this.productAccountcontroller.create(ProductAccountMapper.mapToProductAccountRQRS(ProductAccountMock.mockProductAccount()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
