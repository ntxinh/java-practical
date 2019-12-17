package example;

import com.googlecode.objectify.ObjectifyFilter;

import com.googlecode.objectify.ObjectifyService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import data.objectify.config.EnableObjectifyRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableObjectifyRepositories
@EnableJpaRepositories
public class ExampleApp extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleApp.class, args);
    }

    public ExampleApp() {
        registerEntities();
    }

    @Bean
    public FilterRegistrationBean objectifyFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ObjectifyFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
    
        return registration;
    }
    
    private void registerEntities(){
        ObjectifyService.register(Customer.class);
    }
}
