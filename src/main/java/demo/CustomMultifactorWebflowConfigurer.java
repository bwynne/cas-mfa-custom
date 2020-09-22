package demo;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.configurer.AbstractCasMultifactorWebflowConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

public class CustomMultifactorWebflowConfigurer extends AbstractCasMultifactorWebflowConfigurer {

	public static final String MFA_CUSTOM_EVENT_ID = "mfa-custom";

	private final FlowDefinitionRegistry flowDefinitionRegistry;

	public CustomMultifactorWebflowConfigurer(final FlowBuilderServices flowBuilderServices,
			final FlowDefinitionRegistry loginFlowDefinitionRegistry,
			final FlowDefinitionRegistry flowDefinitionRegistry, final ApplicationContext applicationContext,
			final CasConfigurationProperties casProperties) {
		super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
		this.flowDefinitionRegistry = flowDefinitionRegistry;
	}

	@Override
	protected void doInitialize() {
		registerMultifactorProviderAuthenticationWebflow(getLoginFlow(), MFA_CUSTOM_EVENT_ID,
				this.flowDefinitionRegistry, MFA_CUSTOM_EVENT_ID);
	}
}
