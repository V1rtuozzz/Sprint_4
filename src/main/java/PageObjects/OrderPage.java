package PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private WebDriver driver;

    private String xpathRoot = "//*[@id=\"root\"]/div/div[2]/div[2]";

    //Имя
    private final By orderName = By.xpath(xpathRoot + "/div[1]/input");
    //Фамилия
    private final By orderSurname = By.xpath(xpathRoot + "/div[2]/input");
    //Куда привезти
    private final By orderAddress = By.xpath(xpathRoot + "/div[3]/input");
    //Станция метро список
    private final By orderMetro = By.xpath(xpathRoot + "/div[4]/div/div/input");
    //Номер телефона
    private final By orderPhone = By.xpath(xpathRoot + "/div[5]/input");

    //Кнопка Далее
    private final By nextButton = By.className("Button_Middle__1CSJM");

    //Дата заказа
    private final By orderDate = By.xpath(xpathRoot + "/div[1]/div/div/input");

    //Количество дней заказа
    private final By orderDays = By.xpath(xpathRoot + "/div[2]/div[1]/div[1]");
    //Список с количеством дней
    private final By orderDayLength = By.className("Dropdown-option");
    //Чекбоксы цветов самоката
    private final By orderColor = By.className("Checkbox_Label__3wxSf");
    //Комментарий
    private final By orderComment = By.xpath(xpathRoot + "/div[4]/input");

    //Кнопка Заказать
    private final By getOrderFinal = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]");
    //Кнопка Точно Заказать
    private final By orderConfirm = By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div[2]/button[2]");

    //Окно подтверждения заказа
    private final By orderCreated = By.xpath("//div[text()='Заказ оформлен']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        driver.findElement(orderName).sendKeys(name);
    }

    public void enterSurname(String surname) {
        driver.findElement(orderSurname).sendKeys(surname);
    }

    public void enterAddress(String address) {
        driver.findElement(orderAddress).sendKeys(address);
    }

    public void enterMetro(String metro) {
        driver.findElement(orderMetro).sendKeys(metro);
        driver.findElement(orderMetro).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(orderMetro).sendKeys(Keys.ENTER);
    }

    public void enterPhone(String phone) {
        driver.findElement(orderPhone).sendKeys(phone);
    }

    public void proceed() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement nexButtonElement = driver.findElement(nextButton);
        js.executeScript("arguments[0].scrollIntoView();", nexButtonElement);
        nexButtonElement.click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(orderDate));
    }

    public void enterFirstPageAndProceed(String name, String surname, String address, String metro, String phone) {
        enterName(name);
        enterSurname(surname);
        enterAddress(address);
        enterMetro(metro);
        enterPhone(phone);
        proceed();
    }

    public void enterDate(String date) {
        driver.findElement(orderDate).sendKeys(date);
        driver.findElement(orderDate).sendKeys(Keys.ENTER);
    }

    public void enterDays(int days) {
        int enteredDays = 1;
        if (days > 7) {
            enteredDays = 7;
        } else if (days < 1) {
            enteredDays = 1;
        } else enteredDays = days;

        driver.findElement(orderDays).click();
        driver.findElements(orderDayLength).get(enteredDays - 1).click();
    }

    public void selectColors(String color) {
        List<WebElement> coloursList = driver.findElements(orderColor);
        for (WebElement listElement : coloursList) {
            if (listElement.getText().equals(color)) {
                listElement.click();
            }
        }
    }

    public void enterComment(String comment) {
        driver.findElement(orderComment).sendKeys(comment);
    }

    public void confirmOrderComplete() {
        driver.findElement(getOrderFinal).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(orderConfirm));
        driver.findElement(orderConfirm).click();
    }

    public void enterSecondPageAndPlaceOrder(String date, int days, String color, String comment) {
        enterDate(date);
        enterDays(days);
        selectColors(color);
        enterComment(comment);
        confirmOrderComplete();
    }

    public boolean orderPlaced() {
        return driver.findElement(orderCreated).isDisplayed();
    }
}