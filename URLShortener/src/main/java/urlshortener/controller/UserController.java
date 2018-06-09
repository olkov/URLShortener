package urlshortener.controller;

import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import urlshortener.entity.URL;
import urlshortener.entity.User;
import urlshortener.service.URLService;
import urlshortener.service.UserService;

@Controller
@RequestMapping(value = "")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private URLService urlService;
	
	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	public String getAllUsers(Model model, Principal principal) {
		User user = userService.getUserByPrincipal(principal);
		if (user != null && user.isAdmin()) {
			model.addAttribute("users", userService.getAllUsers());
			return "users.All users";
		}
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = { "/users/{userId}" }, method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userId") Long userId, Principal principal) {
		if (userService.hasAccess(principal, userId) != null) {
			try {
				userService.deleteUser(userId);
				return "/users";
			} catch (Exception e) {
				System.err.println("Cannot remove user!");
				e.printStackTrace();
			}
			return "Error!";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = { "/users/{userId}/urls", "/urls"}, method = RequestMethod.GET)
	public String getUserURLs(@PathVariable(value = "userId", required = false) Long userId, Principal principal, Model model) {
		if(principal != null) {
			List<URL> URLs = new ArrayList<>();
			User user = null;
			String name = null;
			if(userId != null) {
				if (userService.hasAccess(principal, userId) != null) {
					user = userService.getUserById(userId);
					URLs = urlService.getAllByUserId(userId);
					name = user.getFirstName() + "'s " + user.getLastName();
				} else {
					return "redirect:/";
				}
			} else {
				user = userService.getUserByPrincipal(principal);
				if(user == null) {
					return "redirect:/";
				}
				URLs = urlService.getAllByUserId(user.getId());
				name = "Your";
			}
			model.addAttribute("name", name);
			model.addAttribute("user", user);
			model.addAttribute("URLs", URLs);
			return "urls.URLs";
		}
		return "redirect:/login";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/users/{userId}/urls/add"}, method = RequestMethod.POST)
	public String addUserURL(
			@PathVariable(value = "userId") Long userId, 
			@RequestParam("url") String urlStr,
			Principal principal, Model model) throws UnknownHostException {
		User user = userService.getUserById(userId);
		return urlService.save(new URL(urlStr, user)).toString();
	}
}
