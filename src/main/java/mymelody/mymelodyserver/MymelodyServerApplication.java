package mymelody.mymelodyserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MymelodyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymelodyServerApplication.class, args);
    }

}
