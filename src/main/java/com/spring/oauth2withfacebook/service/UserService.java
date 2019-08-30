package com.spring.oauth2withfacebook.service;

import com.spring.oauth2withfacebook.model.Facebook;
import com.spring.oauth2withfacebook.model.User;
import com.spring.oauth2withfacebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Facebook facebook;

    public UserService(Facebook facebook, UserRepository userRepository) {
        this.facebook = facebook;
        this.userRepository = userRepository;
    }

    public User saveUserFromFacebookInfo(Map<String, Object> userInfoMap) {
        org.springframework.social.facebook.api.User userprofile = facebook.getProfile();
        System.out.println("Facebook Profile Data : " + userprofile);
        String imageUrl = facebook.getImageUrl();

        User user = new User();
        user.setName((String) userInfoMap.get("name"));
        user.setEmail((String) userInfoMap.get("email"));
        user.setImageUrl(imageUrl);

        System.out.println("User : " + user);
        userRepository.save(user);
        return user;
    }
}
