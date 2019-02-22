package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.demo.springboot.common.ApiActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.RepositoryActions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfig {

    @Bean
    public ApiActions<Hero> restActions(HeroRepository repository, RepositoryActions repositoryActions) {
        var actions = new ApiActions<Hero>();
        actions.configure()
                .use(repositoryActions.forRepository(repository));
        return actions;
    }

}
