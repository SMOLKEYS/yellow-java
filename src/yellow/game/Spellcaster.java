package yellow.game;

//rune delta
public interface Spellcaster{
    
    float getTensionPoints();
    
    void setTensionPoints(float set);
    
    void addTensionPoints(float amount);
    
    void removeTensionPoints(float amount);
}
