import com.example.sakila.controllers.ActorController;
import com.example.sakila.entities.Actor;
import com.example.sakila.services.ActorService;
import com.example.sakila.services.interfaces.IActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

public class ActorControllerStepDefs {
    private IActorService actorServiceMock;
    private final short expectedId = 1;
    private final Actor expectedActor = new Actor(expectedId, "Wayne", "Rooney", new ArrayList<>());
    private Actor actualActor;

    @Before
    public void setup() {
        actorServiceMock = mock(ActorService.class);
    }

    @Given("the actor with id {short} exists")
    public void givenActorOneExists(short id) {
        actorServiceMock = mock(ActorService.class);

        doReturn(expectedActor).when(actorServiceMock).getActorById(id);
    }

    @When("get request is made actor {short}")
    public void whenRequestWithIdIsMade(short id) {
        final ActorController actorController = new ActorController(actorServiceMock);

        try {
            actualActor = actorController.getActorById(id).getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Then("an actor is returned")
    public void thenActorIsReturned() {
        assertNotNull(actualActor);
        assertEquals("Wayne", actualActor.getFirstName());
        assertEquals("Rooney", actualActor.getLastName());
    }
}
