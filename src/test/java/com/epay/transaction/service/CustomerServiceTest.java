/*
package com.epay.transaction.service;

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.util.util.EncryptionDecryptionUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static  org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @Mock
    private EncryptionDecryptionUtil encryptionDecryptionUtil;

    @Mock
    private ObjectMapper objectMapper;


    @InjectMocks
    private CustomerService customerService;

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCustomerByCustomerId_Success() {
     String customerID ="123";
        CustomerDto customerDto = mock(CustomerDto.class);
        MerchantDto merchantDto  = mock(MerchantDto.class);
        String encryptedResponse = "encryptedResponse";
        when(customerDao.getCustomerByCustomerId(customerID)).thenReturn(Optional.of(customerDto));
        when(customerDao.getActiveMerchantByMID(customerID)).thenReturn(Optional.of(merchantDto));
        //when(ObjectMapper.co

    }

}


*/
