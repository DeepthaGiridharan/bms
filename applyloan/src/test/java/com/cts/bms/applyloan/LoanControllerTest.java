package com.cts.bms.applyloan;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.bms.applyloan.controller.LoanController;
import com.cts.bms.applyloan.model.Loan;
import com.cts.bms.applyloan.service.LoanDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class LoanControllerTest {
	@InjectMocks
	private LoanController loanController;
	@Autowired
	private MockMvc mockMvc;
	@Mock
	LoanDao loanService;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();

	}

	@Test
	public void testGetAllLoans() throws Exception {
		Mockito.when(loanService.getAllLoansByAccountId(Mockito.anyInt())).thenReturn(new ArrayList<>());
		ResultActions actions = mockMvc.perform(get("/apply_loan/get_loans?accountId=1000"));
		actions.andExpect(status().isOk());

	}

	@Test
	public void testGetAllLoansByAccountIdSuccess() throws Exception {
		Mockito.when(loanService.getAllLoansByAccountId((Mockito.anyInt()))).thenReturn(new ArrayList<>());
		Mockito.when(loanService.validateToken(Mockito.anyString())).thenReturn(true);
		ResultActions actions = mockMvc
				.perform(get("/apply_loan/get_account_loans?accountId=1000").header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isOk());

	}

	@Test
	public void testGetAllLoansByAccountIdFailure() throws Exception {
		Mockito.when(loanService.getAllLoansByAccountId(Mockito.anyInt())).thenReturn(new ArrayList<>());
		Mockito.when(loanService.validateToken(Mockito.anyString())).thenReturn(false);
		ResultActions actions = mockMvc
				.perform(get("/apply_loan/get_account_loans?accountId=1").header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isForbidden());

	}

	@Test
	public void testSaveLoanDetailsSuccess() throws Exception {
		Loan loan = new Loan();
		doNothing().when(loanService).saveLoanDetails(loan);
		Mockito.when(loanService.validateToken(Mockito.anyString())).thenReturn(true);
		ResultActions actions = mockMvc.perform(post("/apply_loan/save_loan_details").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(loan)).header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isOk());

	}

	@Test
	public void testSaveLoanDetailsailure() throws Exception {
		Loan loan = new Loan();
		doNothing().when(loanService).saveLoanDetails(loan);
		Mockito.when(loanService.validateToken(Mockito.anyString())).thenReturn(false);
		ResultActions actions = mockMvc.perform(post("/apply_loan/save_loan_details").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(loan)).header("Authorization", Mockito.anyString()));
		actions.andExpect(status().isForbidden());

	}

	public static String asJsonString(Loan loan) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(loan);

	}
}
