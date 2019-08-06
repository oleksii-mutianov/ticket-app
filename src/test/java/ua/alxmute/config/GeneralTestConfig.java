package ua.alxmute.config;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Properties;

@ComponentScan(basePackages = "ua.alxmute")
@EnableAutoConfiguration
public class GeneralTestConfig {

    @Bean
    private VelocityEngine velocityEngine() throws VelocityException {

        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(props);
        velocityEngine.init();

        return velocityEngine;
    }
}
