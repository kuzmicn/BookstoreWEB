package com.example.demo.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.UserRepo;

import model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepo ur;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = ur.findByUsername(username);
		if(u==null)
			throw new UsernameNotFoundException("There is no user by username:"+username);
		UserDetails ud = new CustomUserDetails(u);
		return ud;
	}

}
