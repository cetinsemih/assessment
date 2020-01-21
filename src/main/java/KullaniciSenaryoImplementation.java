import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class KullaniciSenaryoImplementation
{
    static WebDriver driver;
    static Random randomSayi = new Random();
    String sepetTutari;
    int sepetUrunSayisi,sayiTut;


    @Step("Kullanıcı <kullaniciAdi>,Parola <sifre> İle Login Olunur")
    public void kullaniciGirisi(String kullaniciAdi,String sifre) throws InterruptedException {
        setUp();



        driver.navigate().to("https://www.hepsiburada.com/");
        login(kullaniciAdi,sifre);
        urunEkraninaGiris();











        //---------------------------















        driver.quit();
    }

    //gönderilen navigatör ile elementi bulur
    public static WebElement findElement(By by)
    {
        return driver.findElement(by);
    }

    //gönderilen navigatör ile elementleri bulur
    public static ArrayList<WebElement> findElementList(By by)
    {
        return (ArrayList<WebElement>) driver.findElements(by);
    }

    //Webdriverin yerini gösterip driver değişkenine atama yapar
    public static void setUp()
    {
        System.setProperty("webdriver.chrome.driver","properties/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
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

    //Gönderilen Değer Kadar Bekler
    public static void bekle(int sure) throws InterruptedException
    {
        Thread.sleep(sure*1000);
    }

    //navigatörü gönderilen elementin textini döner
    public static String elementGetText(By by)
    {
        String tut = findElement(by).getText();
        System.out.println(tut);
        return tut;
    }

    //Navigatörü Gönderilen Elementin Aktiflik Durumunu Döner
    public static boolean elementIsEnabled(By by)
    {
        boolean tut = findElement(by).isEnabled();
        if (tut) System.out.println(by+ "doğru");
        if (!tut) System.out.println(by+ "doğru değil");
        return tut;
    }

    //Gönderilen Sayı ile 0 Arasında Random Sayı Üretir
    public static int randomSayi(int sayi)
    {
        int gonderilecekSayi = randomSayi.nextInt(sayi);
        System.out.println(gonderilecekSayi);
        return gonderilecekSayi;
    }




    //------------------

    // Gelen Bilgiler İle Login Olur, Login Kontrolü Yapar , Seper Tutarını döner
    public String login(String kullaniciAdi,String sifre) throws InterruptedException {
        driver.navigate().to("https://www.hepsiburada.com/");
        clickElement(By.id("myAccount"));
        clickElement(By.id("login"));
        sendKeys(By.id("email"),kullaniciAdi);
        sendKeys(By.id("password"),sifre);
        clickElement(By.cssSelector("button[class='btn full btn-login-submit']"));
        bekle(5);


        //Login Olunmuşmu Kontrol Edilir
        if (!elementGetText(By.id("myAccount")).equals("Giriş Yap\n" + "veya üye ol"))
        {
            System.out.println("Login Başarılı");

            bekle(1);
            sepetUrunSayisi = Integer.parseInt(elementGetText(By.cssSelector("label[data-bind='visible: cartCount() > -1, text: cartCount']")));

            //Sepette Ürün Var mı Kontrol Edilir
            if (sepetUrunSayisi != 0)
            {
                clickElement(By.id("shoppingCart"));
                bekle(1);
                sepetTutari = elementGetText(By.cssSelector("span[data-bind='text: totalPrice']"));
            }
            else
            {
                sepetTutari = "0,00";
                System.out.println("sepet boş");

            }
        }
        else
        {
            System.out.println("Login Başarısız");
            driver.quit();
        }

        clickElement(By.cssSelector("a[class='logo-hepsiburada']"));
        return sepetTutari;
    }

    public void urunEkraninaGiris() throws InterruptedException {
        //Elementlerin Tutulacağı Liste Tanımlanır
        ArrayList<WebElement> anaKategoriListe = new ArrayList<WebElement>();

        //Ana Menuden Random Seçim Yapılır
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        anaKategoriListe.addAll(findElementList(By.xpath("//li[contains(@class,'menu-main-item')]")));
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        sayiTut = randomSayi(anaKategoriListe.size());
        anaKategoriListe.get(sayiTut).click();

        //Beklenir
        bekle(3);

        //Alt Menuden Random Seçim Yapılır
        anaKategoriListe.clear();
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        anaKategoriListe.addAll(findElementList(By.xpath("//a[@data-level='1']")));
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        sayiTut = randomSayi(anaKategoriListe.size());
        anaKategoriListe.get(sayiTut).click();
    }





}
