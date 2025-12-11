package autotests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateParamTest extends DuckActionsClient {

//    DuckProperties duckProperties1 = new DuckProperties()
//            .color("yellow")
//            .height(0.05)
//            .material("rubber")
//            .sound("quack").wingsState("ACTIVE");
//    DuckProperties duckProperties2 = new DuckProperties()
//            .color("green")
//            .height(1.0)
//            .material("plastic")
//            .sound("meow")
//            .wingsState("FIXED");
//    @Test(dataProvider = "duckList")
//    @CitrusTest
//    @CitrusParameters({"payload", "response", "runner"})
//    public void successfulDuckCreate(Object payload, String response, @Optional @CitrusResource TestCaseRunner runner) {
//        createDuck(runner, payload);
//        validateResponse(runner, response);
//    }
//    @DataProvider(name = "duckList")
//    public Object[][] DuckProvider() {
//        return new Object[][] {
//                {duckProperties1, "getDuckPropertiesTest/duckYellowProperties.json", null},
//                {duckProperties2, "getDuckPropertiesTest/duckGreenProperties.json", null}
//        }
}
