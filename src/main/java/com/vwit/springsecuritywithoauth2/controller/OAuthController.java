package com.vwit.springsecuritywithoauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OAuthController {
	@Autowired
	private OAuth2AuthorizedClientService auth2AuthorizedClientService;

	@RequestMapping("login")
	public String login() {
		return "login"; // will search for login.jsp inside WEB-INF/jsp/
	}

	@RequestMapping("/oauth2LoginSuccess")
	public String getOauthLoginInfo(Model model,
			@AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken) {
		System.out.println(authenticationToken.getAuthorizedClientRegistrationId());
		System.out.println(authenticationToken.getName());

		// fetching user info
		OAuth2User user = authenticationToken.getPrincipal();
		System.out.println("userId:" + user.getName());

		OAuth2AuthorizedClient client = auth2AuthorizedClientService.loadAuthorizedClient(
				authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
		
		model.addAttribute("name",user.getAttribute("name"));
		return "home";
	}
	
	@RequestMapping("/formLoginSuccess")
	public String getFormLoginInfo(Model model, @AuthenticationPrincipal Authentication authentication) {
		UserDetails userDetails= (UserDetails) authentication.getPrincipal();
		model.addAttribute("name",userDetails.getUsername());
		return "home";
	}
}
