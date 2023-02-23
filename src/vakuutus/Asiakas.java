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
        aku.naytaAkuTiedoilla();
        aku.tulosta(System.out);

        aku2.naytaAkuTiedoilla();
        aku2.tulosta(System.out);

        
    }

    
    
    /**
     * apumetodi jolla täytetään testiarvot jäsenelle
     * todo: poista kun kaikki toimii :)
     */
    public void naytaAkuTiedoilla() {
       nimi="Urho Haukka" +" "+ HetuTarkistus.rand(1,4000);
       hetu=HetuTarkistus.arvoHetu();
       katuosoite="Paratiisitie" + HetuTarkistus.rand(1,40);
       postinumero="12345";
       postiosoite="Äänekoski";
       puhelinnumero="124124124";
       kotitaloudenkoko=3;
       lisatietoja="ei lisätietoja";
        
    }
    /**
     * antaa asiakkaalle seuraavan rekisterinumeron 
     * @return asiakkaan uusi tunnusnro
     * @example
     * <pre name="test">
     *  Asiakas aku1=new Asiakas();
     *  aku1.getTunnusNro()===0;
     *  aku1.rekisteroi();
     *  
     *  Asiakas aku2=new Asiakas();
     *  aku2.rekisteroi();
     *  int n1=aku1.getTunnusNro();
     *  int n2= aku2.getTunnusNro();
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
