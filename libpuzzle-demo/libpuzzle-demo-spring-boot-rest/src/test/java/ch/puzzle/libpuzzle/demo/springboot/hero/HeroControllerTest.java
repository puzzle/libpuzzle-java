package ch.puzzle.libpuzzle.demo.springboot.hero;

import ch.puzzle.libpuzzle.demo.springboot.common.ApiActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.util.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.CompositeActionConfigurer.mockedActions;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create.CreateActionMatchers.createAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete.DeleteActionMatchers.deleteAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find.FindActionMatchers.findAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list.ListActionMatchers.listAction;
import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update.UpdateActionMatchers.updateAction;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroControllerTest {

    private static long RESOURCE_ID = 1L;

    private static final String COLLECTION_URL = "/hero";

    private static final String RESOURCE_URL = COLLECTION_URL + "/" + RESOURCE_ID;

    private static final HeroDto NEW_HERO_DTO = HeroDto.builder().name("new-hero").build();

    private static final String NEW_HERO_DTO_JSON = Resource.content("hero/create-hero-body.json");

    private static final HeroDto EXISTING_HERO_DTO = HeroDto.builder().id(RESOURCE_ID).name("existing-hero").build();

    private static final String EXITING_HERO_DTO_JSON = Resource.content("hero/update-hero-body.json");

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ApiActions<Hero> restActions;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(mockedActions(restActions))
                .build();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get(COLLECTION_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(listAction().executed(HeroDto.class));
    }

    @Test
    public void testFind() throws Exception {
        mockMvc.perform(get(RESOURCE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(findAction().by(RESOURCE_ID))
                .andExpect(findAction().executed(HeroDto.class));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(
                post(COLLECTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEW_HERO_DTO_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(createAction().using(new Hero()))
                .andExpect(createAction().with(NEW_HERO_DTO))
                .andExpect(createAction().executed(HeroDto.class));
    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(
                put(RESOURCE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(EXITING_HERO_DTO_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(updateAction().by(RESOURCE_ID))
                .andExpect(updateAction().with(EXISTING_HERO_DTO))
                .andExpect(updateAction().executed());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(RESOURCE_URL))
                .andExpect(status().is2xxSuccessful())
                .andExpect(deleteAction().by(RESOURCE_ID))
                .andExpect(deleteAction().executed());
    }
}
