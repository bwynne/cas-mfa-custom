package com.cas.mfa;

import org.apereo.cas.authentication.AbstractMultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.springframework.stereotype.Component;

@Component
public class CustomMultifactorAuthenticationProvider extends AbstractMultifactorAuthenticationProvider {

	private static final long serialVersionUID = 1L;

	public CustomMultifactorAuthenticationProvider() {
		this.setId("mfa-custom");
		this.setBypassEvaluator(MultifactorAuthenticationUtils
				.newMultifactorAuthenticationProviderBypass(new MultifactorAuthenticationProviderBypassProperties()));
	}

	@Override
	public String getFriendlyName() {
		return "Custom MFA";
	}
}
