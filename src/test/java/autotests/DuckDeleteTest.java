package autotests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckPropertiesCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@Epic("Тесты duck-controller")
@Feature("Удалить утку")
@Story("Endpoint /api/duck/delete")
public class DuckDeleteTest extends DuckActionsClient {

    @Test(description="Удаление утки")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId","3");
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${duckId}, 'orange', 3.0, 'rubber', 'quack','ACTIVE');");
        databaseUpdate(runner, "delete from DUCK where id=${duckId}");
        // не могу сообразить как провести проверку удаления
    }
}
