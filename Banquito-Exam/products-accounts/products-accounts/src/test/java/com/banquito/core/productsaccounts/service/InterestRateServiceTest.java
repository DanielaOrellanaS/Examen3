package com.banquito.core.productsaccounts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.mock.InterestRateMock;
import com.banquito.core.productsaccounts.model.InterestRate;
import com.banquito.core.productsaccounts.repository.InterestRateRepository;

public class InterestRateServiceTest {

    @Mock
    private InterestRateRepository interestRateRepository;

    @InjectMocks
    private InterestRateService interestRateService;

    @BeforeEach
    void setUp() {
        interestRateRepository = mock(InterestRateRepository.class);
        interestRateService = new InterestRateService(interestRateRepository);
    }

    @Test
    void testListAllActives() {
        List<InterestRate> listOfAllActives = InterestRateMock.mockListOfInterestRate();
        List<InterestRate> listExpect = InterestRateMock.mockListOfInterestRate();

        when(interestRateRepository.findByState(any(String.class))).thenReturn(listOfAllActives);

        List<InterestRate> result = interestRateService.listAllActives();
        assertEquals(listExpect, result);
    }

    @Test
    void testCreate() {
        ArgumentCaptor<InterestRate> argument = ArgumentCaptor.forClass(InterestRate.class);
        InterestRate mockInterestRate = InterestRateMock.mockInterestRate();
        when(interestRateRepository.save(any(InterestRate.class))).thenReturn(null);
        interestRateService.create(mockInterestRate);

        Mockito.verify(this.interestRateRepository).save(argument.capture());
        assertEquals(mockInterestRate, argument.getValue());
    }

    @Test
    void testCreateThrowException() {
        InterestRate mockInterestRate = InterestRateMock.mockInterestRate();
        when(interestRateRepository.save(any(InterestRate.class))).thenThrow(CRUDException.class);
        assertThrows(CRUDException.class, () -> this.interestRateService.create(mockInterestRate));
    }

    @Test
    void testInactivate() {
        InterestRate interestRate = InterestRateMock.mockInterestRate();
        ArgumentCaptor<InterestRate> argument = ArgumentCaptor.forClass(InterestRate.class);
        when(interestRateRepository.findById(any(Integer.class))).thenReturn(Optional.of(interestRate));
        when(this.interestRateRepository.save(any(InterestRate.class))).thenReturn(null);
        interestRateService.inactivate(1);

        
        interestRate.setState("INA");
        Mockito.verify(this.interestRateRepository).save(argument.capture());
        assertEquals(interestRate.getState(), argument.getValue().getState());
    }

    @Test
    void testInactivateThrowException() {
        when(interestRateRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(CRUDException.class, () -> interestRateService.inactivate(1));
    }

    @Test
    void testObtainById() {
        InterestRate expect = InterestRateMock.mockInterestRate();
        InterestRate mock = InterestRateMock.mockInterestRate();
        
        when(interestRateRepository.findById(any(Integer.class))).thenReturn(Optional.of(mock));

        InterestRate actual = interestRateService.obtainById(1);
        assertEquals(expect, actual);
    }

    @Test
    void testObtainByIdThrowException() {
        when(interestRateRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        assertThrows(CRUDException.class, () -> interestRateService.obtainById(1));
    }

    @Test
    public void testUpdate() throws Exception {
        ArgumentCaptor<InterestRate> argument = ArgumentCaptor.forClass(InterestRate.class); 
        InterestRate expect = InterestRateMock.mockInterestRate();
        InterestRate mock = InterestRateMock.mockInterestRate();
        
        when(interestRateRepository.findById(any(Integer.class))).thenReturn(Optional.of(mock));
        when(interestRateRepository.save(any(InterestRate.class))).thenReturn(null);

        expect.setName("new expect");
        interestRateService.update(1, expect);
        Mockito.verify(interestRateRepository).save(argument.capture());

        assertEquals(expect, argument.getValue());
    }

    @Test
    public void testUpdateThrowException() throws Exception {
        InterestRate expect = InterestRateMock.mockInterestRate();
        
        when(interestRateRepository.findById(any(Integer.class))).thenReturn(Optional.empty());

        expect.setName("new expect");
        assertThrows(CRUDException.class, () -> interestRateService.update(1, expect));
    }

}
