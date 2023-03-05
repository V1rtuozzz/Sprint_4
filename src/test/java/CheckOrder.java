import PageObjects.MainPage;
import PageObjects.OrderPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class CheckOrder {

    private final boolean useDownButton;
    private final String name, surname, address, metro, phone, date, comment;
    private final String color;
    private final int days;

    public WebDriver driver;

    public CheckOrder(boolean useDownButton, String name, String surname, String address, String metro, String phone, String date, int days, String color, String comment) {
        this.useDownButton = useDownButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.color = color;
        this.days = days;
    }

    @Parameterized.Parameters
    public static Object[][] getFaqs() {
        return new Object[][]{
                {true, "Том", "Харди", "Москва", "Сокольники", "89655967777", "05.03.2023", 5, "чёрный жемчуг", "Быстрее"},
                {true, "Tom", "Hardy", "Moscow", "Лубянка", "9655967777", "03.03.2023", 10, "серая безысходность", ""},
        };
    }

    @Test
    public void orderTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        MainPage mainPage = new MainPage(driver);
        mainPage.openPage();
        if (useDownButton) {
            mainPage.clickMidOrderButton();
        } else {
            mainPage.clickMainOrderButton();
        }
        OrderPage orderPage = new OrderPage(driver);
        orderPage.enterFirstPageAndProceed(name, surname, address, metro, phone);
        orderPage.enterSecondPageAndPlaceOrder(date, days, color, comment);
        Assert.assertTrue("Заказ сформирован", orderPage.orderPlaced());
    }


    @After
    public void tearDown() {
        driver.quit();
    }
}