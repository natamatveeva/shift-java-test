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

    String id;

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        // тест ломается из-за того, что не получается вставить id правильно (так я это вижу)...
        // как исправить не могу понять...
        // причем такое же использование id в другом тесте проходит
        id = extractIdDuckFromResponse(runner);
        validateResponse(runner, "{\n" +
                                 "\"id\":" + id + ",\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + height + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
                                 "}");
    }
        @Test(description = "Создание деревянной уточки")
        @CitrusTest
        public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
            String color = "yellow";
            double height = 0.3;
            String material = "wood";
            String sound = "qack";
            String wingsState = "FIXED";
            createDuck(runner, color, height, material, sound, wingsState);
            id = extractIdDuckFromResponse(runner);
            // тест ломается из-за того, что не получается вставить id правильно (так я это вижу)...
            // как исправить не могу понять...
            // причем такое же использование id в другом тесте проходит
            validateResponse(runner, "{\n" +
                                 "\"id\":\"" + id + ",\n" +
                                 "\"color\":\"" + color + "\",\n" +
                                 "\"height\":" + height + ",\n" +
                                 "\"material\":\"" + material + "\",\n" +
                                 "\"sound\":\"" + sound + "\",\n" +
                                 "\"wingsState\":\"" + wingsState + "\"\n" +
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
                        .client("http://localhost:2222")
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
    public String extractIdDuckFromResponse(TestCaseRunner runner) {
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
