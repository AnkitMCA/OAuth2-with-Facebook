package com.spring.oauth2withfacebook.controller;

import com.spring.oauth2withfacebook.model.User;
import com.spring.oauth2withfacebook.service.AuthorizationService;
import com.spring.oauth2withfacebook.service.UserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {

    private final AuthorizationService authorizationService;
    private final UserService userService;

    public MainController(AuthorizationService authorizationService, UserService userService) {
        this.authorizationService = authorizationService;
        this.userService = userService;
    }

    @GetMapping("")
    public String home() {
        return "Home";
    }

    @GetMapping("oauth_login")
    public ModelAndView OauthLoginPage() {
        ModelAndView modelAndView = new ModelAndView("oauth_login");
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        oauth2AuthenticationUrls = authorizationService.getOAuth2AuthenticationUrls();
        modelAndView.addObject("urls", oauth2AuthenticationUrls);
        return modelAndView;
    }

    @GetMapping("loginSuccess")
    public ModelAndView loginSuccess(Model model, OAuth2AuthenticationToken authentication) {
        Map<String, Object> userInfoMap = authorizationService
                .getUserInfoMapFromOAuth2AuthenticationToken(authentication);

        String token = authorizationService.getTokenFromOAuth2AuthenticationToken(authentication);
        System.out.println("Token : " + token);
        User user = userService.saveUserFromFacebookInfo(userInfoMap);

        ModelAndView modelAndView = new ModelAndView("loginSuccess");
        modelAndView.addObject("user", user);
        modelAndView.addObject("token", token);
        return modelAndView;

    }

    @GetMapping("loginFailure")
    public ModelAndView loginFailure() {
        ModelAndView modelAndView = new ModelAndView("loginFailure");
        return modelAndView;
    }

}