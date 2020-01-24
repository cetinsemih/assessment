import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class KullaniciSenaryoImplementation
{
    static WebDriver driver;
    static Random randomSayi = new Random();
    String sepetTutari,urunBaslikListe,urunBaslikDetay,urunFiyatListe,urunFiyatDetay,adresAdıTut;
    int sepetUrunSayisi,sayiTut;
    String outputFile = "data\\file.csv";
    FileWriter writer = new FileWriter(outputFile,false);

    public KullaniciSenaryoImplementation() throws IOException {
    }


    @Step("Kullanıcı <kullaniciAdi>,Parola <sifre> İle Login Olunur")
    public void kullaniciGirisi(String kullaniciAdi,String sifre) throws InterruptedException, IOException {
        setUp();




        driver.navigate().to("https://www.hepsiburada.com");
        login(kullaniciAdi,sifre);
        urunEkraninaGiris();
        bekle(3);
        asagKaydir(200);
        markaSec();
        fiyatAraligiGir("10","4000");
        urunSec();
        bekle(2);
        detayBilgiCek();
        karsilastirStr(urunBaslikListe,urunBaslikDetay);
        karsilastirStr(urunFiyatListe,urunFiyatDetay);
        karsilastirInt(sepeteUrunEkleme(),getSepetUrunSayisi());
        sepeteGir();
        ürünSayisiArttır(2);
        alisverisiTamamla();
        adresEkle("semih","çetin","istanbul","Ataşehir","içerenköy","adadadadada","testtest","5077130732");
        bekleTikla(By.xpath("//button[@class='btn btn-primary full']"),2);
        krediKartıBilgiGir("4543591813531054","Semih Çetin","09","2022","106");
        anaSayfayaAc();
        sepetTemizle();
        adresSil();
        csvKapama();



























        //---------------------------
















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

    //navigatörü gönderilen süre bilgisi kadar Bekleyip Gönderilen elemente tıklanır
    public static void bekleTikla(By by,int sure) throws InterruptedException {
        bekle(sure);
        findElement(by).click();
        bekle(1);
        System.out.println("BekleTıkla Fonksiyonu"+ by +" için Çalıştı");
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

    //Ekranı aşağı kaydırır
    public void asagKaydir(int süre)
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,"+süre+")");
    }

    //Ekranı Yukarı Kaydırır
    public void yukariKaydir(int süre)
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-"+süre+")");
    }

    //Urun Detay Ekranından Başlık ve Tutar Bilgisi Çekilip Sepete ürün eklenir.
    public void detayEkranıUrunEkleme()
    {

    }

    //CSV Tanımlama
    public void csvYazma(String yaz) throws IOException
    {
        writer.write(yaz+"\n");
    }

    //CSV Kapama
    public void csvKapama() throws IOException
    {
        writer.close();
    }

    //Consola Yaz
    public void consolaBas(String yaz)
    {
        System.out.println(yaz);
    }

    //Sepetteki Ürün Sayısı Çekilir.
    public int getSepetUrunSayisi()
    {
        return Integer.parseInt(elementGetText(By.cssSelector("label[data-bind='visible: cartCount() > -1, text: cartCount']")));
    }

    //Sepet Ekranı Açılır
    public void sepeteGir() throws InterruptedException {
        clickElement(By.id("shoppingCart"));
        bekle(1);
    }

    //Gönderilen Sayi kadar Ürün Sayisi Arttırılır
    public void ürünSayisiArttır(int EklenecekSayi) throws InterruptedException, IOException {
        ArrayList<WebElement> sepetUrunListe = new ArrayList<>();
            for (int i = 0;i<EklenecekSayi;i++)
        {
            sepetUrunListe.clear();
            sepetUrunListe.addAll(findElementList(By.xpath("//button[@data-bind='click: $parent.increase']")));
            sepetUrunListe.get(sepetUrunListe.size()-1).click();
            consolaBas("\n"+urunBaslikDetay+"\nUrunu Sepette 1 Arttırıldı");
            csvYazma("\n"+urunBaslikDetay+"\nUrunu Sepette 1 Arttırıldı");
            bekle(2);
        }
    }

    //Adres Değişkenine Değer Atanır
    public void setAdresAdı(String adresAdı)
    {
        adresAdıTut = adresAdı;
    }


    //------------------

    // Gelen Bilgiler İle Login Olur, Login Kontrolü Yapar , Seper Tutarını döner
    public String login(String kullaniciAdi,String sifre) throws InterruptedException, IOException {
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
            consolaBas("\n Login Başarılı \n\n");
            csvYazma("\n Login Başarılı \n\n");

            bekle(1);
            sepetUrunSayisi = Integer.parseInt(elementGetText(By.cssSelector("label[data-bind='visible: cartCount() > -1, text: cartCount']")));
            consolaBas("Sepetteki Urun Sayisi \n"+sepetUrunSayisi+"\n\n");
            csvYazma("Sepetteki Urun Sayisi \n"+sepetUrunSayisi+"\n\n");


            //Sepette Ürün Var mı Kontrol Edilir
            if (sepetUrunSayisi != 0)
            {
                clickElement(By.id("shoppingCart"));
                bekle(1);
                sepetTutari = elementGetText(By.cssSelector("span[data-bind='text: totalPrice']"));
                consolaBas("Sepet Tutarı\n"+sepetTutari+"\n\n");
                csvYazma("Sepet Tutarı\n"+sepetTutari+"\n\n");
            }
            else
            {
                sepetTutari = "0,00 TL";
                System.out.println("sepet boş");
                consolaBas("Sepet Tutarı\n"+sepetTutari+"\n\n");
                csvYazma("Sepet Tutarı\n"+sepetTutari+"\n\n");

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

    //Ana Menu ve Alt Menuden Random Bir Şekilde Ürün Ekranına Giriş Yapılır
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

    //Rastgele Marka Seçimi Yapılır
    public void markaSec()
    {
        //Elementlerin Tutulacağı Liste Tanımlanır
        ArrayList<WebElement> anaKategoriListe = new ArrayList<WebElement>();

        //Marka Listesinden Random Seçim Yapılır
        anaKategoriListe.clear();
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        anaKategoriListe.addAll(findElementList(By.xpath("//label[contains(@for,'brand-')]")));
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        sayiTut = randomSayi(anaKategoriListe.size());
        anaKategoriListe.get(sayiTut).click();
    }

    //Girilen Verilere göre Fiyat Aralığı kısmına alt ve üst limit girilir
    public void fiyatAraligiGir(String altLimit,String ustLimit) throws InterruptedException
    {
        bekle(2);
        asagKaydir(250);
        sendKeys(By.xpath("//input[contains(@data-bind,'value: minPrice,')]"),altLimit);
        sendKeys(By.xpath("//input[contains(@data-bind,'value: maxPrice,')]"),ustLimit);
        clickElement(By.xpath("//button[contains(@data-bind,'PriceRangeButton')]"));
        System.out.println("Fiyat Aralığı Alanına Alt Limit Olarak ="+altLimit+"Üst Limit Olarak ="+ustLimit+" girilmiştir ");
    }

    //Listeden Gönderilen Elemente Tıklar
    public void scrollAndClick(WebElement gelenElement)
    {
        WebElement element = gelenElement;
        int elementPosition = element.getLocation().getY();
        String js = String.format("window.scroll(0, %s)", elementPosition);
        ((JavascriptExecutor)driver).executeScript(js);
        element.click();
    }


    //Rastgele Ürün Seçilir
    public void urunSec() throws InterruptedException, IOException {
        bekle(2);
        //Elementlerin Tutulacağı Liste Tanımlanır
        ArrayList<WebElement> anaKategoriListe = new ArrayList<WebElement>();
        ArrayList<WebElement> anaKategoriListeBaslik = new ArrayList<WebElement>();
        ArrayList<WebElement> anaKategoriListTutar = new ArrayList<WebElement>();

        //Ana Menuden Random Seçim Yapılır
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        anaKategoriListe.addAll(findElementList(By.xpath("//ul[@class='product-list results-container do-flex list']/li/div/a/div[@class='product-detail']")));
        System.out.println(anaKategoriListe.size()+" listedeki eleman sayisi");
        sayiTut = randomSayi(anaKategoriListe.size());

        //Urunun Başlık Bilgisi Çekilir
        anaKategoriListeBaslik.addAll(findElementList(By.xpath("//ul[@class='product-list results-container do-flex list']/li/div/a/div[@class='product-detail']/h3[@class='product-title title']")));
        System.out.println(anaKategoriListeBaslik.size()+" listedeki eleman sayisi");
        urunBaslikListe = anaKategoriListeBaslik.get(sayiTut).getText();

        //Urunun Fiyat Bilgisi Çekilir
        anaKategoriListTutar.addAll(findElementList(By.xpath("//ul[@class='product-list results-container do-flex list']/li/div/a/div[@class='product-detail']/div[@class='price-container highlight-badge  hb-pl-cn']/span")));
        System.out.println(anaKategoriListTutar.size()+" listedeki eleman sayisi");
        urunFiyatListe = anaKategoriListTutar.get(sayiTut).getText();

        //CVS Dosyasına Kayıt Atar
        csvYazma("Urunun Listedeki Başlığı \n"+urunBaslikListe+"\n");
        csvYazma("Urunun Listedeki fiyatı  \n"+urunFiyatListe+"\n\n\n");

        //Consola Kayıt Atar
        consolaBas("Urunun Listedeki Başlığı \n"+urunBaslikListe+"\n");
        consolaBas("Urunun Listedeki fiyatı  \n"+urunFiyatListe+"\n\n\n");

        //Rasgele Seçilen Ürüne Tıklanır
        scrollAndClick(anaKategoriListe.get(sayiTut));



    }

    //Gönderilen String İfadeleri Karşılaştırır-Consola ve CSV dosyasınna kayıt atar
    public boolean karsilastirStr(String değer1, String değer2) throws IOException
    {

        if(değer1.equals(değer2))
        {
            consolaBas("\n"+değer1+"\n"+değer1+"\naynıdır\n");
            csvYazma("\n"+değer1+"\n"+değer1+"\naynıdır\n");
            return true;
        }
        else
        {
            consolaBas("\n"+değer1+"\n"+değer1+"\neşleşmiyor\n");
            csvYazma("\n"+değer1+"\n"+değer1+"\neşleşmiyor\n");
            return false;
        }
    }

    //Gönderilen İnteger İfadeleri Karşılaştırır-Consola ve CSV dosyasınna kayıt atar
    public boolean karsilastirInt(int değer1, int değer2) throws IOException
    {

        if(değer1 == değer2)
        {
            consolaBas("\n"+değer1+"\n"+değer1+"\naynıdır\n");
            csvYazma("\n"+değer1+"\n"+değer1+"\naynıdır\n");
            return true;
        }
        else
        {
            consolaBas("\n"+değer1+"\n"+değer1+"\neşleşmiyor\n");
            csvYazma("\n"+değer1+"\n"+değer1+"\neşleşmiyor\n");
            return false;
        }
    }



    //Detay Ekraından Başlık ve Tutar Bilgileri Çekilir.
    public void detayBilgiCek() throws IOException, InterruptedException {

        bekle(1);
        urunBaslikDetay= findElement(By.xpath("//h1[contains(@id,'product-name')]")).getText();
        csvYazma("Urunun Listedeki Başlığı \n"+urunBaslikDetay+"\n");
        urunFiyatDetay = findElement(By.xpath("//span[contains(@id,'offering-price')]")).getText();
        csvYazma("Urunun Listedeki fiyatı  \n"+urunFiyatDetay+"\n\n\n");

        //CVS Dosyasına Kayıt Atar
        csvYazma("Urunun Detay Ekranındaki Başlığı \n"+urunBaslikDetay+"\n");
        csvYazma("Urunun Detay Ekranındaki fiyatı  \n"+urunFiyatDetay+"\n\n\n");

        //Consola Kayıt Atar
        consolaBas("Urunun Detay Ekranındaki Başlığı \n"+urunBaslikDetay+"\n");
        consolaBas("Urunun Detay Ekranındaki fiyatı  \n"+urunFiyatDetay+"\n\n\n");

        bekle(1);
    }

    //Sepete Ürün Eklenir
    public int sepeteUrunEkleme() throws InterruptedException, IOException {
        bekle(1);
        clickElement(By.xpath("//button[@id='addToCart']"));
        sepetUrunSayisi++;
        bekle(3);

        //Log Basma
        consolaBas("\n"+urunBaslikDetay+"\nSepete Eklenmiştir.\nSepetteki ürün Sayısı :"+sepetUrunSayisi+"\n");
        csvYazma("\n"+urunBaslikDetay+"\nSepete Eklenmiştir.\nSepetteki ürün Sayısı :"+sepetUrunSayisi+"\n");

        return sepetUrunSayisi;
    }

    //Sepet Tutar Bilgilerini Çekip "Alışverişi Tamamla" Butonuna Tıklar
    public void alisverisiTamamla() throws IOException {
        //Ödenecek Tutar Bilgisi Basılır
        consolaBas("Ödenecek Tutar\n"+findElement(By.xpath("//div[@class='total-price']")).getText()+"\n\n");
        csvYazma("Ödenecek Tutar\n"+findElement(By.xpath("//div[@class='total-price']")).getText()+"\n\n");

        ArrayList<WebElement> tutarListe = new ArrayList<>();
        tutarListe.addAll(findElementList(By.xpath("//div[@class='list-item']/div[@class='price']")));

        //Ürünler Toplamı (KDV Dahil) Bilgisi Basılır
        consolaBas("Ürünler Toplamı (KDV Dahil)\n"+tutarListe.get(0).getText()+"\n\n");
        csvYazma("Ürünler Toplamı (KDV Dahil)\n"+tutarListe.get(0).getText()+"\n\n");

        //Ürünler Toplamı (KDV Dahil) Bilgisi Basılır
        consolaBas("Kargo Ücreti\n"+tutarListe.get(1).getText()+"\n\n");
        csvYazma("Kargo Ücreti\n"+tutarListe.get(1).getText()+"\n\n");

        //Alışverişi Tamamla Butonuna Tıklanır
        clickElement(By.xpath("//i[@class='icon-chevron-right']"));
    }

    //Yeni Adres Ekler
    public void adresEkle(String ad,String soyAd,String il,String ilce,String mahalle,String adres,String adresAdi,String telefon) throws InterruptedException, IOException {
        //"Yeni Ekle" (Adres) Butonuna Tıklar
        bekle(1);
        clickElement(By.xpath("//div[@class='col delivery-addresses']/div[@class='selectbox-header']/a[@class='link-type-two']"));
        bekle(1);

        //Ad Bilgisi Girili
        sendKeys(By.xpath("//input[@id='first-name']"),ad);

        //Soyad Bilgisi Girilir
        sendKeys(By.xpath("//input[@id='last-name']"),soyAd);

        ArrayList<WebElement> dropDownList = new ArrayList<>();

        //İl Seç
        dropDownList.clear();
        clickElement(By.xpath("//button[@data-id='city']"));
        dropDownList.addAll(findElementList(By.xpath("//input[contains(@class,'input-block-level')]")));
        dropDownList.get(1).sendKeys(il);
        dropDownList.clear();
        dropDownList.addAll(findElementList(By.xpath("//li[@rel='1']")));
        dropDownList.get(1).click();

        bekle(2);
        //İlçe Seç
        dropDownList.clear();
        clickElement(By.xpath("//button[@data-id='town']"));
        bekle(1);
        dropDownList.addAll(findElementList(By.xpath("//input[contains(@class,'input-block-level')]")));
        dropDownList.get(2).sendKeys(ilce);
        clickElement(By.linkText("ATAŞEHİR"));

        bekle(2);
        //mahalle Seç
        dropDownList.clear();
        clickElement(By.xpath("//button[@data-id='district']"));
        bekle(1);
        dropDownList.addAll(findElementList(By.xpath("//input[contains(@class,'input-block-level')]")));
        dropDownList.get(3).sendKeys(ilce);
       clickElement(By.linkText("İÇERENKÖY"));


        //Adres Bilgisi Girilir
        sendKeys(By.xpath("//textarea[@id='address']"),adres);


        //Adres Adı Bilgisi Girilir.
        sendKeys(By.xpath("//input[@id='address-name']"),adresAdi);
        setAdresAdı(adresAdi);

        //TC Kimlik No Bilgisi Girilir.
        //sendKeys(By.xpath("//input[@id='citizen-id']"),tcKimkik);

        //Telefon Bilgisi Girilir
        sendKeys(By.xpath("//input[@id='phone']"),telefon);

        //Adresi Kaydet Butoonuna Tıklanır
        clickElement(By.xpath("//button[@class='btn btn-primary btn-save-shipping-address']"));

        bekle(3);

        consolaBas("\nAdres Ekleme İşlemi Gerçekleşti\n\n");
        csvYazma("\nAdres Ekleme İşlemi Gerçekleşti\n\n");

    }

    //"Kredi / Banka Kartı" seçilir Kredi Kartı Bilgileri Girilir
    public void  krediKartıBilgiGir(String kartNo,String kartIsim,String sonKulAy,String sonKulYil,String guvKod) throws InterruptedException, IOException {

        //"Kredi / Banka Kartı" alanına tıklanır
        bekleTikla(By.xpath("//a[@class='accordion-title paymentType-1']"),2);
        bekle(1);

        //"Kart Numarası" Alanına Giriş Yapılır
        sendKeys(By.xpath("//input[@name='cardNumber']"),kartNo);
        bekle(1);

        //"Kart Üzerindeki İsim" Alanına Giriş Yapılır
        sendKeys(By.xpath("//input[@name='holderName']"),kartIsim);
        bekle(1);

        //Son Kullanma Tarihi Ay Bilgisi Girilir
        clickElement(By.xpath("//button[@title='Ay']"));
        bekle(1);
        clickElement(By.linkText(sonKulAy));
        bekle(1);

        //Son Kullanma Tarihi Yıl Bilgisi Girilir
        clickElement(By.xpath("//button[@title='Yıl']"));
        bekle(1);
        clickElement(By.linkText(sonKulYil));
        bekle(1);


        //"Güvenlik Kodu" Alanına Giriş Yapılır
        sendKeys(By.xpath("//input[@name='ccv']"),guvKod);

        //beklenir
        consolaBas("\nKredi Kartı Bilgileri Girildi\n");
        csvYazma("\nKredi Kartı Bilgileri Girildi\n");
        bekle(5);




    }

    //Ana Sayfa Açılır
    public void anaSayfayaAc() throws InterruptedException, IOException {
        driver.navigate().to("https://www.hepsiburada.com");
        consolaBas("\nAna Sayfa Açıldı\n");
        csvYazma("\nAna Sayfa Açıldı\n");
        bekle(2);
    }

    //Sepet Temizlenir
    public void sepetTemizle() throws InterruptedException, IOException {
        bekle(5);
        sepeteGir();

        ArrayList<WebElement> sepetUrunListe = new ArrayList<>();
        sepetUrunListe.addAll(findElementList(By.xpath("//a[contains(@class,'btn-delete')]")));
        int donmeSayisi = sepetUrunListe.size();


        for(int i=0;i< donmeSayisi;i++)
        {
            sepetUrunListe.clear();
            sepetUrunListe.addAll(findElementList(By.xpath("//a[contains(@class,'btn-delete')]")));
            sepetUrunListe.get(0).click();
            consolaBas("\nSepetten Urun Silindi\n");
            csvYazma("\nSepetten Urun Silindi\n");
            bekle(2);
        }



    }

    //Eklenen Adres Silinir
    public void adresSil() throws InterruptedException, IOException {
        driver.navigate().to("https://www.hepsiburada.com/ayagina-gelsin/teslimat-adreslerim");
        bekle(2);
        int intTut=0,listeSizeTut=0;

        ArrayList<WebElement> elementListeTut = new ArrayList<>();
        elementListeTut.addAll(findElementList(By.xpath("//h4[@class='list-item-title']")));
        listeSizeTut = elementListeTut.size();

        for(int i = 0;i < listeSizeTut;i++)
        {
            if(adresAdıTut.equals(elementListeTut.get(i).getText()))
            {
                intTut = i;
                break;
            }

        }
        bekle(2);
        elementListeTut.clear();
        elementListeTut.addAll(findElementList(By.xpath("//a[@data-bind='click: openDelete']")));
        elementListeTut.get(intTut).click();
        bekle(2);
        elementListeTut.clear();
        elementListeTut.addAll(findElementList(By.xpath("//a[@class='btn btn-md']")));
        elementListeTut.get(intTut).click();
        bekle(10);
        anaSayfayaAc();

    }




}
