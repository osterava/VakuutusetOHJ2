package vakuutus;

import java.io.OutputStream;
import java.io.PrintStream;

import kanta.HetuTarkistus;

/**
 * @author olliterava, laidmale
 * @version 21.2.2023
 *
 */
public class Asiakas {
 
    private int         tunnusNro=0;
    private String      nimi = "";
    private String      hetu = "";
    private String      katuosoite = "";
    private String      postinumero="";
    private String      postiosoite="";
    private String      puhelinnumero="";
    private int         kotitaloudenkoko=0;
    private String      lisatietoja="";
    
    
    private static int seuraavaNro =1;
    /**
     * 
     */
    public Asiakas() {
       //ei tarvitse
    }
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Asiakas aku = new Asiakas(), aku2 = new Asiakas();
        aku.rekisteroi();
        aku2.rekisteroi();
        aku.tulosta(System.out);
        aku.naytaMarkoTiedoilla();
        aku.tulosta(System.out);

        aku2.naytaMarkoTiedoilla();
        aku2.tulosta(System.out);

        
    }

    
    
    /**
     * apumetodi jolla täytetään testiarvot jäsenelle
     * todo: poista kun kaikki toimii :)
     */
<<<<<<< HEAD
    public void naytaAkuTiedoilla() {
       nimi="Urho Haukka" +" "+ HetuTarkistus.rand(1,4000);
=======
    public void naytaMarkoTiedoilla() {
       nimi="Mattilainen Marko" +" "+ HetuTarkistus.rand(1,4000);
>>>>>>> afb9ecea888358178db2fdf417ced2015b398f3e
       hetu=HetuTarkistus.arvoHetu();
       katuosoite="Maailmatie" + HetuTarkistus.rand(1,40);
       postinumero="12345";
<<<<<<< HEAD
       postiosoite="Äänekoski";
       puhelinnumero="124124124";
       kotitaloudenkoko=3;
=======
       postiosoite="Maailma";
       puhelinnumero="1234567890";
       kotitaloudenkoko=2;
>>>>>>> afb9ecea888358178db2fdf417ced2015b398f3e
       lisatietoja="ei lisätietoja";
        
    }
    /**
     * antaa asiakkaalle seuraavan rekisterinumeron eli id:n
     * @return asiakkaan uusi tunnusnro
     * @example
     * <pre name="test">
     *  Asiakas marko1=new Asiakas();
     *  marko1.getTunnusNro()===0;
     *  marko1.rekisteroi();
     *  
     *  Asiakas marko2=new Asiakas();
     *  marko2.rekisteroi();
     *  int n1=marko2.getTunnusNro();
     *  int n2= marko2.getTunnusNro();
     *  n1===n2-1;
     *  /pre>
     */
    public int rekisteroi() {
        this.tunnusNro=seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
        
    }
    
    /**
     * palauttaa jäsenen tunnusnron
     * @return jäsenen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.nimi);
        out.println(String.format("%03d", tunnusNro)+ " "+nimi+" "+hetu);
        out.println(" "+katuosoite+" " + postinumero + " "+ postiosoite);
        out.println( "puhelinnumero: " + puhelinnumero);
        out.println( "kotitalouden koko: " + kotitaloudenkoko);
        out.println( " "+ lisatietoja);
    }
    
    /**
     * tulostetaan asiakkaan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta (OutputStream os) {
        tulosta (new PrintStream (os));
    }
    
    
    /**
     * @return nimi
     */
    public String getNimi() {
        
        return this.nimi;
    }

}
