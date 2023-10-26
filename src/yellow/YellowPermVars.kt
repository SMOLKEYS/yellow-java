package yellow

import com.github.mnemotechnician.mkui.delegates.setting

/** Variables that are saved to your save file. Does not reset even when the game is reloaded. */
object YellowPermVars {
    /** The setting string head. DO NOT MODIFY, EVER! */
    val syn = "yellow-settings-"

    var sourceBERepo by setting("https://github.com/SMOLKEYS/yellow-java-builds/releases/latest/download/yellow-java.jar", syn)
    var sourceReleaseRepo by setting("https://github.com/SMOLKEYS/yellow-java/releases/latest/download/yellow-java.jar", syn)
    var removeAllowed = false
    var internalLoggering by setting(false, syn)
    var weaponSanityCheck by setting(true, syn)

    private var _affinity by setting(0f, syn)
    var affinity
      get() = _affinity
      set(value){
        _affinity = if(value >= 100f) 100f else value
    }
}
