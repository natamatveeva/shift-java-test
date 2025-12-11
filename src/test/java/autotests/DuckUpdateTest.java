package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import autotests.payloads.ResponseMessage;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@Epic("Тесты duck-controller")
@Feature("Обновление свойств")
@Story("Endpoint /api/duck/update")
public class DuckUpdateTest extends DuckActionsClient {

    @Test(description = "Обновление параметров утки: высота и цвет")
    @CitrusTest
    public void updateDuckParametersColorHeight(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","10");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'orange', 3.0, 'rubber', 'quack','ACTIVE');");
        updateDuck(runner, "${duckId}",
                "white",
                3.5,
                "rubber",
                "quack",
                "ACTIVE");
        ResponseMessage message = new ResponseMessage()
                .message("{\n\"message\": \"Duck with id = " + "${duckId}" + " is updated\"\n}");
        validateResponse(runner, message.message());
        validateDuckInDatabase(runner, "${duckId}", "white", "3.5", "rubber", "quack", "ACTIVE");

    }

    @Test(description = "Обновление параметров утки: цвет и звук")
    @CitrusTest
    public void updateDuckParametersColorSound(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","11");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'orange', 3.0, 'rubber', 'quack','ACTIVE');");
        updateDuck(runner, "${duckId}",
                "white",
                3.0,
                "rubber",
                "qa",
                "ACTIVE");
        validateResponse(runner, "{\n" +
                                 "\"message\": \"Duck with id = " + "${duckId}" + " is updated\"\n" +
                                 "}");
        validateDuckInDatabase(runner, "${duckId}", "white", "3.0", "rubber", "qa", "ACTIVE");

    }
}