package yellow.game;

@SuppressWarnings("ClassCanBeRecord")
public class YellowEventType{

    /** Fired at the start of {@link Yellow#Yellow() Yellow}'s main constructor. Cannot be used by extensions! */
    public static class YellowPreInitializationEvent{}
    /** Fired at the start {@link YellowVars#init()}. */
    public static class YellowFirstStageInitializationEvent{}
    /** Fired at the very end of {@link YellowVars#init()}. */
    public static class YellowSecondStageInitializationEvent{}
    /** Fired when Yellow has fully loaded (more specifically, when the client load event listener in {@link YellowVars#init()} has fully completed). */
    public static class YellowFinalStageInitializationEvent{}
    /** Fired when Yellow's content (extension content included) has been loaded. (see {@link Yellow#loadContent()})*/
    public static class YellowContentInitEvent{}

    public static class DeathStopEvent{
        public final MultiLifeUnitEntity unit;

        public DeathStopEvent(MultiLifeUnitEntity unit){
            this.unit = unit;
        }
    }
}
