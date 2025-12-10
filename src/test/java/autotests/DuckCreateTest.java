package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckActionsClient {

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.FIXED);
        createDuck(runner, duckProperties);
        validateResponseCreate(runner, "yellow", 0.3, "rubber", "quack", DuckPropertiesCreate.WingsState.FIXED);
    }

    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("wood")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.FIXED);
        createDuck(runner, duckProperties);
        validateResponseCreate(runner, "yellow", 0.3, "wood", "quack", DuckPropertiesCreate.WingsState.FIXED);
    }
}
