import com.example.sakila.controllers.ActorController;
import com.example.sakila.entities.Actor;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.services.ActorService;
import com.example.sakila.services.interfaces.IActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

public class ActorServiceStepDefs {
    private ActorRepository actorRepositoryMock;
    private FilmRepository filmRepositoryMock;

    private ResponseStatusException thrownException;

    @Before
    public void setup() {
        actorRepositoryMock = mock(ActorRepository.class);
        filmRepositoryMock = mock(FilmRepository.class);
    }

    @Given("actor with id {short} does not exist in db")
    public void givenActorOneExists(short id) {

        doReturn(false).when(actorRepositoryMock).existsById(id);
    }

    @When("delete method is called with id {short}")
    public void whenRequestWithIdIsMade(short id) {
        final ActorService actorService = new ActorService(actorRepositoryMock, filmRepositoryMock);

        try {
            actorService.deleteActor(id);
        } catch (ResponseStatusException statusException) {
            thrownException = statusException;
        }
    }

    @Then("a response status exception is thrown")
    public void thenActorIsReturned() {

        assertNotNull(thrownException);
        assertEquals(HttpStatus.NOT_FOUND, thrownException.getStatusCode());
    }
}
