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
     * Palautaa vakuutuksen jäsenmäärän
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
    public int poista(@SuppressWarnings("unused") int nro) {
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
     * Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * vakuutus.getAsiakkaat() === 0;
     * vakuutus.lisaa(aku1); kerho.getAsiakkaat() === 1;
     * vakuutus.lisaa(aku2); kerho.getAsiakkaat() === 2;
     * vakuutus.lisaa(aku1); kerho.getAsiakkaat() === 3;
     * vakuutus.getAsiakkaat() === 3;
     * vakuutus.annaAsiakas(0) === aku1;
     * vakuutus.annaAsiakas(1) === aku2;
     * vakuutus.annaAsiakas(2) === aku1;
     * vakuutus.annaAsiakas(3) === aku1; #THROWS IndexOutOfBoundsException 
     * vakuutus.lisaa(aku1); vakuutus.getAsiakkaat() === 4;
     * vakuutus.lisaa(aku1); vakuutus.getAsiakkaat() === 5;
     * vakuutus.lisaa(aku1);            #THROWS SailoException
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
            // vakuutus.lueTiedostosta("kelmit");

            Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
            aku1.rekisteroi();
            aku1.naytaAkuTiedoilla();
            aku2.rekisteroi();
            aku2.naytaAkuTiedoilla();

            vakuutus.lisaa(aku1);
            vakuutus.lisaa(aku2);
            vakuutus.lisaa(aku1);
            vakuutus.lisaa(aku2);
            vakuutus.lisaa(aku1);
            vakuutus.lisaa(aku2);

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