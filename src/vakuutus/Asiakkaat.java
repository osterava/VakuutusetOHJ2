package vakuutus;

/**
 * @author olliterävä, laura
 * @version 21.2.2023
 *
 */
public class Asiakkaat {
    private static final int MAX_JASENIA   = 5;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Asiakas            alkiot[]      = new Asiakas[MAX_JASENIA];


    /**
     * Oletusmuodostaja
     */
    public Asiakkaat() {
       
    }


    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa asiakkaan omistukseensa.
     * @param asiakas lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Asiakkaat asiakkaat = new Asiakkaat();
     * Asiakas marko1 = new Asiakas(), marko2 = new Asiakas();
     * asiakkaat.getLkm() === 0;
     * asiakkaat.lisaa(marko1); asiakkaat.getLkm() === 1;
     * asiakkaat.lisaa(marko2); asiakkaat.getLkm() === 2;
     * asiakkaat.lisaa(marko1); asiakkaat.getLkm() === 3;
     * asiakkaat.anna(0) === marko1;
     * asiakkaat.anna(1) === marko2;
     * asiakkaat.anna(2) === marko1;
     * asiakkaat.anna(1) == marko1 === false;
     * asiakkaat.anna(1) == marko2 === true;
     * asiakkaat.anna(3) === marko1; #THROWS IndexOutOfBoundsException 
     * asiakkaat.lisaa(marko1); asiakkaat.getLkm() === 4;
     * asiakkaat.lisaa(marko1); asiakkaat.getLkm() === 5;
     * asiakkaat.lisaa(marko1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Asiakas asiakas) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = asiakas;
        lkm++;
    }


    /**
     * Palauttaa viitteen i:teen asiakkaaseen.
     * @param i monennenko jäsenen viite halutaan
     * @return viite jäseneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Asiakas anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee asikkaiden tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa jäsenistön tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa vakuutusten  lukumäärän
     * @return asiakkaiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }


    /**
     * Testiohjelma jäsenistölle
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Asiakkaat asiakkaat = new Asiakkaat();

        Asiakas marko = new Asiakas(), marko2 = new Asiakas();
        marko.rekisteroi();
        marko.naytaMarkoTiedoilla();
        marko2.rekisteroi();
        marko2.naytaMarkoTiedoilla();

        try {
            asiakkaat.lisaa(marko);
            asiakkaat.lisaa(marko2);

            System.out.println("============= asiakkaat testi =================");

            for (int i = 0; i < asiakkaat.getLkm(); i++) {
                Asiakas jasen = asiakkaat.anna(i);
                System.out.println("Jäsen nro: " + i);
                jasen.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
