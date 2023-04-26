package vakuutus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author olliterävä, laura
 * @version 21.2.2023
 * Vakuutusten asiakkaat, joka osaa lisätä uuden jäsenen
 *
 */
public class Asiakkaat {
    private static final int MAX_JASENIA   = 15;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Asiakas          alkiot[]      = new Asiakas[MAX_JASENIA];
    private boolean          muutettu      = false;


    /**
     * Oletusmuodostaja
     */
    public Asiakkaat() {
       
    }


    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa asiakkaan omistukseensa.
     * @param asiakas lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
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
     * asiakkaat.lisaa(marko1); asiakkaat.getLkm() === 4;
     * asiakkaat.lisaa(marko1); asiakkaat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Asiakas asiakas) {
        if ( lkm >= alkiot.length ) alkiot = Arrays.copyOf(alkiot, lkm+20); 
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
     * Lukee asikkaiden tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/asiakkaat.dat";
        String nimi = tiedostonNimi;
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
            while ( fi.hasNext() ) {
                String s = fi.nextLine();
                if ( s == null || "".equals(s) || s.charAt(0) == ';') continue;
                Asiakas asiakas = new Asiakas();
                asiakas.parse(s);
                lisaa(asiakas);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
       
        }     
    }



    /**
     * Tallentaa Asiakkaat tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * 2|Ankka Aku|121103-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
     * 3|Ankka Tupu|121153-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
     * </pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException jos talletus epäonnistuu
     */
        public void tallenna(String hakemisto) throws SailoException {
            File ftied = new File(hakemisto + "/asiakkaat.dat");
            try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
                for (int i=0; i<this.getLkm(); i++) {
                    Asiakas asiakas = this.anna(i);
                    fo.println(asiakas.toString());
                }
                muutettu = false;
                
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
            } 
        }

    
    

    /**
     * Tallentaa jäsenistön tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        tallenna(tiedostonNimi);
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

            asiakkaat.lisaa(marko);
            asiakkaat.lisaa(marko2);

            System.out.println("============= asiakkaat testi =================");

            for (int i = 0; i < asiakkaat.getLkm(); i++) {
                Asiakas jasen = asiakkaat.anna(i);
                System.out.println("Jäsen nro: " + i);
                jasen.tulosta(System.out);
            }

        
        try {
            asiakkaat.tallenna("Asiakkaat");
        }  catch (SailoException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }


     public void korvaaTaiLisaa(Asiakas asiakas){
        int id = asiakas.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = asiakas;
                muutettu = true;
                return;
            }
        }
        lisaa(asiakas);
    }

}
