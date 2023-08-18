package yellow

import com.github.mnemotechnician.mkui.delegates.setting

/** Variables that are saved to your save file. Does not reset even when the game is reloaded. */
object YellowPermVars {
    /** The setting string head. DO NOT MODIFY, EVER! */
    val syn = "yellow-"

    var sourceBERepo by setting("https://github.com/SMOLKEYS/yellow-java-builds/releases/latest/download/yellow-java.jar", syn)
    var sourceReleaseRepo by setting("https://github.com/SMOLKEYS/yellow-java/releases/latest/download/yellow-java.jar", syn)

    var removeAllowed by setting(false, syn)
    var verboseLoggering by setting(false, syn)
    var internalLoggering by setting(false, syn)
    var weaponSanityCheck by setting(true, syn)

    var temporary by setting(false, syn)

    private var affinity by setting(0f, syn)

    fun getAffinityQ() = affinity

    fun setAffinityQ(value: Float) {
        affinity += value
        if (affinity > 100f) {
            affinity = 100f
        }
    }
}
