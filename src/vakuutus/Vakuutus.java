package vakuutus;


/**
 * vakuutus-luokka, joka huolehtii jäsenistöstä.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" jäsenistöön.
 *
 * @author olliterävä, laidmale
 * @version 21.02.2023
 */
public class Vakuutus {
    private final Asiakkaat asiakkaat = new Asiakkaat();


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
    public int poista( int nro) {
        return 0;
    }


    /**
     * Lisää vakuutukseen uuden jäsenen
     * @param asiakas lisättävä jäsen
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Vakuutus vakuutus = new Vakuutus();
     * Asiakas marko1 = new Asiakas(), marko2 = new Asiakas();
     * marko1.rekisteroi(); marko2.rekisteroi();
     * vakuutus.getAsiakkaat() === 0;
     * vakuutus.lisaa(marko1); kerho.getAsiakkaat() === 1;
     * vakuutus.lisaa(marko2); kerho.getAsiakkaat() === 2;
     * vakuutus.lisaa(marko1); kerho.getAsiakkaat() === 3;
     * vakuutus.getAsiakkaat() === 3;
     * vakuutus.annaAsiakas(0) === marko1;
     * vakuutus.annaAsiakas(1) === marko2;
     * vakuutus.annaAsiakas(2) === marko1;
     * vakuutus.annaAsiakas(3) === marko1; #THROWS IndexOutOfBoundsException 
     * vakuutus.lisaa(marko1); vakuutus.getAsiakkaat() === 4;
     * vakuutus.lisaa(marko1); vakuutus.getAsiakkaat() === 5;
     * vakuutus.lisaa(marko1);            #THROWS SailoException
     * </pre>
     */
    public void lisaa(Asiakas asiakas) throws SailoException {
        asiakkaat.lisaa(asiakas);
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
     * Lukee kerhon tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        asiakkaat.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa vakuutuksen tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        asiakkaat.talleta();
        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
    }


    /**
     * Testiohjelma vakuutuksesta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Vakuutus vakuutus = new Vakuutus();

        try {
            // vakuutus.lueTiedostosta("matkavakuutus");

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

           
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        for (int i = 0; i < vakuutus.getAsiakkaat(); i++) {
            Asiakas asiakas = vakuutus.annaAsiakas(i);
            System.out.println("vakuutuksia paikassa: " + i);
            asiakas.tulosta(System.out);
        }

    }

}