package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.demo.springboot.common.ApiActions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("hero")
public class HeroController {

    private ApiActions<Hero> actions;

    public HeroController(ApiActions<Hero> actions) {
        this.actions = actions;
    }

    @GetMapping
    public ResponseEntity<List<HeroDto>> list() {
        return actions.list().execute(HeroDto.class);
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
                .with(dto)
                .using(new Hero())
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
