package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckCreateTest extends DuckActionsClient {

    @Test(description = "создание уточки на прямую в базе")
    @CitrusTest
    public void successfulCreateDb(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","1");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'blue', 1.0, 'rubber', 'quack','ACTIVE');");
        validateDuckInDatabase(runner, "${duckId}", "blue", "1.0", "rubber", "quack", "ACTIVE");
    }
}
