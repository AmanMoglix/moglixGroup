package com.hackathon.gateway;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.hackathon.gateway.config.AccessAuthorityConfig;
import com.hackathon.gateway.filter.ErrorFilter;
import com.hackathon.gateway.filter.PostFilter;
import com.hackathon.gateway.filter.PreFilter;
import com.hackathon.gateway.filter.RouteFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	@Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    @Bean
    public AccessAuthorityConfig accessAuthorityConfig() {
        return new AccessAuthorityConfig("", "", "");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
