package autotests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckCreateTest extends DuckActionsClient {

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
                                       TestContext context) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        validateResponse(runner, "{\n" +
                                 "\"id\":" + id + ",\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + height + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
                                 "}");
    }
    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
                                     TestContext context) {
        String color = "yellow";
        double height = 0.3;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        validateResponse(runner, "{\n" +
                             "\"id\":\"" + id + ",\n" +
                             "\"color\":\"" + color + "\",\n" +
                             "\"height\":" + height + ",\n" +
                             "\"material\":\"" + material + "\",\n" +
                             "\"sound\":\"" + sound + "\",\n" +
                             "\"wingsState\":\"" + wingsState + "\"\n" +
                             "}");
    }
}
