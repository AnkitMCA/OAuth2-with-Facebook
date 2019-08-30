package com.spring.oauth2withfacebook.service;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationService {

    private static String authorizationRequestBaseUri = "oauth2/authorization";

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public AuthorizationService(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
    }

    public Map<String, String> getOAuth2AuthenticationUrls() {
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        System.out.println("oauth2AuthenticationUrls : " + oauth2AuthenticationUrls);
        return oauth2AuthenticationUrls;
    }

    public OAuth2AuthorizedClient getOAuth2AuthorizedClientFromOAuth2AuthenticationToken(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName());

        return client;
    }

    public Map<String, Object> getUserInfoMapFromOAuth2AuthenticationToken(OAuth2AuthenticationToken authenticationToken) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap = authenticationToken.getPrincipal().getAttributes();

        return stringObjectMap;
    }

    public String getTokenFromOAuth2AuthenticationToken(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName());
        String token = client.getAccessToken().getTokenValue();
        return token;
    }
}
