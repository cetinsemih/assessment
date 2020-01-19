import com.thoughtworks.gauge.Step;

public class KullaniciSenaryoImplementation
{
    @Step("Kullanıcı <kullaniciAdi>,Parola <sifre> İle Login Olunur")
    public void kullaniciGirisi(String kullaniciAdi,String sifre)
    {
        System.out.println("parola :"+kullaniciAdi);
        System.out.println("Sifre  :"+sifre);
    }











}
