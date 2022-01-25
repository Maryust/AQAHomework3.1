package ru.netology.win;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldSendCorrectForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ус Иннокентий");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText, actualText);
    }
    @Test
    public void shouldSendFormWithoutName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }
    @Test
    public void shouldSendFormWithForeignName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Oz Albert");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText, actualText);
    }
    @Test
    public void shouldSendFormWithoutNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ус Иннокентий");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }
    @Test
    public void shouldSendFormWithWrongNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ус Иннокентий");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("89993332211");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText, actualText);
    }
    @Test
    public void shouldSendFormWithoutCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ус Иннокентий");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993332211");
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .checkbox__text")).getText().trim();
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expectedText, actualText);
    }
}

