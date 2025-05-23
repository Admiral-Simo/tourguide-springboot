package com.atlas.tourguide.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.atlas.tourguide.domain.entities.User;
import com.atlas.tourguide.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
		return new BlogUserDetails(user);
	}
	
}
