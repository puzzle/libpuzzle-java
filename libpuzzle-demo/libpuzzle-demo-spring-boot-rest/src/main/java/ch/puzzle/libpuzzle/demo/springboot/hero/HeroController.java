package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.dto.PageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("hero")
public class HeroController {

    private RestActions<Hero, Long> actions;

    public HeroController(RestActions<Hero, Long> actions) {
        this.actions = actions;
    }

    @GetMapping
    public ResponseEntity<List<HeroDto>> list() {
        return actions.list(HeroDto.class).execute();
    }

    @GetMapping("{id}")
    public ResponseEntity<HeroDto> find(@PathVariable long id) {
        return actions.find(HeroDto.class).execute(id);
    }

    @PostMapping
    public ResponseEntity<HeroDto> create(@RequestBody @Valid HeroDto dto) {
        return actions.create(HeroDto.class).execute(dto, new Hero());
    }

    @PutMapping("{id}")
    public ResponseEntity<HeroDto> update(@PathVariable long id, @RequestBody @Valid HeroDto dto) {
        return actions.update(HeroDto.class).execute(dto, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return actions.delete().execute(id);
    }

    //@GetMapping
    public ResponseEntity<PageDto<HeroDto>> filter(
            @RequestParam String filter,
            @RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam String orderBy
    ) {
        return actions.filter(HeroDto.class, null).execute(filter, pageSize, page, orderBy);
    }

}
