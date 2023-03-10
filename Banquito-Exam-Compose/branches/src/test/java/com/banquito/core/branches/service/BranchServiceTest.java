package com.banquito.core.branches.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;

public class BranchServiceTest {

    private BranchService branchService;

    @Mock
    private Logger logger;

    @Mock
    private BranchRepository branchRepositoryMock;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        branchService = new BranchService(branchRepositoryMock);
    }

    @Test
    void testCreateValid() throws CRUDException {
        Branch branch = new Branch();
        branch.setCode("111");
        branch.setName("Branch1");
        when(branchRepositoryMock.save(branch)).thenReturn(branch);
        branchService.create(branch);
        verify(branchRepositoryMock, times(1)).save(branch);

    }

    @Test
    void testCreateInvalid() throws CRUDException {
        Branch branch = new Branch();
        branch.setCode("111");
        branch.setName("Branch1");
        when(branchRepositoryMock.save(null)).thenReturn(null);

        assertThrows(CRUDException.class, () -> {
            branchService.create(null);
        });
        // verify(branchRepositoryMock, times(0)).save(branch);
        // verify(branchRepositoryMock, times(1)).save(branch);
    }

    @Test
    void testGetAll() {
        List<Branch> branches = new ArrayList<Branch>();
        Branch branch1 = new Branch();
        branch1.setCode("111");
        branch1.setName("Branch1");
        branches.add(branch1);
        Branch branch2 = new Branch();
        branch2.setCode("222");
        branch2.setName("Branch2");
        branches.add(branch2);
        when(branchRepositoryMock.findAll()).thenReturn(branches);

        List<Branch> result = branchService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("111", result.get(0).getCode());
        assertEquals("222", result.get(1).getCode());
        verify(branchRepositoryMock, times(1)).findAll();
    }

    @Test
    void testLookByCode() {
        String code = "111";
        Branch branch = new Branch();
        branch.setCode("111");
        branch.setName("Branch1");
        when(branchRepositoryMock.findByCode(code)).thenReturn(branch);

        Branch result = branchService.lookByCode(code);

        assertNotNull(result);
        assertEquals("111", result.getCode());
        assertEquals("Branch1", result.getName());
        verify(branchRepositoryMock, times(1)).findByCode(code);
    }

    @Test
    void testLookByIdWithValidId() throws CRUDException {
        String id = "111";
        Optional<Branch> branch = Optional.of(new Branch());
        branch.get().setId(id);
        branch.get().setCode("222");
        branch.get().setName("Branch1");
        when(branchRepositoryMock.findById(id)).thenReturn(branch);

        Branch result = branchService.lookById(id);

        assertNotNull(result);
        assertEquals("222", result.getCode());
        assertEquals("Branch1", result.getName());

        verify(branchRepositoryMock, times(1)).findById(id);
    }

    @Test
    void testLookByIdWithInvalidId() throws CRUDException {
        String id = "111";
        Optional<Branch> branch = Optional.empty();
        when(branchRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(CRUDException.class, () -> {
            branchService.lookById(id);
        });
    }

    @Test
    void testUpdate() throws CRUDException {
        ArgumentCaptor<Branch> argument = ArgumentCaptor.forClass(Branch.class);

        String name = "branch2";
        String code = "222";
        Branch branch = new Branch();

        branch.setCode("222");
        branch.setName("branch2");
        when(branchRepositoryMock.findByCode(any(String.class))).thenReturn(branch);

        branchService.update(code, branch);
        verify(branchRepositoryMock).save(argument.capture());
        assertEquals(name, argument.getValue().getName());

    }

    @Test
    void testUpdateInvalidId() throws CRUDException {
        String code = "001";
        Branch branch = new Branch();
        branch.setCode(code);
        branch.setName("Branch 1");
        Mockito.lenient().when(branchRepositoryMock.findById(code)).thenReturn(Optional.empty());

        assertThrows(CRUDException.class, () -> {
            branchService.update(code, branch);
        });
    }
}
