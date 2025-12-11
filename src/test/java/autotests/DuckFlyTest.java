package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@Epic("Тесты duck-action-controller")
@Feature("Действие Лететь")
@Story("Endpoint /api/duck/action/fly")
public class DuckFlyTest extends DuckActionsClient {

    @Test(description = "Проверка полета с активными крыльями")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","4");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'white', 3.0, 'rubber', 'quack','ACTIVE');");
        flyDuck(runner, "${duckId}");
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"I am flying :)\"\n" +
                                 "}");
    }

    @Test(description = "Проверка полета с неактивными крыльями")
    @CitrusTest
    public void unsuccessfulFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","5");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'white', 3.0, 'rubber', 'quack','FIXED');");
        flyDuck(runner, "${duckId}");
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"I can not fly :C\"\n" +
                                 "}");
    }

    @Test(description = "Проверка полета с неопределенными крыльями")
    @CitrusTest
    public void undefinedFly(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","6");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'white', 3.0, 'rubber', 'quack','UNDEFINED');");
        flyDuck(runner, "${duckId}");
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"Wings are not detected :(\"\n" +
                                 "}");
    }
}
