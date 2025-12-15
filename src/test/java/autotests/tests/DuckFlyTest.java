package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckActionsClient {

    @Test(description = "Проверка полета с активными крыльями")
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner,
                              @Optional @CitrusResource TestContext context
                              ) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
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
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
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
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "UNDEFINED";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        flyDuck(runner, id);
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"Wings are not detected :(\"\n" +
                                 "}");
    }
}
