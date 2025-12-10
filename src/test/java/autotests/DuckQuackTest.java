package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "Тест издаваемого звука, если id четный")
    @CitrusTest
    public void successefulQuackEvenNumbered(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
                                             TestContext context) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.ACTIVE);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        int repetitionCount = 2;
        int soundCount = 2;
        if (Integer.parseInt(id) % 2 == 0) {
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
        } else {
            createDuck(runner, duckProperties);
            id = extractIdDuckFromResponse(runner, context);
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
        }
    }

    @Test(description = "Тест издаваемого звука, если id нечетный")
    @CitrusTest
    public void successefulQuackOdd(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
            TestContext context) {
        DuckPropertiesCreate duckProperties = new DuckPropertiesCreate()
                .color("yellow")
                .height(0.3)
                .material("rubber")
                .sound("quack")
                .wingsState(DuckPropertiesCreate.WingsState.ACTIVE);
        createDuck(runner, duckProperties);
        String id = extractIdDuckFromResponse(runner, context);
        int repetitionCount = 2;
        int soundCount = 2;
        if (Integer.parseInt(id) % 2 == 1) {
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
        } else {
            createDuck(runner, duckProperties);
            id = extractIdDuckFromResponse(runner, context);
            getQuackDuck(runner, id, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
        }
    }
}
