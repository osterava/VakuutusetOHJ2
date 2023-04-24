package kanta;


    public interface Tietue {

    
   
    public abstract int getKenttia();

    public abstract int ekaKentta();

    public abstract String getKysymys(int k);


    public abstract String anna(int k);

    
    public abstract String aseta(int k, String s);


    
    public abstract Tietue clone() throws CloneNotSupportedException;

    @Override
    public abstract String toString();

}
