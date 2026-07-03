package com.trialmaple.external;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

import com.trialmaple.external.tmrpg.TmRpgService;

@Configuration(proxyBeanMethods = false)
@ImportHttpServices(TmRpgService.class)
public class HttpClientConfig {
}
