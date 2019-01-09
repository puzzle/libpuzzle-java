package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfig {

    @Bean
    public RestActions<Hero, Long> restActions(DtoMapper dtoMapper, HeroRepository repository) {
        return new RestActions<Hero, Long>(repository, dtoMapper);
    }

}
