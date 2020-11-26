package com.cas.mfa;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.configurer.AbstractCasMultifactorWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Component
public class CustomMultifactorWebflowConfigurer extends AbstractCasMultifactorWebflowConfigurer {

	@Autowired
	public CustomMultifactorWebflowConfigurer(FlowBuilderServices flowBuilderServices,
			@Qualifier("loginFlowRegistry") FlowDefinitionRegistry loginFlowDefinitionRegistry,
			ApplicationContext applicationContext, CasConfigurationProperties casProperties) {
		super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
	}

	@Override
	protected void doInitialize() {
		FlowDefinitionRegistryBuilder builder = new FlowDefinitionRegistryBuilder(this.applicationContext,
				this.flowBuilderServices);
		builder.setBasePath("classpath*:/webflow");
		builder.addFlowLocationPattern("/mfa-custom/*-webflow.xml");
		registerMultifactorProviderAuthenticationWebflow(getLoginFlow(), "mfa-custom", builder.build(), "mfa-custom");
	}
}
