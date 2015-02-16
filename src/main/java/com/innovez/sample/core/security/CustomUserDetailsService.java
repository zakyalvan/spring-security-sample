package com.innovez.sample.core.security;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.innovez.sample.core.entity.User;

public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	private static final String CHECK_USER_BY_USERNAME_QUERY = "SELECT COUNT(u.id)>0 FROM User u WHERE u.username = :username";
	
	private static final String LOAD_USER_BY_USERNAME_QUERY = "SELECT u FROM User u WHERE u.username = :username";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Load user by username ({})", username);
		
		boolean userExists = entityManager.createQuery(CHECK_USER_BY_USERNAME_QUERY, Boolean.class)
			.setParameter("username", username)
			.getSingleResult();
		
		if(!userExists) {
			LOGGER.error("User with username {} not found", username);
			throw new UsernameNotFoundException(String.format("User with username %s not found", username));
		}
		
		User user = entityManager.createQuery(LOAD_USER_BY_USERNAME_QUERY, User.class)
			.setParameter("username", username)
			.getSingleResult();
		
		return createUserDetails(user);
	}
	
	private UserDetails createUserDetails(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(String role : user.getRoles()) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role);
			authorities.add(authority);
		}
		
		UserDetails userDetails = new CustomUserDetails(
				user.getUsername(), 
				user.getPassword(), 
				authorities, 
				user.isAccountExpired(), 
				user.isAccountLocked(), 
				user.isCredentialsExpired(), 
				user.isEnabled());
		
		return userDetails;
	}
}
