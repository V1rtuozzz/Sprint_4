package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {
    private WebDriver driver;

    //Кнопка "Заказать" в шапке
    private By mainOrderButton = By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/button[1]");
    //Кнопка "Заказать" внизу
    private By midOrderButton = By.xpath("//*[@id=\"root\"]/div/div/div[4]/div[2]/div[5]/button");
    //Блок вопросов
    private By questionsList = By.className("accordion__button");
    //Блок ответов
    private By answersList = By.className("accordion__panel");
    //Cookies
    private By cookiesWarning = By.xpath("//*[@id=\"rcc-confirm-button\"]");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        WebElement cookies = driver.findElement(cookiesWarning);
        if (cookies.isDisplayed()) {
            cookies.click();
        }
    }

    public void clickMainOrderButton() {
        driver.findElement(mainOrderButton).click();
    }

    public void clickMidOrderButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement element = driver.findElement(midOrderButton);

        js.executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    public List<WebElement> getQuestionsListElements() {
        return driver.findElements(questionsList);
    }

    public List<WebElement> getAnswersListElements() {
        return driver.findElements(answersList);
    }
}