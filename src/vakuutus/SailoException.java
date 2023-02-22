package vakuutus;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author olliterava, laidmale
 * @version 21.2.2023
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}