package autotests;

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

public class DuckPropertiesTest extends TestNGCitrusSpringSupport {
    public static final String URL = "http://localhost:2222/";

    @Test(description = "Вывод параметров уточки (id целое четное)")
    @Parameters({"runner", "context"})
    @CitrusTest
    public void getPropertiesEvenNumbered(@Optional @CitrusResource TestCaseRunner runner,
                                          @Optional @CitrusResource TestContext context) {
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
    public void getPropertiesOdd(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource
            TestContext context) {
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
                                 "}";;
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

    public void createDuck(
            TestCaseRunner runner,
            String color,
            double height,
            String material,
            String sound,
            String wingsState
    ) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .post("/api/duck/create")
                        .message().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\n" +
                              "\"color\":\"" + color + "\",\n" +
                              "\"height\":" + height + ",\n" +
                              "\"material\":\"" + material + "\",\n" +
                              "\"sound\":\"" + sound + "\",\n" +
                              "\"wingsState\":\"" + wingsState + "\"\n" +
                              "}")
        );
    }

    public String extractIdDuckFromResponse(TestCaseRunner runner, TestContext context) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .extract(fromBody().expression("$.id", "id"))
        );
        return context.getVariable("${id}");
    }

    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id));
    }

    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage)
        );
    }

}
