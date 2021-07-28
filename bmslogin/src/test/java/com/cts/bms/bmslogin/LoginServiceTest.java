package com.cts.bms.bmslogin;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.bms.bmslogin.mdel.UserLoginDetails;
import com.cts.bms.bmslogin.repository.UserLoginDetailsRepository;
import com.cts.bms.bmslogin.service.LoginService;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
	@InjectMocks
	private LoginService loginService;

	@Mock
	private UserLoginDetailsRepository userLoginDetailsRepository;

	@Test
	public void loadByUsernameSuccess() {
		Mockito.when( userLoginDetailsRepository.findById(Mockito.anyString()))
				.thenReturn(Optional.of(new UserLoginDetails("test", "test", null)));
		assertNotNull(loginService.loadUserByUsername(Mockito.anyString()));
	}

	@Test
	public void loadByUsernameFailure() {
		Mockito.when(userLoginDetailsRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		assertNull(loginService.loadUserByUsername(Mockito.anyString()));
	}

	@Test(expected = RuntimeException.class)
	public void loadByUsernameException() {
		Mockito.when(userLoginDetailsRepository.findById(Mockito.anyString())).thenThrow(new RuntimeException());
		loginService.loadUserByUsername(Mockito.anyString());
	}

	@Test
	public void saveCredentialsSuccess() {
		UserLoginDetails userLoginDetails = new UserLoginDetails("test", "test", null);
		Mockito.when(userLoginDetailsRepository.save(userLoginDetails)).thenReturn(userLoginDetails);
		assertTrue(loginService.saveCredentials(userLoginDetails));
	}

	@Test
	public void saveCredentialsFailure() {
		UserLoginDetails userLoginDetails = new UserLoginDetails("test", "test", null);
		Mockito.when(userLoginDetailsRepository.save(userLoginDetails)).thenThrow(new RuntimeException());
		assertFalse(loginService.saveCredentials(userLoginDetails));
	}
}
