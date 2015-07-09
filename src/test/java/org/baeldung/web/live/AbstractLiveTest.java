package org.baeldung.web.live;

import java.text.SimpleDateFormat;

import org.baeldung.web.live.config.CommonPaths;
import org.baeldung.web.live.config.TestConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class }, loader = AnnotationConfigContextLoader.class)
@Ignore
public class AbstractLiveTest {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private CommonPaths commonPaths;

    protected String urlPrefix;

    protected ObjectMapper objectMapper = new ObjectMapper().setDateFormat(dateFormat);

    @Before
    public void setup() {
        urlPrefix = commonPaths.getServerRoot();
    }

    //

    protected RequestSpecification givenAuth() {
        final FormAuthConfig formConfig = new FormAuthConfig(urlPrefix + "/j_spring_security_check", "username", "password");
        return RestAssured.given().auth().form("john", "123", formConfig);
    }

    protected RequestSpecification withRequestBody(final RequestSpecification req, final Object obj) throws JsonProcessingException {
        return req.contentType(MediaType.APPLICATION_JSON_VALUE).body(objectMapper.writeValueAsString(obj));
    }

}