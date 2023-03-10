package com.banquito.core.productsaccounts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.mock.ProductAccountMock;
import com.banquito.core.productsaccounts.model.ProductAccount;
import com.banquito.core.productsaccounts.repository.ProductAccountRepository;

public class ProductAccountServiceTest {

    @Mock
    private ProductAccountRepository productAccountRepository;

    @InjectMocks
    private ProductAccountService productAccountService;

    @BeforeEach
    void setUp() {
        productAccountRepository = mock(ProductAccountRepository.class);
        productAccountService = new ProductAccountService(productAccountRepository);
    }

    
    @Test
    void testListAllActives() {
       
        List<ProductAccount> listOfAllActives = ProductAccountMock.mockListOfProductAccount();
        List<ProductAccount> listExpect = ProductAccountMock.mockListOfProductAccount();

        when(productAccountRepository.findByState(any(String.class))).thenReturn(listOfAllActives);

        List<ProductAccount> result = productAccountService.listAllActives();
        assertEquals(listExpect, result);
    }

    
    @Test
    void testObtainById() {
        ProductAccount expect = ProductAccountMock.mockProductAccount();
        ProductAccount mock = ProductAccountMock.mockProductAccount();
        
        when(productAccountRepository.findById(any(String.class))).thenReturn(Optional.of(mock));

        ProductAccount actual = productAccountService.obtainById("1");
        assertEquals(expect, actual);
    }

    @Test
    void testObtainByIdThrowException() {
        when(productAccountRepository.findById(any(String.class))).thenReturn(Optional.empty());
        assertThrows(CRUDException.class, () -> productAccountService.obtainById("1"));
    }

    @Test
    void testCreate() {
        ArgumentCaptor<ProductAccount> argument = ArgumentCaptor.forClass(ProductAccount.class);
        ProductAccount mockProductAccount = ProductAccountMock.mockProductAccount();
        when(productAccountRepository.save(any(ProductAccount.class))).thenReturn(null);
        productAccountService.create(mockProductAccount);

        Mockito.verify(this.productAccountRepository).save(argument.capture());
        assertEquals(mockProductAccount, argument.getValue());
    }

    @Test
    void testCreateThrowException() {
        when(productAccountRepository.save(any(ProductAccount.class))).thenReturn(null);
        assertThrows(CRUDException.class, () -> productAccountService.create(null));
    }
    
}
