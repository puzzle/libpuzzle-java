package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.demo.springboot.common.ApiActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actionfactory.RepositoryActionsFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroConfig {

    @Bean
    public ApiActions<Hero> heroActions(HeroRepository repository, RepositoryActionsFactory repositoryActionsFactory) {
        var repositoryActions = repositoryActionsFactory.forRepository(repository);
        return ApiActions.configure(new ApiActions<Hero>())
                .with(repositoryActions.all())
                .apply();
    }
}
