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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {AuthorizationServerApplication.class})
class AuthenticationLifecycleTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${AUTHORIZATION_CLIENT_ID}")
    private String authorizationClientId;

    @Value("${AUTHORIZATION_CLIENT_SECRET}")
    private String authorizationClientSecret;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("AUTHORIZATION_CLIENT_ID", "test_authorization_client_id");
        System.setProperty("AUTHORIZATION_CLIENT_SECRET", "test_authorization_client_secret");
    }

    @Test
    void getUserToken() throws Exception {
        final MultiValueMap<String, String> parameters;
        parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", authorizationClientId);
        parameters.add("grant_type", "client_credentials");
        // parameters from /resources/data.sql
        parameters.add("username", "User");
        parameters.add("password", "user_password");
        
        assertNotNull(getAccessToken(parameters));
    }

    @Test
    void getAdministratorToken() throws Exception {
        final MultiValueMap<String, String> parameters;
        parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", authorizationClientId);
        parameters.add("grant_type", "client_credentials");
        // parameters from /resources/data.sql
        parameters.add("username", "Administrator");
        parameters.add("password", "administrator_password");

        assertNotNull(getAccessToken(parameters));
    }

    private String getAccessToken(MultiValueMap<String, String> parameters) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/oauth/token")
                        .params(parameters)
                        .with(httpBasic(authorizationClientId, authorizationClientSecret))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        final String response = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(response, JsonNode.class).asText();
    }
}
