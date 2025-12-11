package autotests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-action-controller")
@Feature("Получить свойства утки")
@Story("Endpoint /api/duck/action/properties")
public class DuckPropertiesTest extends DuckActionsClient {

    @Test(description = "Вывод параметров уточки (id целое четное)")
    @CitrusTest
    public void getPropertiesEvenNumbered(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","12");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'orange', 3.0, 'rubber', 'quack','ACTIVE');");
        validateDuckInDatabase(runner, "${duckId}", "orange", "3.0", "rubber", "quack", "ACTIVE");
        String responseMessage = "{\n" +
                                 "\"color\": \"orange\",\n" +
                                 "\"height\": " + 300.0 + ",\n" +
                                 "\"material\": \"rubber\",\n" +
                                 "\"sound\": \"quack\",\n" +
                                 "\"wingsState\": \"ACTIVE\"\n" +
                                 "}";

        getPropertiesDuck(runner, "${duckId}");
        validateResponse(runner, responseMessage);

    }

    @Test(description = "Вывод параметров уточки (id целое нечетное)")
    @CitrusTest
    public void getPropertiesOdd(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","13");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'black', 3.5, 'metall', 'quack','ACTIVE');");
        validateDuckInDatabase(runner, "${duckId}", "black", "3.5", "metall", "quack", "ACTIVE");
        String responseMessage = "{\n" +
                                 "\"color\": \"black\",\n" +
                                 "\"height\": " + 350.0 + ",\n" +
                                 "\"material\": \"metall\",\n" +
                                 "\"sound\": \"quack\",\n" +
                                 "\"wingsState\": \"ACTIVE\"\n" +
                                 "}";

        getPropertiesDuck(runner, "${duckId}");
        validateResponse(runner, responseMessage);

    }
}
