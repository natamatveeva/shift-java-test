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

public class DuckFlyTest extends DuckActionsClient {

    @Test(description = "Проверка полета с активными крыльями")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner,
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
        flyDuck(runner, id);
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"I am flying :)\"\n" +
                                 "}");
    }

    @Test(description = "Проверка полета с неактивными крыльями")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner,
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
        flyDuck(runner, id);
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"I can not fly :C\"\n" +
                                 "}");
    }

    @Test(description = "Проверка полета с определенными крыльями")
    @CitrusTest
    public void undefinedFly(@Optional @CitrusResource TestCaseRunner runner,
                             @Optional @CitrusResource TestContext context
                            ) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.UNDEFINED);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        flyDuck(runner, id);
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"Wings are not detected :(\"\n" +
                                 "}");
    }
}
