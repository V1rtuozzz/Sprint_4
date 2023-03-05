import PageObjects.MainPage;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CheckFAQ {
    public WebDriver driver;
    public final String questionHeader, answer;

    public CheckFAQ(String questionHeader, String answer) {
        this.questionHeader = questionHeader;
        this.answer = answer;
    }

    @Parameterized.Parameters
    public static Object[][] getFaqs() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Test
    public void checkFaqList() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        List<WebElement> questionsList = mainPage.getQuestionsListElements();
        List<WebElement> answersList = mainPage.getAnswersListElements();
        for (int i = 0; i < questionsList.size(); i++) {
            WebElement element = questionsList.get(i);
            if (element.getText().equals(questionHeader)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
                element.click();
                WebElement targetElement = answersList.get(i);
                new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(targetElement));
                assertEquals("Текст ответа на вопрос №" + (i + 1) + " не совпадает с ожидаемым", answer, targetElement.getText());
                break;
            }
        }
        driver.quit();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}