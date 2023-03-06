package vakuutus;

import java.io.OutputStream;
import java.io.PrintStream;

import kanta.HetuTarkistus;

/**
 * @author olliterava,laidmale
 * @version 6.3.2023
 *
 */
public class Kotivakuutus {
    
    private int tunnusnumero;
    private int asNro;
    private String kaytossa;
    private double pAla;
    private double hinta;
    private String pvmVoimassa;
    private String onkoIV;
    private String asuntotyyppi;
    private double omaVastuu;
    
    private static int seuraavaNro = 1;
    
    
    /** 
     * testiohjelma Vakuutukselle
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotivakuutus koti1 = new Kotivakuutus();
        koti1.testiVakuutus(2);
        koti1.tulosta(System.out);
    
    }
    
    public Kotivakuutus() {
        // joskus ehkä käyttöön;
    }
    
    /**
     * Alustaa tietyn asiakkaan kotivakuutukset
     * @param tunnusnumero viite asiakkaaseen, jonka vakuutuksesta kyse
     */
    public Kotivakuutus(int tunnusnumero) {
        this.tunnusnumero = tunnusnumero;
    }
    
    /**
     * testausmetodi väliaikaiseen toimintaan
     * @param nro viite henkilöön jonka vakuutuksista kyse;
     */
    public void testiVakuutus(int nro) {
        asNro = nro;
        kaytossa = "käytössä";
        pAla = HetuTarkistus.rand(10,300);
        hinta = HetuTarkistus.rand(10,150);
        pvmVoimassa = "13.3.9999";
        onkoIV = "irtaimisto on vakuutettu";
        asuntotyyppi = "kerrostalo";
        omaVastuu = 100;
    }
    
    /**
     * Tulostetaan kotivakuutuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(kaytossa + " " + pAla + " " + hinta + " " + pvmVoimassa + "...");
    }

    /**
     * Tulostetaan asiakkaan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa kotivakuutukselle seuraavan vapaan rekisterinumeron.
     * @return kotivakuutuksen uusi tunnusnro
     * @example
     * <pre name="test">
     *   Kotivakuutus koti1 = new Kotivakuutus();
     *   koti1.getTunnusNro() === 0;
     *   koti1.rekisteroi();
     *   Kotivakuutus koti2 = new Kotivakuutus();
     *   koti2.rekisteroi();
     *   int n1 = koti1.getTunnusNro();
     *   int n2 = koti2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusnumero = seuraavaNro;
        seuraavaNro++;
        return tunnusnumero;
    }
    
    /**
     * Palautetaan kotivakuutuksen oma id
     * @return kotivakuutuksen id
     */
    public int getTunnusNro() {
        return tunnusnumero;
    }
    
    /**
     * Palautetaan mille asiakkaalle Vakuutus kuuluu
     * @return asiakkaan id
     */
    public int getAsNro() {
        return asNro;
    }
    
    


    
}
