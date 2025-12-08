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

public class DuckQuackTest extends TestNGCitrusSpringSupport {

    String id;

    @Test(description = "Тест издаваемого звука, если id четный")
    @CitrusTest
    public void successefulQuackEvenNumbered(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        id = extractIdDuckFromResponse(runner);
        int repetitionCount = 2;
        int soundCount = 2;
        if (Integer.parseInt(id) % 2 == 0) {
            getQuackDuck(runner, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
        } else {
            createDuck(runner, color, height, material, sound, wingsState);
            getQuackDuck(runner, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"moo-moo, moo-moo\"\n}");
        }
    }

    @Test(description = "Тест издаваемого звука, если id нечетный")
    @CitrusTest
    public void successefulQuackOdd(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.3;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "ACTIVE";
        createDuck(runner, color, height, material, sound, wingsState);
        id = extractIdDuckFromResponse(runner);
        int repetitionCount = 2;
        int soundCount = 2;
        if (Integer.parseInt(id) % 2 == 0) {
            getQuackDuck(runner, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
        } else {
            createDuck(runner, color, height, material, sound, wingsState);
            getQuackDuck(runner, repetitionCount, soundCount);
            validateResponse(runner, "{\n\"sound\": \"quack-quack, quack-quack\"\n}");
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

    public String extractIdDuckFromResponse(TestCaseRunner runner) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type(MessageType.JSON)
                        .extract(fromBody().expression("$.id", "id"))
        );
        return "${id}";
    }

    public void getQuackDuck(TestCaseRunner runner, int repetitionCount, int soundCount) {

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
