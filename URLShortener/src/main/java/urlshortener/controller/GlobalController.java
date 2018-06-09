package urlshortener.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import urlshortener.entity.User;
import urlshortener.service.UserService;

@Component
@ControllerAdvice
public class GlobalController {
	@Autowired
	private UserService userService;

	@ModelAttribute
	public void getUser(Model model, Principal principal) {
		if (principal != null) {
			User user = userService.getUserByPrincipal(principal);
			if(user != null) {
				model.addAttribute("principalUser", user.getCopy());
			}
		}
	}
}
