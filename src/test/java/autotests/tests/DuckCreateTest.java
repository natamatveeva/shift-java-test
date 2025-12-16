package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckActionsClient {

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner
                                      ) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        validateResponseCreate(runner, color, height, material, sound, wingsState);
    }

    @Test(description = "Создание деревянной уточки")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner
                                    ) {
        String color = "yellow";
        double height = 0.3;
        String material = "wood";
        String sound = "quack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        validateResponseCreate(runner, color, height, material, sound, wingsState);
    }
}
