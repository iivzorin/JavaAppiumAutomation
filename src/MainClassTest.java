import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MainClassTest extends MainClass {
    @Test
    public void testGetLocalNumber() {
        int result = this.getLocalNumber();
        Assert.assertEquals("Ошибка: метод getLocalNumber должен возвращать число 14", 14, result);
    }

    @Test
    public void testGetClassNumber() {
        MainClass mainClass = new MainClass();
        int result = mainClass.getClassNumber();
        assertTrue("Ошибка: метод getClassNumber должен возвращать число больше 45", result > 45);
    }
}