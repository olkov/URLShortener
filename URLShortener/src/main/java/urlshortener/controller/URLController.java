package urlshortener.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import urlshortener.application.Utils;
import urlshortener.entity.URL;
import urlshortener.service.URLService;
import urlshortener.service.UserService;

@Controller
public class URLController {
	@Autowired
	private URLService urlService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/{shortURL}"}, method = RequestMethod.GET)
	public String getURL(@PathVariable("shortURL") String shortURL) {
		URL url = urlService.getURLByShortURL(shortURL);
		String redirectURL = StringUtils.EMPTY;
		if(url != null) {
			redirectURL = url.getURL();
		}
		return "redirect:" + redirectURL;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/urls/{urlId}/edit" }, method = RequestMethod.POST)
	public String editURL(@PathVariable("urlId") Long urlId,
		@RequestParam("url") String urlStr,
		Principal principal, Model model) {
		if(principal != null) {
			URL url = urlService.getById(urlId);
			if(url != null && userService.hasAccess(principal, url.getUser().getId()) != null) {
				url.setURL(Utils.toCanonicalURL(urlStr));
				return urlService.save(url).toString();
			}
		}
		return "Error!";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/urls/{urlId}/delete", "urls/{urlId}/remove" }, method = RequestMethod.GET)
	public String deleteURL(@PathVariable("urlId") Long urlId, Principal principal) {
		if(principal != null) {
			URL url = urlService.getById(urlId);
			if(url != null && userService.hasAccess(principal, url.getUser().getId()) != null) {
				urlService.deleteById(urlId);
				return "success";
			}
		} 
		return "Error!";
	}
}
