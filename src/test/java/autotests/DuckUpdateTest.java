package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.MessageSupport;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckUpdateTest extends TestNGCitrusSpringSupport {
    public static final String URL = "http://localhost:2222/";

    @Test(description = "Обновление параметров утки: высота и цвет")
    @Parameters({"runner", "context"})
    @CitrusTest
    public void updateDuckParametersColorHeight(
            @Optional @CitrusResource TestCaseRunner runner,
            @Optional @CitrusResource TestContext context
    ) {
        String color = "black";
        double height = 0.5;
        String material = "rubber";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        updateDuck(runner, id,
                "white",
                3.5,
                "rubber",
                "quack",
                "ACTIVE"
        );
        validateResponse(runner, "{\n" +
                                 "\"message\": \"Duck with id = " + id + " is updated\"\n" +
                                 "}");
    }

    @Test(description = "Обновление параметров утки: цвет и звук")
    @Parameters({"runner", "context"})
    @CitrusTest
    public void updateDuckParametersColorSound(
            @Optional @CitrusResource TestCaseRunner runner,
            @Optional @CitrusResource TestContext context
    ) {
        String color = "violet";
        double height = 1;
        String material = "metall";
        String sound = "qack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        String id = extractIdDuckFromResponse(runner, context);
        updateDuck(runner, id,
                "white",
                1,
                "metall",
                "qa",
                "ACTIVE"
        );
        validateResponse(runner, "{\n" +
                                 "\"message\": \"Duck with id = " + id + " is updated\"\n" +
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
                        .extract(MessageSupport.MessageBodySupport.fromBody().expression("$.id", "id"))
        );
        return context.getVariable("${id}");
    }

    public void updateDuck(
            TestCaseRunner runner,
            String idDuck,
            String newColor,
            double newHeight,
            String newMaterial,
            String newSound,
            String newWingsState
    ) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .put("/api/duck/update")
                        .queryParam("id", idDuck)
                        .queryParam("color", newColor)
                        .queryParam("height", String.valueOf(newHeight))
                        .queryParam("material", newMaterial)
                        .queryParam("sound", newSound)
                        .queryParam("wingsState", newWingsState));
    }

    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(responseMessage));
    }
}