package com.cas.mfa;

import java.security.GeneralSecurityException;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.support.WebUtils;

public class CustomAuthenticatorAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {

	public CustomAuthenticatorAuthenticationHandler(String name, ServicesManager servicesManager,
			PrincipalFactory principalFactory) {
		super(name, servicesManager, principalFactory, null);
	}

	@Override
	protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential)
			throws GeneralSecurityException, PreventedException {

		System.out.println("二次验证");

		final Authentication authentication = WebUtils.getInProgressAuthentication();
		final String uid = authentication.getPrincipal().getId();
		return this.createHandlerResult(credential, this.principalFactory.createPrincipal(uid));
	}

	@Override
	public boolean supports(Credential credential) {
		return CustomAuthenticatorTokenCredential.class.isAssignableFrom(credential.getClass());
	}
}
