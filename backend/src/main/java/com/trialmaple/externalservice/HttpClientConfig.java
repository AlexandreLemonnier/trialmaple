package com.trialmaple.externalservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import com.trialmaple.externalservice.tmrpg.TmRpgService;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(TmRpgService.class)
public class HttpClientConfig {
}
