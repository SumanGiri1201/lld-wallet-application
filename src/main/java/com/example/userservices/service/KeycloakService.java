package com.example.userservices.service;

import com.example.userservices.dto.RegisterRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
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
    private String userRealm; // digital-wallet-realm

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    public Keycloak getAdminKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
    public String getUserRealm() {
        return userRealm;
    }

    public String registerUser(RegisterRequest request) {
        try {
            Keycloak keycloak = getAdminKeycloak();

            // Create user
            UserRepresentation user = new UserRepresentation();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setEnabled(true);

            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setTemporary(false);
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(request.getPassword());

            user.setCredentials(Collections.singletonList(credentials));

            UsersResource usersResource = keycloak.realm(userRealm).users();
            Response response = usersResource.create(user);

            System.out.println("Keycloak status: " + response.getStatus());
            System.out.println("Keycloak message: " + response.readEntity(String.class));

            if (response.getStatus() == 201) {
                return "User registered successfully";
            } else {
                return "Failed to register user: " + response.getStatusInfo().getReasonPhrase();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception during registration: " + e.getMessage();
        }
    }
}
