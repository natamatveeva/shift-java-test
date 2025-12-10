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
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckSwimTest extends DuckActionsClient {

    @Test(description = "Уточка плыви (существующий id)")
    @CitrusTest
    public void successfulSwimRightId(@Optional @CitrusResource TestCaseRunner runner,
                                      @Optional @CitrusResource TestContext context) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.ACTIVE);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        swimDuck(runner, id);
        validateResponse(runner, "{\n" +
                                         "\"message\":" + "\"Paws are not found ((((\"\n" +
                                         "}");}

    @Test(description = "Уточка плыви (несуществующий id)")
    @CitrusTest
    public void successfulSwimWrongId(@Optional @CitrusResource TestCaseRunner runner,
                                      @Optional @CitrusResource TestContext context) {
        swimDuck(runner, "0");
        validateResponse(runner, "{\n" +
                                  "\"message\":" + "\"Paws are not found ((((\"\n" +
                                 "}");
    }
}
