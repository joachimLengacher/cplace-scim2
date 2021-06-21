package cf.cplace.examples.oauth2client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Configuration
@SpringBootApplication
public class OAuth2ClientApplication implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(OAuth2ClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ClientApplication.class, args);
    }

    @Autowired
    private AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;

    @Override
    public void run(String... args) {
        OAuth2AccessToken accessToken = retrieveToken();
        logToken(accessToken);
        String info = accessCplaceInfoEndpoint(accessToken);
        log.info("Reply: " + info);
    }

    private OAuth2AccessToken retrieveToken() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal("Demo Service")
                .build();
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientServiceAndManager.authorize(authorizeRequest);
        return Objects.requireNonNull(authorizedClient).getAccessToken();
    }

    private void logToken(OAuth2AccessToken accessToken) {
        log.info("Issued: " + Objects.requireNonNull(accessToken.getIssuedAt()).toString());
        log.info("Expires:" + Objects.requireNonNull(accessToken.getExpiresAt()).toString());
        log.info("Scopes: " + accessToken.getScopes().toString());
        log.info("Token: " + accessToken.getTokenValue());
    }

    private String accessCplaceInfoEndpoint(OAuth2AccessToken accessToken) {
        HttpEntity<String> requestEntity = createRequestEntity(accessToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8083/intern/tricia/cplace-api/cf.cplace.platform/management/info",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        return response.getBody();
    }

    private HttpEntity<String> createRequestEntity(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());
        return new HttpEntity<>(headers);
    }
}
