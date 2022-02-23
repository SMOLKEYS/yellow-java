package yellow.content.emotion;

public class YellowState{
    
    private Emotion emotion = Emotion.neutral;
    
    public void set(Emotion yemotion){
        emotion = yemotion;
    }
    
    public Emotion getEmotion(){
        return emotion;
    }
    
    public enum Emotion{
        happy, 
        sad, 
        angry, 
        neutral, //normal state
        anxious, 
        panicked, 
        jealous, 
        annoyed,
        stressed,
        flustered,
        mischievious,
        mixed
    }
}