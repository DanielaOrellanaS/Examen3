package com.banquito.core.productsaccounts.controller;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.banquito.core.productsaccounts.controller.dto.InterestRateRQRS;
import com.banquito.core.productsaccounts.controller.mapper.InterestRateMapper;
import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.mock.InterestRateMock;
import com.banquito.core.productsaccounts.model.InterestRate;
import com.banquito.core.productsaccounts.service.InterestRateService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InterestRateControllerTest {

    @Mock
    private InterestRateService interestRateService;

    @InjectMocks
    private InterestRateController interestRateController;


    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtainAll() {
        List<InterestRate> listInterestRate = InterestRateMock.mockListOfInterestRate();

        when(interestRateService.listAllActives()).thenReturn(listInterestRate);
        ResponseEntity<List<InterestRateRQRS>> response = interestRateController.obtainAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testObtainByCode() {
        InterestRate interestRate = InterestRateMock.mockInterestRate();

        when(interestRateService.obtainById(any(Integer.class))).thenReturn(interestRate);
        ResponseEntity<InterestRateRQRS> response = interestRateController.obtainByCode("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    
    }

    @Test
    void testObtainByCodeWithInvalidCode() {
        when(interestRateService.obtainById(any(Integer.class))).thenReturn(null);
        ResponseEntity<InterestRateRQRS> response = interestRateController.obtainByCode("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testCreate() {
        Mockito.doNothing().when(interestRateService).create(Mockito.any(InterestRate.class));
        ResponseEntity<?> response = this.interestRateController.create(InterestRateMapper.mapToInterestRateRQRS(InterestRateMock.mockInterestRate()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
     }

    @Test
    public void testCreateWithException() throws CRUDException {
        Mockito.doThrow(CRUDException.class).when(interestRateService).create(Mockito.any(InterestRate.class));
        ResponseEntity<?> response = this.interestRateController.create(InterestRateMapper.mapToInterestRateRQRS(InterestRateMock.mockInterestRate()));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
     }

     
    @Test
    void testUpdate() {
        Mockito.doNothing().when(interestRateService).update(any(Integer.class), any(InterestRate.class));
        when(interestRateService.obtainById(any(Integer.class))).thenReturn(InterestRateMock.mockInterestRate());
        ResponseEntity<?> response = this.interestRateController.update("1", InterestRateMapper.mapToInterestRateRQRS(InterestRateMock.mockInterestRate()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateThrowException(){
        Mockito.doThrow(new CRUDException(500)).when(interestRateService).update(any(Integer.class), any(InterestRate.class));
        when(interestRateService.obtainById(any(Integer.class))).thenReturn(InterestRateMock.mockInterestRate());
        ResponseEntity<?> response = this.interestRateController.update("1", InterestRateMapper.mapToInterestRateRQRS(InterestRateMock.mockInterestRate()));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDelete() {
        Mockito.doNothing().when(interestRateService).inactivate(any(Integer.class));
        ResponseEntity<?> response = this.interestRateController.delete("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteThrowException() {
        Mockito.doThrow(new CRUDException(500)).when(interestRateService).inactivate(any(Integer.class));
        ResponseEntity<?> response = this.interestRateController.delete("1");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
