package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesTest extends DuckActionsClient {

    @Test(description = "Вывод параметров уточки (id целое четное)")
    @Parameters({"runner", "context"})
    @CitrusTest
    public void getPropertiesEvenNumbered(@Optional @CitrusResource TestCaseRunner runner,
                                          @Optional @CitrusResource TestContext context
    ) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.FIXED);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        String responseMessage = "{\n" +
                                 "\"color\": \"yellow\",\n" +
                                 "\"height\": " + 30.0 + ",\n" +
                                 "\"material\": \"rubber\",\n" +
                                 "\"sound\": \"quack\",\n" +
                                 "\"wingsState\": \"FIXED\"\n" +
                                 "}";
        if (Integer.parseInt(id) % 2 == 0) {
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        } else {
            createDuck(runner, duckProperties);
            id = extractIdDuckFromResponse(runner, context);
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        }
    }

    @Test(description = "Вывод параметров уточки (id целое нечетное)")
    @CitrusTest
    public void getPropertiesOdd(@Optional @CitrusResource TestCaseRunner runner,
                                 @Optional @CitrusResource TestContext context
    ) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.ACTIVE);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        String responseMessage = "{\n" +
                                 "\"color\": \"yellow\",\n" +
                                 "\"height\": " + 30.0 + ",\n" +
                                 "\"material\": \"rubber\",\n" +
                                 "\"sound\": \"quack\",\n" +
                                 "\"wingsState\": \"ACTIVE\"\n" +
                                 "}";
        if (Integer.parseInt(id) % 2 == 1) {
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        } else {
            createDuck(runner, duckProperties);
            id = extractIdDuckFromResponse(runner, context);
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        }
    }
}
