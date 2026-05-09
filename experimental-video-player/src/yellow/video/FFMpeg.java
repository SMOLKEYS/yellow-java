package yellow.video;

public class FFMpeg {

    public static void setDebugLogging (boolean debugLogging) {
        setDebugLoggingNative(debugLogging);
    }

    /*
     * Native functions
     * @off
     */

	/*JNI
		#include "Utilities.h"
	 */

    /**
     * This function can be used to turn on/off debug logging of the native code.
     *
     * @param debugLogging whether logging should be turned on or off
     */
    private native static void setDebugLoggingNative (boolean debugLogging);/*
		debug(debugLogging);
	 */
}
