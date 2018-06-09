package urlshortener.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import urlshortener.entity.Role;

@EnableWebMvc
@Configuration
@EntityScan("urlshortener.entity")
@EnableJpaRepositories("urlshortener.dao")
@SpringBootApplication(scanBasePackages = { "urlshortener" })
@EnableTransactionManagement
public class WebApplication extends SpringBootServletInitializer {
	public static List<Role> roles = Arrays.asList(new Role(1L, "USER", true), new Role(2L, "ADMIN"));

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}
}
