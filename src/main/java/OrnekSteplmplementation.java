import com.thoughtworks.gauge.Step;

public class OrnekSteplmplementation {

    @Step("ornek bir adım")
public void ornekAdim()
{
    System.out.println("Ornek adım Çalıştı");
}

@Step("ornek ikinci adim")
public void OrnekAdim2()
    {
        System.out.println("ornek 2 çalıştı");
    }

    @Step("<urunAdi> urununu ara")
    public void arcelikUrununuAra (String urunAdi)
    {
        System.out.println(urunAdi+" Urununu Ara");
    }

}
