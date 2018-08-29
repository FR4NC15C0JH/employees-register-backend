package com.employees.profile.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.employees.profile.enumerator.Profiles;
import com.employees.profile.model.User;

public class JwtUserFactory {
	
	private JwtUserFactory() {
		
	}
	/**
	 * Converter um jwtUser com dados do User
	 * @param user
	 * @return
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getProfiles()));
	}
	/**
	 * Converter perfil User para ser usado no spring security
	 * @param profiles
	 * @return
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(Profiles profiles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profiles.toString()));
		return authorities;
	}
}
