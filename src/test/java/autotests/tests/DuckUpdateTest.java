package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckActionsClient {

    @Test(description = "Обновление параметров утки: высота и цвет")
    @CitrusTest
    public void updateDuckParametersColorHeight(@Optional @CitrusResource TestCaseRunner runner,
                                                @Optional @CitrusResource TestContext context
                                                ) {
        String color = "black";
        double height = 0.5;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
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
        String color = "violet";
        double height = 1;
        String material = "metall";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
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