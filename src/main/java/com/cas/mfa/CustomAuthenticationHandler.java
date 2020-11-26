package com.cas.mfa;

import java.security.GeneralSecurityException;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {

	public CustomAuthenticationHandler() {
		super(null, null, null, null);
	}

	@Override
	protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential)
			throws GeneralSecurityException, PreventedException {

		System.out.println("二次验证");

		Authentication authentication = WebUtils.getInProgressAuthentication();
		return this.createHandlerResult(credential, authentication.getPrincipal());
	}

	@Override
	public boolean supports(Credential credential) {
		return credential instanceof CustomCredential;
	}
}
