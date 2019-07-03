package ch.puzzle.libpuzzle.demo.springboot;

import ch.puzzle.libpuzzle.springframework.boot.rest.annotation.EnableCrudActions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCrudActions
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
