package ch.puzzle.libpuzzle.demo.springboot;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.ModelMapperDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {

    @Bean
    public DtoMapper dtoMapper() {
        return new ModelMapperDtoMapper(new ModelMapper());
    }

}
