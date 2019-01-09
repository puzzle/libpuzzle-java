package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.RestRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends RestRepository<Hero, Long> {
}
