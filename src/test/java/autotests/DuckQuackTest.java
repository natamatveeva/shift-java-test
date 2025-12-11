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
@Feature("Действие Крякать")
@Story("Endpoint /api/duck/action/quack")
public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "Тест издаваемого звука, если id четный")
    @CitrusTest
    public void successefulQuackEvenNumbered(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","8");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'white', 3.0, 'rubber', 'quack','UNDEFINED');");
        int repetitionCount = 2;
        int soundCount = 2;
        getQuackDuck(runner, "${duckId}", repetitionCount, soundCount);
        validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
    }

    @Test(description = "Тест издаваемого звука, если id нечетный")
    @CitrusTest
    public void successefulQuackOdd(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "7");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(
                runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'white', 3.0, 'rubber', 'quack','UNDEFINED');"
        );
        int repetitionCount = 2;
        int soundCount = 2;
        getQuackDuck(runner, "${duckId}", repetitionCount, soundCount);
        validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
    }
}
