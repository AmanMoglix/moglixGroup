//package com.hackathon.auth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import com.hackathon.auth.utils.security.JwtAuthenticationEntryPoint;
//import com.hackathon.auth.utils.security.JwtAuthenticationFilter;
//import com.hackathon.auth.utils.security.JwtUserDetailsService;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private JwtUserDetailsService jwtUserDetailsService;
//
//	@Autowired
//	private JwtAuthenticationEntryPoint unauthorizedHandler;
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
//		return new JwtAuthenticationFilter();
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//	//to make password encode bean gloabal
//	@Autowired
//	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//		.csrf().disable()
//		.cors().disable()
//		.authorizeRequests().antMatchers("/api/auth/login/**").permitAll()
//		.antMatchers("/actuator/**").permitAll()
//		.antMatchers("/booked/**").permitAll()
//		.antMatchers("/user/**").permitAll()
//		.antMatchers("/home/**").permitAll()
//		.anyRequest()
//				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
//
//		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//	}
//	@SuppressWarnings("rawtypes")
//	@Bean
//	public FilterRegistrationBean platformCorsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//		CorsConfiguration configAutenticacao = new CorsConfiguration();
//		//configAutenticacao.addAllowedOriginPattern("*");
//		configAutenticacao.setAllowCredentials(true);
//		configAutenticacao.addAllowedOrigin("*");
//		configAutenticacao.addAllowedOrigin("https://*");
//		configAutenticacao.addAllowedHeader("*");
//		configAutenticacao.addAllowedHeader("Content-Type");
//		configAutenticacao.addAllowedHeader("Accept");
//		configAutenticacao.addAllowedMethod("POST");
//		configAutenticacao.addAllowedMethod("GET");
//		configAutenticacao.addAllowedMethod("DELETE");
//		configAutenticacao.addAllowedMethod("PUT");
//		configAutenticacao.addAllowedMethod("OPTIONS");
//		configAutenticacao.setMaxAge(3600L);
//		source.registerCorsConfiguration("/**", configAutenticacao);
//
//		@SuppressWarnings("unchecked")
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(-110);
//		return bean;
//	}
//}
