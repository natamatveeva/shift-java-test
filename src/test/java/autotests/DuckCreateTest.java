package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckCreateTest extends TestNGCitrusSpringSupport {
    public static final String URL = "http://localhost:2222/";

    @Test(description = "Создание резиновой уточки")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "FIXED";
        createDuck(runner, color, height, material, sound, wingsState);
        validateResponseCreate(runner, color, height, material, sound, wingsState);
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
            validateResponseCreate(runner, color, height, material, sound, wingsState);
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

    public void validateResponseCreate(TestCaseRunner runner,
                                       String color, double height, String material,
                                       String sound, String wingsState) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response()
                        .message()
                        .type(MessageType.JSON)
                        .body("{\n" +
                              "\"id\":" + "\"@ignore@\"" + ",\n" +
                              "\"color\":\"" + color + "\",\n" +
                              "\"height\":" + height + ",\n" +
                              "\"material\":\"" + material + "\",\n" +
                              "\"sound\":\"" + sound + "\",\n" +
                              "\"wingsState\":\"" + wingsState + "\"\n" +
                              "}")
        );
    }
}
