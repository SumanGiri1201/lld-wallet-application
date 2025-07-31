package com.example.userservices.service;

import com.example.userservices.dto.LoginRequest;
import com.example.userservices.dto.RegisterRequest;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak-admin.username}")
    private String adminUsername;

    @Value("${keycloak-admin.password}")
    private String adminPassword;


    private Keycloak getAdminKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    public String registerUser(RegisterRequest request) {
        return createUser(request.getUsername(), request.getPassword(), request.getEmail());
    }

    public String createUser(String username, String password, String email) {
        try {
            Keycloak keycloak = getAdminKeycloak();
            UsersResource usersResource = keycloak.realm(realm).users();

            UserRepresentation user = new UserRepresentation();
            user.setUsername(username);
            user.setEmail(email);
            user.setEnabled(true);

            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(password);
            credentials.setTemporary(false);

            user.setCredentials(Collections.singletonList(credentials));

            usersResource.create(user);

            return "Registration successful";
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }

    public String loginUser(LoginRequest request) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .build();

            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
            return tokenResponse.getToken();
        } catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }
}
