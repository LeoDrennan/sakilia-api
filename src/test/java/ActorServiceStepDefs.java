import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.partials.PartialFilm;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class ActorServiceStepDefs {

    private ActorRepository actorRepositoryMock;
    private FilmRepository filmRepositoryMock;

    private final short mockActorId = 1;
    private final Actor mockActor = new Actor(mockActorId, "George", "Best", new ArrayList<>());
    private final Short[] mockFilmIds = new Short[] { 3 };
    private final Film mockFilm = new Film(mockFilmIds[0]);
    private Actor actualActor;

    @Before
    public void setup() {
        actorRepositoryMock = mock(ActorRepository.class);
        filmRepositoryMock = mock(FilmRepository.class);
    }


    @Given("the actor id {short} is provided")
    public void givenAnActorIdAndAListOfFilmIds(Short actorId) {
        actorRepositoryMock = mock(ActorRepository.class);
        filmRepositoryMock = mock(FilmRepository.class);

        doReturn(mockActor).when(actorRepositoryMock).findById(actorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such actor."));
        doReturn(mockFilm).when(filmRepositoryMock).findById(mockFilmIds[0])
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such film."));
    }

    @When("the actor is not already credited for the film")
    public void whenTheFilmIsNotADuplicate(Short actorId, Short[] filmIds) {
        final ActorService actorServiceMock = new ActorService();

        try {
            actualActor = actorServiceMock.addFilmsForActor(actorId, filmIds);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Then("the actors list of films contains id {short}")
    public void thenTheActorsListOfFilmsIncludesTheNewFilm() {
        short actualId = actualActor.getFilms().getFirst().getId();
        final short expectedId = 3;

        assertEquals(expectedId, actualId);
    }
}
