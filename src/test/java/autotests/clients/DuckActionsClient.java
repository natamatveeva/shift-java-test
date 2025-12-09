package autotests.clients;

import autotests.tests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.net.http.HttpClient;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

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

    public void swimDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id));

    }

    public void flyDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id));

    }

    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client("http://localhost:2222")
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id));
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
                        .client("http://localhost:2222")
                        .send()
                        .put("/api/duck/update")
                        .queryParam("id", idDuck)
                        .queryParam("color", newColor)
                        .queryParam("height", String.valueOf(newHeight))
                        .queryParam("material", newMaterial)
                        .queryParam("sound", newSound)
                        .queryParam("wingsState", newWingsState));
    }
}
