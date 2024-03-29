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
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author olliterävä, laura
 * @version 21.2.2023
 * Vakuutusten asiakkaat, joka osaa lisätä uuden jäsenen
 *
 */
public class Asiakkaat implements Iterable<Asiakas> {
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

     public class AsiakkaatIterator implements Iterator<Asiakas> {
         private int kohdalla = 0;


         /**
          * Onko olemassa vielä seuraavaa jäsentä
          * @see java.util.Iterator#hasNext()
          * @return true jos on vielä jäseniä
          */
         @Override
         public boolean hasNext() {
             return kohdalla < getLkm();
         }


         /**
          * Annetaan seuraava jäsen
          * @return seuraava jäsen
          * @throws NoSuchElementException jos seuraava alkiota ei enää ole
          * @see java.util.Iterator#next()
          */
         @Override
         public Asiakas next() throws NoSuchElementException {
             if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
             return anna(kohdalla++);
         }


         /**
          * Tuhoamista ei ole toteutettu
          * @throws UnsupportedOperationException aina
          * @see java.util.Iterator#remove()
          */
         @Override
         public void remove() throws UnsupportedOperationException {
             throw new UnsupportedOperationException("Me ei poisteta");
         }
     }


     /**
      * Palautetaan iteraattori jäsenistään.
      * @return jäsen iteraattori
      */
     @Override
     public Iterator<Asiakas> iterator() {
         return new AsiakkaatIterator();
     }

     /**
      * etsimille tarkoitettu metodi
      * @param hakuehto hakuehto
      * @param k mionesto
      * @return löytyneet listan
      */
     public Collection<Asiakas> etsi(String hakuehto, int k) { 
                  String ehto = "*"; 
                  if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
                  int hk = k; 
                  if ( hk < 0 ) hk = 1;
                  Collection<Asiakas> loytyneet = new ArrayList<Asiakas>(); 
                  for (Asiakas jasen : this) { 
                      if (WildChars.onkoSamat(jasen.anna(hk), ehto)) loytyneet.add(jasen);   
                  } 
                  //  TODO: lajittelua varten vertailija  
                  return loytyneet; 
              }

    /**
     *  etsii asiakkaan id:n.
     * @param id mikä asiakkaan tunnusnumero
     * @return -1 jos ei löydy, idn jos löytyy
     */
     public int etsiId(int id) { 
                  for (int i = 0; i < lkm; i++) 
                      if (id == alkiot[i].getTunnusNro()) return i; 
                  return -1; 
              } 
     
     /**
      * poistaa id:n
      * @param id monesko id
      * @return 1 jos on poistettu
      */
     public int poista(int id) { 
                  int ind = etsiId(id); 
                  if (ind < 0) return 0; 
                  lkm--; 
                  for (int i = ind; i < lkm; i++) 
                      alkiot[i] = alkiot[i + 1]; 
                  alkiot[lkm] = null; 
                  muutettu = true; 
                  return 1; 
              } 
     

}
