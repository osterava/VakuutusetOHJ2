package vakuutus;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.HetuTarkistus;

/**
 * @author olliterava, laidmale
 * @version 21.2.2023
 * Vakuutuksen asiakas joka osaa mm. huolehtia itse tunnutNrosta ja tietää asiakkaan tiedot
 */
public class Asiakas {
 
    private int         tunnusNro=0;
    private String      nimi = "";
    private String      hetu = "";
    private String      katuosoite = "";
    private String      postinumero="";
    private String      postiosoite="";
    private String      puhelinnumero="";
    private String      kotitaloudenkoko= "0";
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
    public void naytaMarkoTiedoilla() {
       nimi="Mattilainen Marko" +" "+ HetuTarkistus.rand(1,4000);
       hetu=HetuTarkistus.arvoHetu();
       katuosoite="Maailmatie" + HetuTarkistus.rand(1,40);
       postinumero="12345";
       postiosoite="Äänekoski";
       puhelinnumero="124124124";
       postiosoite="Maailma";
       puhelinnumero="1234567890";
       kotitaloudenkoko="2";
       lisatietoja="ei lisätietoja";
        
    }
    
    /**
     * antaa asiakkaalle seuraavan rekisterinumeron eli id:n
     * @return asiakkaan uusi tunnusnro
     * @example
     * <pre name="test">
     *  Asiakas marko1 = new Asiakas();
     *  marko1.getTunnusNro() === 0;
     *  marko1.rekisteroi();
     *  
     *  Asiakas marko2 = new Asiakas();
     *  marko2.rekisteroi();
     *  int n1 = marko2.getTunnusNro();
     *  int n2 = marko2.getTunnusNro();
     *  n1 === n2;
     *  </pre>
     *  
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
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.parse("   3  |  Ankka Aku   | 030201-111C");
     *   asiakas.toString().startsWith("3|Ankka Aku|030201-111C|") === true;
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" +
                hetu + "|" +
                katuosoite + "|" +
                postinumero + "|" +
                postiosoite + "|" +
                puhelinnumero + '|' +
                kotitaloudenkoko + '|' +
                lisatietoja;
    }

    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Asiakas jasen = new Asiakas();
     *   jasen.parse("   3  |  Ankka Aku   | 030201-111C");
     *   jasen.getTunnusNro() === 3;
     *   jasen.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   jasen.rekisteroi();
     *   int n = jasen.getTunnusNro();
     *   jasen.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   jasen.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   jasen.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        katuosoite = Mjonot.erota(sb, '|', katuosoite);
        postinumero = Mjonot.erota(sb, '|', postinumero);
        postiosoite = Mjonot.erota(sb, '|', postiosoite);
        puhelinnumero = Mjonot.erota(sb, '|', puhelinnumero);
        kotitaloudenkoko = Mjonot.erota(sb, '|', kotitaloudenkoko);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }

    /**
     * @return montako kenttaa
     */
    public int getKenttia() {
        return 9;
    }

    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }

    
    /**
     * @return nimi
     */
    public String getNimi() {
        
        return this.nimi;
    }

    /** 
     * Antaa k:n kentän sisällön merkkijonona 
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */ 
    public String getAvain(int k) { 
        switch ( k ) { 
        case 0: return "" + tunnusNro; 
        case 1: return "" + nimi.toUpperCase(); 
        case 2: return "" + hetu; 
        case 3: return "" + katuosoite; 
        case 4: return "" + postinumero; 
        case 5: return "" + postiosoite; 
        case 6: return "" + puhelinnumero; 
        case 7: return "" + kotitaloudenkoko; 
        case 8: return "" + lisatietoja; 
        default: return "älä jaksa"; 
        } 
    } 
    
    /**
     * Palauttaa k:tta jäsenen kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "nimi";
        case 2: return "hetu";
        case 3: return "katuosoite";
        case 4: return "postinumero";
        case 5: return "postiosoite";
        case 6: return "puhelinnumero";
        case 7: return "kotitaloudenkoko";
        case 8: return "lisatietoja";
        default: return "Kokeile uudestaan";
        }
    }

    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro; 
        case 1: return "" + nimi.toUpperCase(); 
        case 2: return "" + hetu; 
        case 3: return "" + katuosoite; 
        case 4: return "" + postinumero; 
        case 5: return "" + postiosoite; 
        case 6: return "" + puhelinnumero; 
        case 7: return "" + kotitaloudenkoko; 
        case 8: return "" + lisatietoja; 
        default: return "Äääliö";
        }
    }

    
   /**     
    * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
    * @param k kuinka monennen kentän arvo asetetaan
    * @param jono jonoa joka asetetaan kentän arvoksi
    * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
    * @example
    * <pre name="test">
    *   Jasen jasen = new Jasen();
    *   jasen.aseta(1,"Ankka Aku") === null;
    *   jasen.aseta(2,"kissa") =R= "Hetu liian lyhyt"
    *   jasen.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
    *   jasen.aseta(2,"030201-111C") === null; 
    *   jasen.aseta(9,"kissa") === "Liittymisvuosi väärin jono = \"kissa\"";
    *   jasen.aseta(9,"1940") === null;
    * </pre>
    */
    public String aseta(int k, String jono) {
        String aasi = " " + kotitaloudenkoko;
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            hetu = tjono;
            return null;
        case 3:
            katuosoite = tjono;
            return null;
        case 4:
            postinumero = tjono;
            return null;
        case 5:
            postiosoite = tjono;
            return null;
        case 6:
            puhelinnumero = tjono;
            return null;
        case 7:
            aasi = tjono;
            aasi.toUpperCase();
            return null;
        case 8:
            lisatietoja = tjono;
            return null;
        default:
            return "yritä uudestaan";
        }
    }


    @Override
    public Asiakas clone() throws CloneNotSupportedException {
        Asiakas uusi;
        uusi = (Asiakas)super.clone();
        return uusi;
    }
    public String setNimi(String s) {
        nimi = s;
        return null;
    }
    public String getHetu() {
        return hetu;
    }
    public String getKatuosoite() {
        return katuosoite;
    }
    public String getPostinumero() {
        return postinumero;
    }
    public String setHetu(String s) {
        hetu = s;
        return null;
    }
    public String setKatuosoite(String s) {
       katuosoite = s;
        return null;
    }
    public String setPostinumero(String s) {
        postinumero = s;
        return null;
    }
    public String getKaupunki() {
        return postiosoite;
    }
    public String getPuhelin() {
        return puhelinnumero;
        
    }
    public String getKoti() {
        return kotitaloudenkoko;
    }
    public String getInfo() {
        return lisatietoja;
    }


}
