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

public class DuckSwimTest extends TestNGCitrusSpringSupport {
    public static final String URL = "http://localhost:2222/";

    @Test(description = "Уточка плыви (существующий id)")
    @Parameters({"runner", "context"})
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
        validateResponse(runner, HttpStatus.NOT_FOUND, "{\n" +
                                         "\"message\":" + "\"Paws are not found ((((\"\n" +
                                         "}");}
    @Test(description = "Уточка плыви (несуществующий id)")
    @CitrusTest
    public void successfulSwimWrongId(@Optional @CitrusResource TestCaseRunner runner) {

        swimDuck(runner, "0");
        validateResponse(runner, HttpStatus.NOT_FOUND, "{\n" +
                                  "\"message\":" + "\"Paws are not found ((((\"\n" +
                                 "}");
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
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
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

    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id));

    }

    public void validateResponse(TestCaseRunner runner, HttpStatus expectedStatus, String responseMessage) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(expectedStatus)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage)
        );
    }
}
