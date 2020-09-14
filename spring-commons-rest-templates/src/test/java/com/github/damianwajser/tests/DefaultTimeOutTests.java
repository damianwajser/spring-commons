package com.github.damianwajser.tests;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultTimeOutTests {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testOK() {
        System.out.println("start request without delay");
        final Integer result = this.restTemplate.getForObject("http://localhost:" + port + "/without_timeout", Integer.class);
        System.out.println("end request without delay");

        assertThat(result, notNullValue());
        assertThat(restTemplate.getRequestFactory(), instanceOf(InterceptingClientHttpRequestFactory.class));
    }

    @Test
    public void testDefaultConfig() {
        final RequestConfig requestConfig = getRequestConfig(restTemplate);

        assertThat(requestConfig.getConnectTimeout(), is(-1));
        assertThat(requestConfig.getConnectionRequestTimeout(), is(-1));
        assertThat(requestConfig.getSocketTimeout(), is(-1));
    }

    private RequestConfig getRequestConfig(RestTemplate restTemplate) {
        final HttpClient httpClient = (HttpClient) ReflectionTestUtils.getField(
                Objects.requireNonNull(ReflectionTestUtils.getField(restTemplate, "requestFactory")), "httpClient");

        assertThat(httpClient, notNullValue());

        return ((RequestConfig) ReflectionTestUtils.getField(httpClient, "defaultConfig"));
    }
}
