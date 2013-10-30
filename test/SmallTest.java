import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SmallTest {
    @Test
    public void simpleCheck() {
        assertThat(System.getProperty("mysetting")).isNull();
    }
}
