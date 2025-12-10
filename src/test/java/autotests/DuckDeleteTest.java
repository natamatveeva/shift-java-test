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

public class DuckDeleteTest extends DuckActionsClient {

    @Test(description="Удаление утки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner,
                                 @Optional @CitrusResource TestContext context) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.FIXED);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        deleteDuck(runner, id);
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"Duck is deleted\"\n" +
                                 "}");

    }
}
