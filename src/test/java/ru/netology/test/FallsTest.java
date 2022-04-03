package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FallsTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void filledNameInAnotherLanguageTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Semyon Gorbunkov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector(".input_invalid[data-test" +
                "-id=name] .input__sub")).getText().trim());
    }

    @Test
    public void numbersInsteadOfLettersTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("222 333");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector(".input_invalid[data-test" +
                        "-id=name] .input__sub")).getText().trim());
    }

    @Test
    public void emptyPhoneFieldTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Семен Горбунков");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector(".input_invalid[data-test" +
                "-id=phone] .input__sub")).getText().trim());
    }

    @Test
    public void emptyFieldNameTest() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector(".input_invalid[data-test" +
                "-id=name] .input__sub")).getText().trim());
    }

    @Test
    public void invalidFormClickTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Семен Горбунков");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",
                driver.findElement(By.cssSelector(".input_invalid[data-test-id=agreement]")).getText().trim());
    }

    @Test
    public void incompletePhoneNumberEnteredTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Семен Горбунков");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector(".input_invalid[data-test" +
                "-id=phone] .input__sub")).getText().trim());
    }

    @Test
    public void lettersInsteadOfNumbersTelTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Семен Горбунков");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+a9c111111b");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector(".input_invalid[data-test" +
                        "-id=phone] .input__sub")).getText().trim());
    }
}
