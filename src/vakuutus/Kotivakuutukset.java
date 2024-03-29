package vakuutus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * 
 * @author olliterava, laidmale
 * @version 6.3.2023
 *
 */
public class Kotivakuutukset {
    
    private List<Kotivakuutus> alkiot = new ArrayList<Kotivakuutus>();
    
    
   
    /**
     * alustaminen
     */
    public Kotivakuutukset() {
        
    }
    
    /**
     * @param koti Lisättävä kotivakuutus 
     */
     public void lisaa(Kotivakuutus koti){
         alkiot.add(koti);
     }
     
     
     /**
      * Tallentaa kotivakuutusten tiedot tiedostoon.  
      * @param hakemisto tallennettavan tiedoston hakemisto
      * @throws SailoException jos talletus epäonnistuu
      */
     public void tallenna(String hakemisto) throws SailoException {
         File ftied = new File(hakemisto + "/kotivakuutus.dat");
         try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
             for (var har: alkiot) {
                 fo.println(har.toString());
             }
         } catch (FileNotFoundException ex) {
             throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
         }        
     }

     
     /**
      * Lukee harrastukset tiedostosta.
      * @param hakemisto tiedoston nimen alkuosa
      * @throws SailoException jos lukeminen epäonnistuu
     */
     public void lueTiedostosta(String hakemisto) throws SailoException {
         String nimi = hakemisto + "/kotivakuutus.dat";
         File ftied = new File(nimi);
         try (Scanner fi = new Scanner(new FileInputStream(ftied))) { 
             while ( fi.hasNext() ) {
                 String s = fi.nextLine().trim();
                 if ( "".equals(s) || s.charAt(0) == ';' ) continue;
                 Kotivakuutus koti = new Kotivakuutus();
                 koti.parse(s); 
                 lisaa(koti);
             }
         } catch ( FileNotFoundException e ) {
             throw new SailoException("Ei saa luettua tiedostoa " + nimi);
         }
     }

 
     /**
      * Haetaan kaikki asiakkaan Kotivakuutukset
      * @param tunnusnro asiakkaan tunnusnumero jolle kotivakuutuksia haetaan
      * @return tietorakenne jossa viiteet löydetteyihin kotivakuutuksiin
      * @example
      * <pre name="test">
      * #import java.util.*;
      * 
      *  Kotivakuutukset kotona = new Kotivakuutukset();
      *  Kotivakuutus koti21 = new Kotivakuutus(2); kotona.lisaa(koti21);
      *  Kotivakuutus koti11 = new Kotivakuutus(4); kotona.lisaa(koti11);
      *  Kotivakuutus koti22 = new Kotivakuutus(2); kotona.lisaa(koti22);
      *  Kotivakuutus koti12 = new Kotivakuutus(1); kotona.lisaa(koti12);
      *  Kotivakuutus koti23 = new Kotivakuutus(2); kotona.lisaa(koti23);
      *  Kotivakuutus koti51 = new Kotivakuutus(5); kotona.lisaa(koti51);
      *  
      *  List<Kotivakuutus> loydetyt;
      *  loydetyt = kotona.annaKotivakuutukset(1);
      *  loydetyt.size() === 0; 
      *  loydetyt.get(1) == koti12 === true;
      * </pre> 
      */
     public List<Kotivakuutus> annaKotivakuutukset(int tunnusnro) {
         
         List<Kotivakuutus> loytyneet = new ArrayList<Kotivakuutus>();
         for (Kotivakuutus koti: alkiot) 
             if (koti.getAsNro() == tunnusnro) loytyneet.add(koti);
         return loytyneet;
     }
     
     /**
      * Testiohjelma kotivakuutusten testailuun
      * @param args ei käytössä
      */
     public static void main(String[] args) {
         Kotivakuutukset kotona = new Kotivakuutukset();
         Kotivakuutus koti1 = new Kotivakuutus();
         koti1.testiVakuutus(2);
         Kotivakuutus koti2 = new Kotivakuutus();
         koti2.testiVakuutus(1);
         Kotivakuutus koti3 = new Kotivakuutus();
         koti3.testiVakuutus(2);
         Kotivakuutus koti4 = new Kotivakuutus();
         koti4.testiVakuutus(2);
     
         kotona.lisaa(koti1);
         kotona.lisaa(koti2);
         kotona.lisaa(koti3);
         kotona.lisaa(koti4);
     
         System.out.println("**************** Kotivakuutukset testi *******************");
     
         var koti13 = kotona.annaKotivakuutukset(2);
     
         for (Kotivakuutus koti : koti13) {
             System.out.print(koti.getAsNro() + " ");
             koti.tulosta(System.out);
         }
     }
     
     /**
      * tutkii onko jo olemassa vai ei
      * @param vakuutus mikä vakuutus
      */
     public void korvaaTaiLisaa(Kotivakuutus vakuutus){
         int id = vakuutus.getTunnusNro();
         for (int i = 0; i < alkiot.size(); i++) {
             if (alkiot.get(i).getTunnusNro() == id) {
                 alkiot.set(i, vakuutus);
                 return;
             }
         }
         lisaa(vakuutus);
     }
     
     /**
      * poistaa asiakkaan vakuutuksen
      * @param koti mikä vakuutus poistetaan
      * @return true jos onnistuu
      */
     public boolean poista(Kotivakuutus koti) {
        
         boolean ret = alkiot.remove(koti);
                  return ret;
  
}

    /** 
     * poistaa asiakkaan vakuutuksen
     * @param tunnusnro mikä id
     * @return n
     */
    public int poistaAsiakkaanVakuutus(int tunnusnro) {
        
        int n = 0;
        for (Iterator<Kotivakuutus> it = alkiot.iterator(); it.hasNext();) {
                Kotivakuutus har = it.next();
                if ( har.getAsNro() == tunnusnro ) {
                    it.remove();
                    n++;
               }
                
          }
        return n;
     }
}
