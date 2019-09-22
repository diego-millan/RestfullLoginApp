package br.com.loginapplication.login.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.loginapplication.login.model.User;
import br.com.loginapplication.login.repository.UserRepository;

public class TokenAuthorizationFilter extends OncePerRequestFilter {

	private CustomTokenService tokenService;
	private UserRepository userRepository;
	
	public TokenAuthorizationFilter(CustomTokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = retrieveToken(request);
		boolean isValidToken = tokenService.isValidToken(token);
		
		if (isValidToken) {
			Long userId = tokenService.getUserId(token);
			Optional<User> optionalUser = userRepository.findById(userId);
			
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		return token;
	}
	
	
}
