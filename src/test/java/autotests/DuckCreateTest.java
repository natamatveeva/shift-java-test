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

public class DuckCreateTest extends TestNGCitrusSpringSupport {
    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        validateResponse(runner, "{\n" +
                                 "\"id\":" + 1 + ",\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + height + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
                                 "}");
    }
        @Test(description = "Создание деревянной уточки")
        @CitrusTest
        public void successfulCreate(@Optional @CitrusResource TestCaseRunner runner) {
            String color = "yellow";
            double height = 0.3;
            String material = "wood";
            String sound = "qack";
            String wingsState = "FIXED";
            createDuck(runner, color, height, material, sound, wingsState);
            validateResponse(runner, "{\n" +
                                 "\"id\":\"" + 2 + ",\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + height + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
                                 "}");
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client("http://localhost:2222")
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
