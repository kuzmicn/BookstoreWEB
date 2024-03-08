package com.example.demo.sec;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import model.User;

public class CustomOauthUserDetails implements OAuth2User {

	private User user;
	private UserDetails ud;
	private OAuth2User oauth2User;
	
	public CustomOauthUserDetails(User user, OAuth2User oauth2User, UserDetails ud) {
		this.user=user;
		this.oauth2User = oauth2User;
		this.ud=ud;
	}
	


	@Override
	public Map<String, Object> getAttributes() {
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return ud.getAuthorities();
	}

	@Override
	public String getName() {
		return ud.getUsername();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
