package com.example.demo.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.RoleRepo;
import com.example.demo.repositories.UserRepo;
import model.User;

@Service
public class CustomUserDetailsOauthService extends DefaultOAuth2UserService {
	@Autowired
	CustomUserDetailsService cuds;

	@Autowired
	UserRepo ur;

	@Autowired
	RoleRepo rr;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User user = super.loadUser(userRequest);
		UserDetails ud = null;
		User u = null;
		try {
			ud = cuds.loadUserByUsername((String) user.getAttributes().get("login"));
			u = ur.findByUsername(ud.getUsername());
			if(u.getPassword()!=null)
				throw new RuntimeException("Cant realise oauth, user with this username exists!");
		} catch (UsernameNotFoundException e) {
			
			u = saveUser(user);
			ud = cuds.loadUserByUsername((String) user.getAttributes().get("login"));
		}
		CustomOauthUserDetails oauthUser = new CustomOauthUserDetails(u, user, ud);
		return oauthUser;
	}

	public User saveUser(OAuth2User us) {
		User u = new User();
		u.setUsername(us.getAttribute("login"));
		try {
			String name[] = ((String) us.getAttribute("name")).split(" ");
			u.setName(name[0]);
			u.setSurname(name[1]);
		}catch(Exception e) {
			//If user has no name in his account it will remain null
		}
		u.setEmail(us.getAttribute("email"));
		u.setRole(rr.findByName("USER"));
		u = ur.save(u);

		return u;
	}
}
