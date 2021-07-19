package pl.example.customspring.program;

import pl.example.customspring.annotation.Bean;
import pl.example.customspring.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean(name = "overrideBeanName")
    public String stringBean() {
        return "Example";
    }

    @Bean
    public Integer intBean() {
        return 12;
    }

    public Integer notABean() {
        return 1;
    }
}
