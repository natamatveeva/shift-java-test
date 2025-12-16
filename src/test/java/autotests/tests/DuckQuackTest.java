package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "Тест издаваемого звука, если id четный")
    @CitrusTest
    public void successefulQuackEvenNumbered(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
                                             TestContext context) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        int repetitionCount = 2;
        int soundCount = 2;
        if (Integer.parseInt(id) % 2 == 0) {
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
        } else {
            createDuck(runner, color, height, material, sound, wingsState);
            id = extractIdDuckFromResponse(runner, context);
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
        }
    }

    @Test(description = "Тест издаваемого звука, если id нечетный")
    @CitrusTest
    public void successefulQuackOdd(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
            TestContext context) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        int repetitionCount = 2;
        int soundCount = 2;
        if (Integer.parseInt(id) % 2 == 1) {
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
        } else {
            createDuck(runner, color, height, material, sound, wingsState);
            id = extractIdDuckFromResponse(runner, context);
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
        }
    }
}
