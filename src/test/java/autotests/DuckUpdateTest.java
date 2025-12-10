package autotests.duck_controller;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class DuckUpdateTest extends DuckActionsClient {

    @Test(description = "Обновление параметров утки: высота и цвет")
    @CitrusTest
    public void updateDuckParametersColorHeight(@Optional @CitrusResource TestCaseRunner runner,
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
        updateDuck(runner, id,
                "white",
                3.5,
                "rubber",
                "quack",
                "ACTIVE");
        validateResponse(runner, "{\n" +
                                 "\"message\": \"Duck with id = " + id + " is updated\"\n" +
                                 "}");
    }

    @Test(description = "Обновление параметров утки: цвет и звук")
    @CitrusTest
    public void updateDuckParametersColorSound(@Optional @CitrusResource TestCaseRunner runner,
                                               @Optional @CitrusResource TestContext context) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.ACTIVE);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        updateDuck(runner, id,
                "white",
                1,
                "metall",
                "qa",
                "ACTIVE");
        validateResponse(runner, "{\n" +
                                 "\"message\": \"Duck with id = " + id + " is updated\"\n" +
                                 "}");
    }
}