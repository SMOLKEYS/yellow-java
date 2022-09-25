package yellow.game

import com.github.mnemotechnician.mkui.delegates.setting

/** Variables that are saved to your save file. Does not reset even when the game is reloaded. From Ion. */
object YellowPermVars {
    /** The setting string head. DO NOT MODIFY, EVER! */
    val syn = "yellow-"
    
    var removeAllowed by setting(false, syn)
}
