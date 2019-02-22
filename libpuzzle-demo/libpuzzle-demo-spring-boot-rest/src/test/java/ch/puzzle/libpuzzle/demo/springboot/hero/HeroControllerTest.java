package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.demo.springboot.common.ApiActions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.CreateAssert.assertCreate;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.DeleteAssert.assertDelete;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.FindAssert.assertFind;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.ListAssert.assertList;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.UpdateAssert.assertUpdate;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerTest {

    private static long RESOURCE_ID = 1L;

    private static String COLLECTION_URL = "/hero";

    private static String RESOURCE_URL = COLLECTION_URL + "/" + RESOURCE_ID;

    private static HeroDto NEW_HERO_DTO = HeroDto.builder().name("new-hero").build();

    private static HeroDto EXISTING_HERO_DTO = HeroDto.builder().id(RESOURCE_ID).name("existing-hero").build();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ApiActions<Hero> restActions;

    @Test
    public void testList() {
        assertList(restTemplate, restActions)
                .using(COLLECTION_URL, HeroDto.class)
                .executed(HeroDto.class);
    }

    @Test
    public void testFind() {
        assertFind(restTemplate, restActions)
                .using(RESOURCE_URL)
                .by(RESOURCE_ID)
                .withResponse(HeroDto.class);
    }

    @Test
    public void testCreate() {
        assertCreate(restTemplate, restActions)
                .using(COLLECTION_URL, NEW_HERO_DTO, HeroDto.class)
                .from(NEW_HERO_DTO)
                .with(new Hero())
                .withResponse(HeroDto.class);
    }

    @Test
    public void testUpdate() {
        assertUpdate(restTemplate, restActions)
                .using(RESOURCE_URL, EXISTING_HERO_DTO, HeroDto.class)
                .by(RESOURCE_ID)
                .dto(EXISTING_HERO_DTO)
                .withResponse(HeroDto.class);
    }

    @Test
    public void testDelete() {
        assertDelete(restTemplate, restActions)
                .using(RESOURCE_URL)
                .by(RESOURCE_ID)
                .executed();
    }


}
