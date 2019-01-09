package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.RestAssert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerTest {

    private static long RESOURCE_ID = 1L;

    private static String COLLECTION_URL= "/hero";

    private static String RESOURCE_URL= COLLECTION_URL + "/" + RESOURCE_ID;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RestActions<Hero, Long> restActions;

    @Test
    public void testList() {
        assertList(restTemplate, restActions)
                .using(COLLECTION_URL, HeroDto.class)
                .executed();
    }

    @Test
    public void testFind() {
        assertFind(restTemplate, restActions)
                .using(RESOURCE_URL, HeroDto.class)
                .executedWith(RESOURCE_ID);
    }

    @Test
    public void testCreate() {
        assertCreate(restTemplate, restActions)
                .using(COLLECTION_URL, new HeroDto(), HeroDto.class)
                .executedWith(new Hero());
    }

    @Test
    public void testUpdate() {
        assertUpdate(restTemplate, restActions)
                .using(RESOURCE_URL, new HeroDto(), HeroDto.class)
                .executedWith(RESOURCE_ID);
    }

    @Test
    public void testDelete() {
        assertDelete(restTemplate, restActions)
                .using(RESOURCE_URL)
                .executedWith(RESOURCE_ID);
    }


}
