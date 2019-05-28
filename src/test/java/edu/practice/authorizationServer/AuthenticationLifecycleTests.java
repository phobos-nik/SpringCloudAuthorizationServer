package edu.practice.authorizationServer;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {AuthorizationServerApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationLifecycleTests {

    @Autowired
    private MockMvc mockMvc;

    @Value("${AUTHORIZATION_CLIENT_ID}")
    private String authorizationClientId;
    //private static final String authorizationClientId = "default_authorization_client_id";

    @Value("${AUTHORIZATION_CLIENT_SECRET}")
    private String authorizationClientSecret;
    //private static final String authorizationClientSecret = "authorization_client_secret";

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("AUTHORIZATION_CLIENT_ID", "test_authorization_client_id");
        System.setProperty("AUTHORIZATION_CLIENT_SECRET", "test_authorization_client_secret");
    }

    @Test
    void getToken() throws Exception {
        final MultiValueMap<String, String> parameters;
        parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", authorizationClientId);
        parameters.add("grant_type", "client_credentials");
        // parameters from /resources/data.sql
        parameters.add("username", "User");
        parameters.add("password", "user_password");

        mockMvc.perform(
                        post("/oauth/token")
                        .params(parameters)
                        .with(httpBasic(authorizationClientId, authorizationClientSecret))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}
