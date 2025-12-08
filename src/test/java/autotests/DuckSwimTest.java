package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckSwimTest extends TestNGCitrusSpringSupport {
    @Test(description = "Уточка плыви (существующий id)")
    @CitrusTest
    public void successfulSwimRightId(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        swimDuck(runner, extractDataFromResponse(runner));
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"I'm swimming\"\n" +
                                 "}");
    }
    @Test(description = "Уточка плыви (несуществующий id)")
    @CitrusTest
    public void successfulSwimWrongId(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        swimDuck(runner, extractDataFromResponse(runner));
        validateResponse(runner, "{\n" +
                                 "\"message\":" + "\"Paws are not found ((((\"\n" +
                                 "}");
    }

    //---
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
                        .client("http://localhost:2222")
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .body("{\n\"color\":\"" +
                              color +
                              "\",\n\"height\":" +
                              height +
                              "\",\n\"material\":" +
                              material +
                              "\",\n\"sound\":" +
                              sound +
                              "\",\n\"wingsState\":" +
                              wingsState)
        );
    }

    public String extractDataFromResponse(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .extract(fromBody().expression("$.id","id"))
        );
        return "${id}";
    }

    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id));

    }

    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage)
        );
    }
}
