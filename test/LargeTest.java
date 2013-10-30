import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class LargeTest {
    @Test
    public void simpleCheck() {
        assertThat(System.getProperty("mysetting")).isEqualTo("1");
    }
}
