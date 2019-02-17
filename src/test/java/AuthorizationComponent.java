import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationComponent {

    public JwtResponse getJWT(final String port) {
        final ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        final RestTemplate restTemplate = new RestTemplate(requestFactory);

        final String requestUrl = "http://localhost:" + port + "/oauth/token";

        final FormHttpMessageConverter converter = new FormHttpMessageConverter();
        final List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        converter.setSupportedMediaTypes(mediaTypes);
        restTemplate.getMessageConverters().add(converter);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", "tutorialspoint@gmail.com");
        map.add("password", "1234");

        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic dHV0b3JpYWxzcG9pbnQ6bXktc2VjcmV0LWtleQ==");

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForObject(requestUrl, request, JwtResponse.class);
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        final int timeout = 5000;
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}