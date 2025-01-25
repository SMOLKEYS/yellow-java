package yellow.comp;

public interface Revivec{
    
    int lives();
    
    void lives(int lives);
    
    float livesf();
    
    default boolean hasLives(){
        return lives() > 0;
    }
    
    void removeLife();
}