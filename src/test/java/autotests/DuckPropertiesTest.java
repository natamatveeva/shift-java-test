package autotests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesTest extends DuckActionsClient {

    @Test(description = "Вывод параметров уточки (id целое четное)")
    @Parameters({"runner", "context"})
    @CitrusTest
    public void getPropertiesEvenNumbered(@Optional @CitrusResource TestCaseRunner runner,
                                          @Optional @CitrusResource TestContext context
    ) {
        String color = "yellow";
        double height = 1.0;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        String responseMessage = "{\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + 100.0 + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
                                 "}";
        if (Integer.parseInt(id) % 2 == 0) {
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        } else {
            createDuck(runner, color, height, material, sound, wingsState);
            id = extractIdDuckFromResponse(runner, context);
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        }
    }

    @Test(description = "Вывод параметров уточки (id целое нечетное)")
    @CitrusTest
    public void getPropertiesOdd(@Optional @CitrusResource TestCaseRunner runner,
                                 @Optional @CitrusResource TestContext context
    ) {
        String color = "yellow";
        double height = 1.0;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        String responseMessage = "{\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + 100.0 + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
                                 "}";
        ;
        if (Integer.parseInt(id) % 2 == 1) {
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        } else {
            createDuck(runner, color, height, material, sound, wingsState);
            id = extractIdDuckFromResponse(runner, context);
            getPropertiesDuck(runner, id);
            validateResponse(runner, responseMessage);
        }
    }
}
