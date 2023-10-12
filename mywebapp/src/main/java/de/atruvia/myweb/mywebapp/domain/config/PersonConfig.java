package de.atruvia.myweb.mywebapp.domain.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PersonConfig {

    @Bean
    public List<String> antipathen() {
        return List.of("Attila","Peter","Paul","Mary");
    }

}
