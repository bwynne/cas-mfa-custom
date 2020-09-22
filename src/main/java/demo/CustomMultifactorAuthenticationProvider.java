package demo;

import org.apereo.cas.authentication.AbstractMultifactorAuthenticationProvider;

public class CustomMultifactorAuthenticationProvider extends AbstractMultifactorAuthenticationProvider {

	private static final long serialVersionUID = 1L;

	@Override
	public String getFriendlyName() {
		return "Custom MFA";
	}
}
