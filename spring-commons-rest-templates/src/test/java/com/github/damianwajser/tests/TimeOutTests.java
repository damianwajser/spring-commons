package com.github.damianwajser.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
        "spring.commons.rest.template.timeout.connection=50",
        "spring.commons.rest.template.timeout.write=50",
        "spring.commons.rest.template.timeout.read=50"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeOutTests {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testOK() throws Exception {
        try {
            System.out.println("start request");
            this.restTemplate
                    .getForObject("http://localhost:" + port + "/timeout", Integer.class);
            Assert.fail();
        } catch (ResourceAccessException e) {
            System.out.println("TimeOut- start new delay");
            Thread.sleep(200);
            System.out.println("TimeOut- stop new delay");
            Integer response = this.restTemplate
                    .getForObject("http://localhost:" + port + "/without_timeout", Integer.class);
            Assert.assertEquals(1, response.intValue());
        }
    }
}
