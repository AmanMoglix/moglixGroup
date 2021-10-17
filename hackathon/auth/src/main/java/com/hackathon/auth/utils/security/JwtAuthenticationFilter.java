//package com.hackathon.auth.utils.security;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.hackathon.auth.utils.security.enumeration.JWTConstants;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter{
//	@Autowired
//	private JwtUserDetailsService jwtUserDetailsService;
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	
//
//	@Autowired
//	HttpSession session;
//	@Override
//	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
//			throws ServletException, IOException {
//		String header = httpServletRequest.getHeader(JWTConstants.HEADER_STRING);
//		String username = null;
//		String audience = null; // tenantOrClientId
//		String authToken = null;
//		if (header != null && header.startsWith(JWTConstants.TOKEN_PREFIX)) {
//
//			authToken = header.replace(JWTConstants.TOKEN_PREFIX, "");
//			if (authToken.contains("#_=_") || authToken.contains("#")) {
//				authToken = authToken.substring(0, authToken.indexOf('#'));
//			}
//			try {
//				username = jwtTokenUtil.getUsernameFromToken(authToken);
//				audience = jwtTokenUtil.getAudienceFromToken(authToken);
//				httpServletRequest.setAttribute("audience", audience);
//				Claims claims = jwtTokenUtil.getAllClaimsFromToken(authToken);
//				httpServletRequest.setAttribute("defaultloginbranch", claims.get("defaultloginbranch"));
//				httpServletRequest.setAttribute("claims", claims);
////				MasterTenant masterTenant = masterTenantService.findByClientId(Integer.valueOf(audience));
////				if (null == masterTenant) {
////					logger.error("An error during getting tenant name");
////					throw new BadCredentialsException("Invalid tenant and user.");
////				}
////				DBContextHolder.setCurrentDb(masterTenant.getDbName());
//			} catch (IllegalArgumentException ex) {
//				logger.error("An error during getting username from token", ex);
//			} catch (ExpiredJwtException ex) {
//				httpServletRequest.setAttribute("expired", "The token is expired and not valid anymore");
//				logger.warn("The token is expired and not valid anymore", ex);
//			}
//		} else {
//			logger.warn("Couldn't find bearer string, will ignore the header");
//		}
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
//			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
//
//				/*
//				 * ROLE_ADMIN MUST NOT BE PASSES STATICALLY
//				 * 
//				 */
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//				logger.info("authenticated user " + username + ", setting security context");
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
//		}
//		filterChain.doFilter(httpServletRequest, httpServletResponse);
//		
//	}
//
//}
