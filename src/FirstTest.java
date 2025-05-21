import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    // Имя метода setUp не менять, так как оно установлено в Appium
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android"); // Достаточно для старта сессии Appium
        capabilities.setCapability("deviceName", "AndroidTestDevice"); //Для iOS имя должно соответствовать названию симулятора // Достаточно для старта сессии Appium
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia"); // Достаточно для старта сессии Appium
        capabilities.setCapability("appActivity", ".main.MainActivity"); // Достаточно для старта сессии Appium
        capabilities.setCapability("app", "C:/Users/3217_plz/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
//        System.out.println("First test run");

//        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text, 'Search Wikipedia')]");
//        element_to_init_search.click();

//        WebElement element_to_enter_search_line = driver.findElementByXPath("//*[contains(@text, 'Search…')]");
//        element_to_enter_search_line.sendKeys("Appium");


//        // Заменить метод Appium выше на Selenium для ожидания появления элемента на экране
//        WebElement element_to_enter_search_line = waitForElementPresentByXpath(
//                "//*[contains(@text, 'Search…')]",
//                "Cannot find search input", // Если не сработал клик на поле
//                5); // Удалить таймаут при использовании перегрузки метода,
//        // добавить таймаут обратно при использовании оригинального метода, какой вариант используется, можно проверить
//        // по Ctrl + click на waitForElementPresentByXpath выше
//        element_to_enter_search_line.sendKeys("Java");

        // Метод для закрытия онбординга в новой версии приложения
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find the 'Skip' button",
                5
        );

        // Рефакторинг element_to_init_search - шаг 2
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // Рефакторинг element_to_enter_search_line - шаг 2
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"), // 'Search…' заменено на 'Search Wikipedia' в новой версии приложения
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                // Часть //*[@resource-id='org.wikipedia:id/page_list_item_container'] в XPath удалена, так как отсутствует в новой версии приложения
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5 // Удалить таймаут при использовании перегрузки метода,
                // добавить таймаут обратно при использовании оригинального метода, какой вариант используется,
                // можно проверить по Ctrl + click на waitForElementPresentByXpath выше
        );
    }

    @Test
    public void testCancelSearch() {
        // Метод для закрытия онбординга в новой версии приложения
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find the 'Skip' button",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"), // 'Search…' заменено на 'Search Wikipedia' в новой версии приложения
                "Java",
                "Cannot find search input",
                5
        );

//        // Неактуально в новой версии приложения
//        waitForElementAndClear(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Cannot find search field",
//                5
//        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        // Метод для закрытия онбординга в новой версии приложения
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find the 'Skip' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // Рефакторинг element_to_enter_search_line - шаг 2
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"), // 'Search…' заменено на 'Search Wikipedia' в новой версии приложения
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                // Часть //*[@resource-id='org.wikipedia:id/page_list_item_container'] в XPath удалена, так как отсутствует в новой версии приложения
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        // Для получения заголовка статьи записать его в переменную
        WebElement title_element = waitForElementPresent(
//                By.id("org.wikipedia:id/view_page_title_text"), // Неактуально в новой версии приложения
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title", // При вводе одной этой строки будет определено как expected, после добавления остальных - message
                "Java (programming language)",
                article_title
        );
    }

    // Тест для ДЗ "Ex4*: Тест: проверка слов в поиске"
    @Test
    public void testHwEx4() {
        // Метод для закрытия онбординга в новой версии приложения
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find the 'Skip' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"), // 'Search…' заменено на 'Search Wikipedia' в новой версии приложения
                "java",
                "Cannot find search input",
                5
        );

        // Проверка всех результатов поиска на наличие "java" без учета регистра
        checkAllSearchResultsContainJava();
    }

//    // Добавить ожидание появления элемента на экране по XPath
//    private WebElement waitForElementPresentByXpath(String xpath, String error_message, long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
//        wait.withMessage(error_message + "\n");
//        By by = By.xpath(xpath);
//        return wait.until(
//                ExpectedConditions.presenceOfElementLocated(by)
//        );
//    }

    // Добавить ожидание появления элемента на экране по любому элементу - использование универсальных методов для поиска локаторов
    // waitForElementPresentByXpath везде переименован, String xpath заменено на By by (так же для waitForElementNotPresent)
    // Методы waitForElementByXpathAndClick, waitForElementByXpathAndSendKeys также изменены
    // waitForElementPresentById и waitForElementByIdAndClick в итоге удалены
    // В тестах выше строки с локаторами изменены на By.xpath и By.id
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    // Перегрузка метода waitForElementPresentByXpath - сейчас не используется, так как метод выше имеет таймаут
    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    // Рефакторинг element_to_init_search - шаг 1
    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    // Метод waitForElementByXpathAndClick переименован так, потому что ищет элементы по Xpath - так понятнее

    // Рефакторинг element_to_enter_search_line - шаг 1
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
    // Метод waitForElementByXpathAndSendKeys переименован так, потому что ищет элементы по XPath - так понятнее

//    // Добавить ожидание появления элемента на экране по XPath
//    private WebElement waitForElementPresentById(String id, String error_message, long timeoutInSeconds) {
//        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
//        wait.withMessage(error_message + "\n");
//        By by = By.id(id);
//        return wait.until(
//                ExpectedConditions.presenceOfElementLocated(by)
//        );
//    }

//    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds) {
//        WebElement element = waitForElementPresentById(id, error_message, timeoutInSeconds);
//        element.click();
//        return element;
//    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

//    // Неактуально в новой версии приложения
//    // Метод для очистки поля, добавлено в тест testCancelSearch (перед waitForElementAndClear добавить waitForElementAndSendKeys)
//    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
//        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
//        element.clear();
//        return element;
//    }

    // Метод для ДЗ "Ex4*: Тест: проверка слов в поиске"
    public void checkAllSearchResultsContainJava() {
        // Поиск всех элементов по указанному локатору
        List<WebElement> results = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));

        // Логирование количества найденных элементов
        System.out.println("Found " + results.size() + " results:");

        // Проверка найденных элементов
        if (results.isEmpty()) {
            throw new AssertionError("No search results found.");
        } else {
            // Перебор найденных элементов и вывод их текста
            for (WebElement result : results) {
                String text = result.getText();
                System.out.println("Result text: " + text);
                // Проверка текста на наличие "java" без учета регистра
                if (!text.toLowerCase().contains("java")) {
                    throw new AssertionError("Found a result that does not contain the word 'java': " + text);
                }
            }
        }
    }
}
