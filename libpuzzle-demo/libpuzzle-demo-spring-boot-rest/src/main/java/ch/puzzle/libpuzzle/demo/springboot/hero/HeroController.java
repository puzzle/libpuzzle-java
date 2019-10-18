package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.demo.springboot.common.ApiActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions.RepositoryListAction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("hero")
public class HeroController {

    private ApiActions<Hero> actions;

    public HeroController(ApiActions<Hero> actions) {
        this.actions = actions;
    }

    @GetMapping
    public ResponseEntity<Iterable<HeroDto>> list() {
        return actions.list()
                .matching(RepositoryListAction.allFilter())
                .execute(HeroDto.class);
    }

    @GetMapping("{id}")
    public ResponseEntity<HeroDto> find(@PathVariable long id) {
        return actions.find()
                .by(id)
                .execute(HeroDto.class);
    }

    @PostMapping
    public ResponseEntity<HeroDto> create(@RequestBody @Valid HeroDto dto) {
        return actions.create()
                .using(new Hero())
                .with(dto)
                .execute(HeroDto.class);
    }

    @PutMapping("{id}")
    public ResponseEntity<HeroDto> update(@PathVariable long id, @RequestBody @Valid HeroDto dto) {
        return actions.update()
                .by(id)
                .with(dto)
                .execute(HeroDto.class);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return actions.delete()
                .by(id)
                .execute();
    }

}
