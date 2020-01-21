import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class kullanıcıSenaryoYedek
{
    static WebDriver driver;

    @Step("Kullanıcı <kullaniciAdi>,Parola <sifre> İle Login Olunur2")
    public void kullaniciGirisi(String kullaniciAdi,String sifre) throws InterruptedException {
        setUp();

        driver.navigate().to("https://www.hepsiburada.com/");
        findElement(By.id("myAccount")).click();
        findElement(By.id("login")).click();
        findElement(By.id("email")).sendKeys(kullaniciAdi);
        findElement(By.id("password")).sendKeys(sifre);
        findElement(By.cssSelector("button[class='btn full btn-login-submit']")).click();

        Thread.sleep(5000);

        driver.quit();
    }

    //gönderilen navigatör ile elementi bulur
    public static WebElement findElement(By by)
    {
        return driver.findElement(by);
    }

    //Webdriverin yerini gösterip driver değişkenine atama yapar
    public static void setUp()
    {
        System.setProperty("webdriver.chrome.driver","properties/driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    //navigatörü gönderilen elemente tıklanır
    public static void clickElement(By by)
    {
        findElement(by).click();
    }

    //navigatörü gönderilen elemente değer girilir
    public static void sendKeys(By by,String metin)
    {
        findElement(by).sendKeys(metin);
    }

}
