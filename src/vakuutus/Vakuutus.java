package vakuutus;

import java.io.File;
import java.util.Collection;
import java.util.List;

import kanta.Tietue;

/**
 * vakuutus-luokka, joka huolehtii jäsenistöstä.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" jäsenistöön.
 *
 * @author olliterävä, laidmale
 * @version 21.02.2023
 */
public class Vakuutus  {
    private Asiakkaat asiakkaat = new Asiakkaat();
    private Kotivakuutukset kotivakuutukset = new Kotivakuutukset();
    private String hakemisto = "vakuutukset";

    /**
     * Palautaa vakuutuksen asiakkaiden
     * @return asiakkaiden määrä
     */
    public int getAsiakkaat() {
        return asiakkaat.getLkm();
    }


    /**
     * Poistaa jäsenistöstä ja vakuutuksista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako asiakasta poistettiin
     */
    public int poista(int nro) {
        return 0;
    }


    public int poista(Asiakas asiakas) {
                 if ( asiakas == null ) return 0;
                 int ret = asiakkaat.poista(asiakas.getTunnusNro()); 
               //  kotivakuutukset.poistaAsiakkaanVakuutus(asiakas.getTunnusNro()); 
                 return ret; 
             }
    
    
    /**
     * Lisää vakuutukseen uuden jäsenen
     * @param asiakas lisättävä jäsen
     * @example
     * <pre name="test">
     * Vakuutus vakuutus = new Vakuutus();
     * Asiakas marko1 = new Asiakas(), marko2 = new Asiakas();
     * marko1.rekisteroi(); marko2.rekisteroi();
     * vakuutus.getAsiakkaat() === 0;
     * vakuutus.lisaa(marko1); vakuutus.getAsiakkaat() === 1;
     * vakuutus.lisaa(marko2); vakuutus.getAsiakkaat() === 2;
     * vakuutus.getAsiakkaat() === 2;
     * vakuutus.annaAsiakas(0) === marko1;
     * vakuutus.annaAsiakas(1) === marko2; 
     * </pre>
     */
    public void lisaa(Asiakas asiakas) {
        asiakkaat.lisaa(asiakas);
    }

    
    public void lisaa(Kotivakuutus koti) {
        kotivakuutukset.lisaa(koti);
    }

    /**
     * haetaan kaikki asiakkaiden kotivakuutukset
     * @param asiakas keneen asiakkaaseen viitataan
     * @return tietorakenne, jossa viitteet kotivakuutuksiin
     */
    public List<Kotivakuutus> annaKotivakuutus(Asiakas asiakas) {
        return kotivakuutukset.annaKotivakuutukset(asiakas.getTunnusNro());
    }

    
    /**
     * Palauttaa i:n asiakkaan
     * @param i monesko asiakas palautetaan
     * @return viite i:teen jäseneen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Asiakas annaAsiakas(int i) throws IndexOutOfBoundsException {
        return asiakkaat.anna(i);
    }

    /**
     * KOrvaa asiakkaan jolla sama id
     * @param asiakas millä korvataan
     */
    public void korvaaTaiLisaa(Asiakas asiakas) {
        asiakkaat.korvaaTaiLisaa(asiakas);
    }

    /** 
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy, 
     * niin lisätään uutena harrastuksena. 
     * @param koti lisärtävän vakuutuksen viite.
     */ 
    public void korvaaTaiLisaa(Kotivakuutus koti) { 
        kotivakuutukset.korvaaTaiLisaa(koti); 
    } 


    /**
     * Lukee kerhon tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        asiakkaat = new Asiakkaat(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        kotivakuutukset = new Kotivakuutukset();

        hakemisto = nimi;
        asiakkaat.lueTiedostosta(nimi);
        kotivakuutukset.lueTiedostosta(nimi);
    }




    /**
     * Tallettaa kerhon tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            asiakkaat.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            kotivakuutukset.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

  




    /**
     * Testiohjelma vakuutuksesta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Vakuutus vakuutus = new Vakuutus();

       
            Asiakas marko1 = new Asiakas(), marko2 = new Asiakas();
            marko1.rekisteroi();
            marko1.naytaMarkoTiedoilla();
            marko2.rekisteroi();
            marko2.naytaMarkoTiedoilla();

            vakuutus.lisaa(marko1);
            vakuutus.lisaa(marko2);
            vakuutus.lisaa(marko1);
            vakuutus.lisaa(marko2);
            vakuutus.lisaa(marko1);
            vakuutus.lisaa(marko2);

            System.out.println("============= Vakuutuksen testi =================");

           
      
        for (int i = 0; i < vakuutus.getAsiakkaat(); i++) {
            Asiakas asiakas = vakuutus.annaAsiakas(i);
            System.out.println("vakuutuksia paikassa: " + i);
            asiakas.tulosta(System.out);
        }

    

    }
/**
 * etsimieen apu metodi
 * @param hakuehto mitä haetaan
 * @param k monesko ehto
 * @return tuloksen listassa
 */
    public Collection<Asiakas> etsi(String hakuehto, int k) { 
                return asiakkaat.etsi(hakuehto, k); 
             } 



   
}