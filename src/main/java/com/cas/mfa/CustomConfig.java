package com.cas.mfa;

import javax.annotation.Resource;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.cas.mfa")
public class CustomConfig implements AuthenticationEventExecutionPlanConfigurer, CasWebflowExecutionPlanConfigurer {

	@Resource
	private CustomAuthenticationHandler customAuthenticationHandler;

	@Resource
	private CustomMultifactorWebflowConfigurer customMultifactorWebflowConfigurer;

	@Override
	public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
		plan.registerAuthenticationHandler(customAuthenticationHandler);
	}

	@Override
	public void configureWebflowExecutionPlan(CasWebflowExecutionPlan plan) {
		plan.registerWebflowConfigurer(customMultifactorWebflowConfigurer);
	}
}
