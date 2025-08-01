package com.example.userservices.service;

import com.example.userservices.dto.LoginRequest;
import com.example.userservices.dto.RegisterRequest;
import com.example.userservices.model.User;
import com.example.userservices.Repository.UserRepository;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private KeycloakService keycloakService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Registration method
    public String register(RegisterRequest request) {
        try {
            Keycloak keycloak = keycloakService.getAdminKeycloak();
            UsersResource usersResource = keycloak.realm(keycloakService.getUserRealm()).users();

            // Create user in Keycloak
            UserRepresentation user = new UserRepresentation();
            user.setUsername(request.getEmail());
            user.setEmail(request.getEmail());
            user.setEnabled(true);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(request.getPassword());
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            Response response = usersResource.create(user);
            if (response.getStatus() != 201) {
                return "Failed to register in Keycloak: " + response.getStatus();
            }

            // Optional: save to local DB
            User newUser = new User();
            newUser.setEmail(request.getEmail());
            newUser.setPassword(request.getPassword()); // 🔐 Optional: hash this
            userRepository.save(newUser);

            return "Registration successful";

        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }

    // ✅ Login method
    public String login(LoginRequest request) {
        String tokenUrl = "http://localhost:8080/auth/realms/digital-wallet-realm/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create request body
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", "wallet-client-user");
        params.put("username", request.getEmail());
        params.put("password", request.getPassword());

        // Convert to URL encoded string
        StringBuilder formBody = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (formBody.length() > 0) formBody.append("&");
            formBody.append(entry.getKey()).append("=").append(entry.getValue());
        }

        HttpEntity<String> entity = new HttpEntity<>(formBody.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // return entire token JSON
            } else {
                return "Login failed: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Login error: " + e.getMessage();
        }
    }
}
