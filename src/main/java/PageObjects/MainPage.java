package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {
    private WebDriver driver;

    //Заказ в шапке
    private By upperOrderButton = By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/button[1]");
    //Заказ внизу
    private By downOrderButton = By.xpath("//*[@id=\"root\"]/div/div/div[4]/div[2]/div[5]/button");
    //Блок вопросов
    private By questionsList = By.className("accordion__button");
    //Блок ответов
    private By answersList = By.className("accordion__panel");
    //Кукисы
    private By cookiesWarning = By.xpath("//*[@id=\"rcc-confirm-button\"]");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        WebElement cookiesEle = driver.findElement(cookiesWarning);
        if (cookiesEle.isDisplayed()) {
            cookiesEle.click();
        }
    }

    public void clickUpperOrderButton() {
        driver.findElement(upperOrderButton).click();
    }

    public void clickDownOrderButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement element = driver.findElement(downOrderButton);

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