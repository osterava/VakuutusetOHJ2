package vakuutus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author olliterava, laidmale
 * @version 6.3.2023
 *
 */
public class Kotivakuutukset {
    private Collection<Kotivakuutus> alkiot = new ArrayList<Kotivakuutus>();
    
    
    /*
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
      *  loydetyt = kotona.annaKotivakuutukset(3);
      *  loydetyt.size() === 0; 
      *  loydetyt = kotona.annaKotivakuutukset(1);
      *  loydetyt.size() === 2; 
      *  loydetyt.get(0) == koti11 === true;
      *  loydetyt.get(1) == koti12 === true;
      *  loydetyt = kotona.annaKotivakuutukset(5);
      *  loydetyt.size() === 1; 
      *  loydetyt.get(0) == koti51 === true;
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


}
