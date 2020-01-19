import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class KullaniciSenaryoImplementation
{
    static WebDriver driver;

    @Step("Kullanıcı <kullaniciAdi>,Parola <sifre> İle Login Olunur")
    public void kullaniciGirisi(String kullaniciAdi,String sifre) throws InterruptedException {
        setUp();


        driver.navigate().to("https://www.hepsiburada.com/");

        findElement(By.cssSelector("Span.profile-name")).click();
        //driver.findElement(By.cssSelector("Span[class='profile-name ']")).click();  Çalışıyor
        //findElement(By.id("L-UserNameField")).sendKeys("sts.cetinsemih@gmail.com");
        //findElement(By.id("L-PasswordField")).sendKeys("Aa9090900");

        /*
        sendKeys(By.id("L-UserNameField"),"sts.cetinsemih@gmail.com");
        sendKeys(By.id("L-PasswordField"),"08110045034");
        clickElement(By.id("gg-login-enter"));
         */

        Thread.sleep(3000);

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
