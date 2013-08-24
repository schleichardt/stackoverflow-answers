import org.junit.*;

import play.test.*;
import play.libs.F.*;

import java.util.concurrent.TimeUnit;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class WsTest {
    @Test
    public void testWebSocketsWithSession() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), FIREFOX, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/");
                final String selector = ".ws-output";
                browser.await().atMost(10, TimeUnit.SECONDS).until(selector).hasSize(2);
                assertThat(browser.$(selector, 0).getText()).isEqualTo("Hello fake-user");
                assertThat(browser.$(selector, 1).getText()).isEqualTo("Hello FAKE-USER");
            }
        });
    }
}
