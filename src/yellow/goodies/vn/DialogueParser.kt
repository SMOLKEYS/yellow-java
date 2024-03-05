package yellow.goodies.vn

import arc.audio.Sound
import arc.struct.*
import mindustry.gen.Sounds

@Suppress("KotlinConstantConditions")
object DialogueParser {

    var testString = """
    s "This is a test-run of the dialogue parser I made."
    s "Right now, it's very simple and buggy."
    y "It will receive improvements, though!"
    s "Well then..."
    s "Uh-" autoskip 12
    s "Um-" autoskip 12
    s "Err-" autoskip 12
    s "Mhh-" autoskip 12
    s "Anyways...!"
    s "See you later" nl "for now" nl 2 "I guess."
    """.trimIndent()

    private var lastKeyword = "nothing"
    private val cache = ObjectMap<String, Dialogue>()

    @JvmStatic
    fun parseString(input: String): Dialogue {
        //very basic and wip
        val parts = input.trimIndent().split("\n")
        val map = ObjectMap<Int, DialogueIndex>()

        val fStrings = arrayOfNulls<String>(parts.size)

        var index = -1
        var emptyLines = 0


        parts.forEach parts@{p ->
            var dquotes = 0
            var encounteredWhitespace = false
            var p1 = p
            val dindex = DialogueIndex()

            if(p1.isBlank()){
                emptyLines++
                return@parts
            }

            index++
            map.put(index, dindex)

            val narrator = p[0] == '"'
            if(narrator){
                dindex.character = InteractiveCharacter.getByShorthand("narrator")
            }

            var fString = ""
            var prevChar = ' '

            p1.forEach p1@{c1 ->
                if(narrator){
                    if(dquotes == 2) return@p1
                    if(c1 == '"'){
                        if(prevChar == '\\') {
                            fString += c1
                            prevChar = c1
                            return@p1
                        }
                        dquotes++
                        return@p1
                    }
                    prevChar = c1
                    fString += c1
                }else{
                    if(encounteredWhitespace){
                        val t = fString.trim()
                        dindex.character = InteractiveCharacter.getByShorthand(t)
                        if(dindex.character == null) throw RuntimeException("Dialogue parsing failed: Nonexistent character reference ($t) at line ${index + 1}")
                        return@p1
                    }
                    if(c1.isWhitespace()){
                        encounteredWhitespace = true
                        return@p1
                    }
                    fString += c1
                }
            }

            if(narrator) {
                p1 = p1.substring(fString.length + 2).trimStart()
                fStrings[index] = fString
                parseKeywords(fStrings, p1, dindex, index)
            }else{
                p1 = p1.substring(fString.length).trimStart()
                dquotes = 0
                fString = ""
                prevChar = ' '
                p1.forEach p1@{c1 ->
                    if(dquotes == 2) return@p1
                    if(c1 == '"'){
                        if(prevChar == '\\') {
                            fString += c1
                            prevChar = c1
                            return@p1
                        }
                        dquotes++
                        return@p1
                    }
                    prevChar = c1
                    fString += c1
                }
                fStrings[index] = fString
                parseKeywords(fStrings, p1.substring(fString.length + 2).trimStart(), dindex, index)
            }
        }

        //create a new array with null indexes removed
        val tfStrings = Seq<String>()

        fStrings.forEach {s ->
            if(s != null) tfStrings.add(s)
        }

        //caster blaster
        return Dialogue(tfStrings.toArray(String::class.java), map)
    }

    private fun parseKeywords(source: Array<String?>, input: String?, dataOutput: DialogueIndex?, index: Int){
        if(!input.isNullOrEmpty() && dataOutput != null){
            when{
                input.startsWith("autoskip") -> {
                    if(input == "autoskip".trim()){
                        dataOutput.autoskip = true
                        return
                    }
                    var p1 = input.removePrefix("autoskip").trimStart()
                    var delay = ""
                    var argP = 0

                    p1.forEach p1@{c1 ->
                        if(argP == 1) return@p1
                        if(c1.isWhitespace()){
                            argP++
                            return@p1
                        }
                        if(!c1.isDigit()) return@p1
                        delay += c1
                    }


                    dataOutput.autoskip = true
                    if(delay.isNotBlank()) {
                        val finalDelay = delay.toFloatOrNull() ?: throw RuntimeException("Dialogue parsing failed: Invalid delay parameter at line ${index + 1}")
                        dataOutput.autoskipDelay = finalDelay
                    }

                    p1 = p1.substring(delay.length).trimStart()

                    lastKeyword = "autoskip"
                    parseKeywords(source, p1, dataOutput, index)
                }
                input.startsWith("nl") -> {
                    if(lastKeyword == "autoskip") throw RuntimeException("Dialogue parsing failed: Use of `nl` after `autoskip` at line ${index + 1}")
                    if(input == "nl".trim()) throw RuntimeException("Dialogue parsing failed: Stray `nl` at line ${index + 1}")

                    var p1 = input.removePrefix("nl").trimStart()
                    var d = ""
                    var prevChar = ' '
                    var index2 = 0
                    var spaces = 0
                    var amount = ""

                    p1.forEach p1@{c1 ->
                        if(index2 == 2) return@p1

                        if(c1.isDigit() && spaces != 2){
                            amount += c1
                            return@p1
                        }else if(c1 == ' ' && spaces != 2){
                            spaces++
                            amount += c1
                            return@p1
                        }else if(!c1.isDigit()){
                            spaces = 2
                        }

                        if(c1 == '"'){
                            if(prevChar == '\\'){
                                d += '\"'
                                prevChar = '"'
                                return@p1
                            }
                            index2++
                            return@p1
                        }else if(c1 != '"' && !c1.isWhitespace() && index2 == 0) throw RuntimeException("Dialogue parsing failed: Expected string after `nl` call at line ${index + 1}")
                        prevChar = c1
                        d += c1
                    }

                    if(index2 != 2) throw RuntimeException("Dialogue parsing failed: Missing closing double-quote for `nl` call at line ${index + 1}")

                    val am = amount.trim().toIntOrNull()

                    source[index] = source[index] + "\n".repeat(am ?: 1) + d

                    p1 = p1.substring(d.length + if(amount.isBlank()) 2 else amount.length + 2).trimStart()

                    lastKeyword = "nl"
                    parseKeywords(source, p1, dataOutput, index)
                }
                input.startsWith("sfx") -> {
                    if(input == "sfx".trim()) throw RuntimeException("Dialogue parsing failed: Stray `sfx` at line ${index + 1}")

                    var p1 = input.removePrefix("sfx").trimStart()
                    var target = 0
                    var sound = ""
                    var vol = ""
                    var argP = 0
                    val p2: String

                    when{
                        p1.startsWith("start") -> p2 = p1.removePrefix("start").trimStart()
                        p1.startsWith("end") -> {
                            target = 1
                            p2 = p1.removePrefix("end").trimStart()
                        }
                        else -> throw RuntimeException("Dialogue parsing failed: Expected `start` or `end` for `sfx` but got `$p1` instead at line ${index + 1}")
                    }

                    p2.forEach p2@{c1 ->
                        if(c1.isWhitespace()) argP++

                        when(argP){
                            0 -> {
                                sound += c1
                            }
                            1 -> {
                                if(!c1.isDigit()){
                                    argP++
                                    return@p2
                                }
                                vol += c1
                            }
                        }
                    }

                    try{
                        if(target == 0){
                            dataOutput.startSfx = Sounds::class.java.getDeclaredField(sound).get(null) as Sound
                        }else{
                            dataOutput.endSfx = Sounds::class.java.getDeclaredField(sound).get(null) as Sound
                        }
                    }catch(e: Exception){
                        if(sound.isNotBlank()){
                            throw RuntimeException("Dialogue parsing failed: Sound `$sound` does not exist at line ${index + 1}", e)
                        }else{
                            throw RuntimeException("Dialogue parsing failed: No data passed for `sfx` at line ${index + 1}")
                        }
                    }

                    if(vol.isNotBlank()){
                        val fVol = vol.toFloatOrNull() ?: throw RuntimeException("Dialogue parsing failed: Invalid volume parameter at line ${index + 1}")

                        dataOutput.sfxVol = fVol
                    }

                    p1 = p1.substring(sound.length + vol.length + if(target == 0) 6 else 4).trimStart()

                    lastKeyword = "sfx"
                    parseKeywords(source, p1, dataOutput, index)
                }
                else -> {}
            }
        }else{
            lastKeyword = "nothing"
        }
    }



    open class DialogueIndex{
        var character: InteractiveCharacter? = null
        var dialogueStart: () -> Unit = {}
        var dialogueEnd: () -> Unit = {}
        var startSfx = Sounds.none
        var endSfx = Sounds.none
        var sfxVol = 1f
        var autoskip = false
        var autoskipDelay = 0f

        override fun toString(): String {
            return "DialogueIndex[character=$character, dialogueStart=$dialogueStart, dialogueEnd=$dialogueEnd, startSfx=$startSfx, endSfx=$endSfx, sfxVol=$sfxVol, autoskip=$autoskip, autoskipDelay=$autoskipDelay]"
        }
    }
}