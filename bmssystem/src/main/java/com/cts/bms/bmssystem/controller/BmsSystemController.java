package com.cts.bms.bmssystem.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.bms.bmssystem.model.Customer;
import com.cts.bms.bmssystem.model.Loan;
import com.cts.bms.bmssystem.model.UserLoginDetails;
import com.cts.bms.bmssystem.service.BmsSystemService;

@Controller
@RequestMapping("/bmssystem")
public class BmsSystemController {
	@Autowired
	BmsSystemService bmsSystemService;

	@Value("${spring.kafka.topic.registerCustomer}")
	String REGISTER_CUSTOMER_TOPIC;

	@Value("${spring.kafka.topic.updateCustomer}")
	String UPDATE_CUSTOMER_TOPIC;

	@Value("${spring.kafka.topic.applyLoan}")
	String APPLY_LOAN_TOPIC;

	@Value("${spring.kafka.topic.login}")
	String LOGIN_TOPIC;

	@Value("${spring.kafka.topic.validateToken}")
	String VALIDATE_TOKEN_TOPIC;
	@PostMapping("/save_customer_details")
	public String register(@RequestBody Customer customer, ModelMap model) {

		bmsSystemService.registerCustomer(REGISTER_CUSTOMER_TOPIC, customer);
		model.addAttribute("registrationMessage",
				bmsSystemService.getMessage());
		return "registered-details";
	}
	@PostMapping("/update_customer_details")
	public String update(@RequestBody Customer customer, HttpSession session, ModelMap model) {
		System.out.println((String) session.getAttribute("TOKEN"));
		if ((session == null) || (!bmsSystemService.validateToken((String) session.getAttribute("TOKEN"))))
			return "redirect:/bmssystem/logout";
		try {
			bmsSystemService.updateCustomer(UPDATE_CUSTOMER_TOPIC, customer);
			model.addAttribute("updateMessage", bmsSystemService.getMessage());
			return "updated";
		} catch (Exception e) {
			return "error";
		}
	}
	@PostMapping("/save_loan_details")
	public String apply(@RequestBody Loan loan, HttpSession session, ModelMap model) {
		if ((session == null) || (!bmsSystemService.validateToken((String) session.getAttribute("TOKEN"))))
			return "redirect:/bmssystem/logout";
		try {
			bmsSystemService.applyLoan(APPLY_LOAN_TOPIC, loan);
			model.addAttribute("applyLoanMessage",bmsSystemService.getMessage());
			return "loan-applied";
		} catch (Exception e) {
			return "error";
		}
	}
	@GetMapping("/get_account_loans")
	public String retrieve(@RequestParam Integer accountNumber, HttpSession session, ModelMap model) {
		if ((session == null) || (!bmsSystemService.validateToken((String) session.getAttribute("TOKEN"))))
			return "redirect:/bmssystem/logout";

		bmsSystemService.retrieveLoans(accountNumber);
		model.addAttribute("loanHistory", bmsSystemService.returnLoans());
		return "loan-history";
	}
	@GetMapping("/get_loans")
	public List<Loan> getLoans() {
		return bmsSystemService.returnLoans();

	}

	@PostMapping("/login")
	public String login(@RequestBody UserLoginDetails userLoginDetails, HttpSession session, ModelMap warning) {
		if (bmsSystemService.login(userLoginDetails, session, warning))
			return "home";
		else
			return "login";

	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

	@GetMapping("/getLogin")
	public String getLoginPage(HttpSession session) {
		return "login";
	}

	@GetMapping("/getRegistrationForm")
	public String getRegistrationForm() {
		return "registration";
	}

	@GetMapping("/getUpdateForm")
	public String getUpdateForm(HttpSession session) {
		if ((session == null) || (!bmsSystemService.validateToken((String) session.getAttribute("TOKEN"))))
			return "redirect:/bmssystem/logout";
		return "update";
	}

	@GetMapping("/getLoanApplication")
	public String getLoanApplication(HttpSession session) {
		if ((session == null) || (!bmsSystemService.validateToken((String) session.getAttribute("TOKEN"))))
			return "redirect:/bmssystem/logout";
		return "apply-loan";
	}

	@GetMapping("/home")
	public String getHome(HttpSession session) {
		System.out.println(session.getAttribute("TOKEN"));
		if ((session == null) || (!bmsSystemService.validateToken((String) session.getAttribute("TOKEN"))))
			return "redirect:/bmssystem/logout";
		return "home";
	}

	
}
