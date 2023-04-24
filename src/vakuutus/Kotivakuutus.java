package vakuutus;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
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
    
    /**
     * joskus ehkä käyttöön
     */
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
     * @param out tietovirta johon tulostetaan "näyttöön"
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
    
    /**
     * Palauttaa harrastuksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return harrastus tolppaeroteltuna merkkijonona
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + asNro + "|" + kaytossa + "|" + pAla + "|" + hinta + "|" + pvmVoimassa 
                + "|" + onkoIV + "|" + asuntotyyppi + "|" + omaVastuu;
    }


    /**
     * Selvitää kotivakuususten tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta harrastuksen tiedot otetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        asNro = Mjonot.erota(sb, '|', asNro);
        kaytossa = Mjonot.erota(sb, '|', kaytossa);
        pAla = Mjonot.erota(sb, '|', pAla);
        hinta = Mjonot.erota(sb, '|', hinta);
        pvmVoimassa = Mjonot.erota(sb, '|', pvmVoimassa);
        onkoIV = Mjonot.erota(sb, '|', onkoIV);
        asuntotyyppi = Mjonot.erota(sb, '|', asuntotyyppi);
        omaVastuu = Mjonot.erota(sb, '|', omaVastuu);
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusnumero = nr;
        if ( tunnusnumero >= seuraavaNro ) seuraavaNro = tunnusnumero + 1;
    }

    /**
     * @return harrastukse kenttien lukumäärä
     */
    public int getKenttia() {
        return 5;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    public int ekaKentta() {
        return 2;
    }
    

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "tunnusnumero";
            case 1:
                return "asNro";
            case 2:
                return "kaytossa";
            case 3:
                return "pAla";
            case 5:
                return "pvmVoimassa";
            case 6:
                return "onkoIV";
            case 7:
                return "asuntotyyppi";
            case 8:
                return "omaVastuu";
            default:
                return "???";
        }
        
    }

 
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusnumero;
            case 1:
                return "" + asNro;
            case 2:
                return kaytossa;
            case 3:
                return "" + pAla;
            case 4:
                return "" + hinta;
            case 5:
                return "" + pvmVoimassa;
            case 6:
                return "" + onkoIV;
            case 7:
                return "" + asuntotyyppi;
            case 8:
                return "" + omaVastuu;
            default:
                return "???";
        }
    }

    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     */
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                asNro = Mjonot.erota(sb, '$', tunnusnumero);
                return null;
            case 2:
                kaytossa = st;
                return null;
            case 3:
                pAla = Mjonot.erota(sb, '$', pAla);
                return null;
            case 4:
                hinta = Mjonot.erota(sb, '$', hinta);
                return null;
            case 5:
                pvmVoimassa = st;
                return null;
            case 6:
                onkoIV = st;
                return null;
            case 7:
                asuntotyyppi = st;
                return null;
            case 8:
                omaVastuu = Mjonot.erota(sb, '$', omaVastuu);
                return null;
          
            default:
                return "Väärä kentän indeksi";
        }
    }
    
    @Override
    public Kotivakuutus clone() throws CloneNotSupportedException { 
        return (Kotivakuutus)super.clone();
    }




    
}
