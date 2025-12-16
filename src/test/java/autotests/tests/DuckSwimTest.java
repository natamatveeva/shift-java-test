package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckActionsClient {

    @Test(description = "Уточка плыви (существующий id)")
    @CitrusTest
    public void successfulSwimRightId(@Optional @CitrusResource TestCaseRunner runner,
                                      @Optional @CitrusResource TestContext context) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        swimDuck(runner, id);
        validateResponse(runner, "{\n" +
                                         "\"message\":" + "\"Paws are not found ((((\"\n" +
                                         "}");}

    @Test(description = "Уточка плыви (несуществующий id)")
    @CitrusTest
    public void successfulSwimWrongId(@Optional @CitrusResource TestCaseRunner runner,
                                      @Optional @CitrusResource TestContext context) {
        swimDuck(runner, "0");
        validateResponse(runner, "{\n" +
                                  "\"message\":" + "\"Paws are not found ((((\"\n" +
                                 "}");
    }
}
