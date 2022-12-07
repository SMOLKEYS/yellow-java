package yellow.game;

//rune delta
public interface Spellcaster{
    
    int getTensionPoints();
    
    void setTensionPoints(int set);
    
    void addTensionPoints(int amount);
    
    void removeTensionPoints(int amount);
}
