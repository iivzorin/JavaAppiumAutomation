import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {
    @Test
    public void testGetLocalNumber() {
        int result = this.getLocalNumber();
        Assert.assertEquals("Ошибка: метод getLocalNumber должен возвращать число 14", 14, result);
    }
}