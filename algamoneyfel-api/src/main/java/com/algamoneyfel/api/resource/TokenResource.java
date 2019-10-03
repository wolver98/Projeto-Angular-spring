package com.algamoneyfel.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoneyfel.api.AlgamoneyfelApiApplication;
import com.algamoneyfel.api.config.property.AlgamoneyFelApiProperty;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private AlgamoneyFelApiProperty algamoneyProperties;
	
	@DeleteMapping("/revoke") // revoke vai anular o refresh token dando um logout
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);           // habilitando para true para uso do HEROKO
		cookie.setSecure(this.algamoneyProperties.getSeguranca().isEnableHttps()); // Em produção será true lah no properties
		cookie.setPath(req.getContextPath() + "/oauth/token" );
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
