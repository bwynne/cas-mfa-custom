package com.cas.mfa;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.services.MultifactorAuthenticationProvider;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.FlowDefinitionRegistryBuilder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Configuration
public class CustomConfig implements AuthenticationEventExecutionPlanConfigurer, CasWebflowExecutionPlanConfigurer {

	@Autowired
	private CasConfigurationProperties casProperties;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier("loginFlowRegistry")
	private FlowDefinitionRegistry loginFlowDefinitionRegistry;

	@Autowired
	private FlowBuilderServices flowBuilderServices;

	@Autowired
	@Qualifier("servicesManager")
	private ServicesManager servicesManager;

	@Bean
	public MultifactorAuthenticationProvider customAuthenticationProvider() {
		CustomMultifactorAuthenticationProvider p = new CustomMultifactorAuthenticationProvider();
		p.setBypassEvaluator(MultifactorAuthenticationUtils
				.newMultifactorAuthenticationProviderBypass(new MultifactorAuthenticationProviderBypassProperties()));
		p.setId("mfa-custom");
		return p;
	}

	@Bean
	public FlowDefinitionRegistry customFlowRegistry() {
		final FlowDefinitionRegistryBuilder builder = new FlowDefinitionRegistryBuilder(this.applicationContext,
				this.flowBuilderServices);
		builder.setBasePath("classpath*:/webflow");
		builder.addFlowLocationPattern("/mfa-custom/*-webflow.xml");
		return builder.build();
	}

	@Bean
	public CasWebflowConfigurer customWebflowConfigurer() {
		return new CustomMultifactorWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry,
				customFlowRegistry(), applicationContext, casProperties);
	}

	@Override
	public void configureWebflowExecutionPlan(CasWebflowExecutionPlan plan) {
		plan.registerWebflowConfigurer(customWebflowConfigurer());
	}

	@Bean
	public AuthenticationHandler customAuthenticatorAuthenticationHandler() {
		return new CustomAuthenticatorAuthenticationHandler("mfa-custom", servicesManager,
				new DefaultPrincipalFactory());
	}

	@Override
	public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
		plan.registerAuthenticationHandler(customAuthenticatorAuthenticationHandler());
	}
}
