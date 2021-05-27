package mj.jdbc_template_test.util;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@PropertySource("classpath:/oauth.properties")
public class OauthiOSUtil {

    private final Environment environment;

    public OauthiOSUtil(Environment environment) {
        this.environment = environment;
    }

    public String getClientId(){
        return environment.getProperty("github.client.id.ios");
    }

    public String getSecretId(){
        return environment.getProperty("github.secret.ios");
    }
}
