package com.cts.bms.applyloan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.bms.applyloan.config.ApplyLoanProducer;
import com.cts.bms.applyloan.config.RetrieveLoanProducer;
import com.cts.bms.applyloan.model.Loan;
import com.cts.bms.applyloan.repository.LoanRepository;
import com.cts.bms.applyloan.restclient.LoginClient;
import com.cts.bms.applyloan.service.LoanDaoImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LoanDaoImplTest {
	@InjectMocks
	private LoanDaoImpl loanDaoImpl;

	@Mock
	private LoanRepository loanRepository;

	@Mock
	ApplyLoanProducer applyLoanProducer;

	@Mock
	RetrieveLoanProducer retrieveLoanProducer;

	@Mock
	LoginClient loginClient;

	@Test
	public void testSavePersonalLoanDetails() {

		Loan loan = new Loan();
		loan.setLoanType("Personal");
		
		loanDaoImpl.saveLoanDetails(loan);
		
	}

	@Test
	public void testSaveEducationalLoanDetails() {

		Loan loan = new Loan();
		loan.setLoanType("Educational");
		
		loanDaoImpl.saveLoanDetails(loan);
		
	}
	@Test
	public void testSaveLoanFailure() {
		
		loanDaoImpl.saveLoanDetails(null);
	}
	
	@Test
	public void testGetAllLoansByAccountIdSuccess() {

		List<Loan> loanList = new ArrayList<>();
		loanList.add(new Loan());
		Mockito.when(loanRepository.getAllLoansByAccountId((Mockito.anyInt()))).thenReturn(loanList);
		assertEquals(1, loanDaoImpl.getAllLoansByAccountId(Mockito.anyInt()).size());

	}

	@Test
	public void testgetAllLoansByAccountIdFailure() {
		RetrieveLoanProducer retrieveLoanProducer = Mockito.mock(RetrieveLoanProducer.class);

		Mockito.when(loanRepository.getAllLoansByAccountId(Mockito.anyInt())).thenThrow(new RuntimeException());
		assertNull(loanDaoImpl.getAllLoansByAccountId(Mockito.anyInt()));
	}

	@Test
	public void testValidateTokenSuccess() {

		Mockito.when(loginClient.validateToken(Mockito.anyString()))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		assertTrue(loanDaoImpl.validateToken(Mockito.anyString()));
	}

	@Test
	public void testValidateTokenFailure() {

		Mockito.when(loginClient.validateToken(Mockito.anyString()))
				.thenReturn(new ResponseEntity<>(HttpStatus.FORBIDDEN));
		assertFalse(loanDaoImpl.validateToken(Mockito.anyString()));
	}

	@Test
	public void testValidateTokenFailureNull() {

		assertFalse(loanDaoImpl.validateToken(null));
	}

	@Test
	public void testValidateTokenFailureException() {

		Mockito.when(loginClient.validateToken(Mockito.anyString())).thenThrow(new RuntimeException());
		assertFalse(loanDaoImpl.validateToken(Mockito.anyString()));
	}

}
