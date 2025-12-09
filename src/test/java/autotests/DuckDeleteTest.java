package autotests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteTest extends DuckActionsClient {

    @Test(description="Удаление утки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner);
        deleteDuck(runner, id);
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"Duck is deleted\"\n" +
                                 "}");

    }
}
