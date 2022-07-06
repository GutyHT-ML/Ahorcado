import kotlin.math.floor
import kotlin.random.Random

object Utils {
    const val errorChances = 9
    const val hidePercentage = 80
    const val debug = false

    const val headerLen = 50
    const val rC = "\u001b[31m"
    const val gC = "\u001B[32m"
    const val bC = "\u001B[34m"
    const val wC = "\u001b[0m"
    const val pC = "\u001B[35m"
    const val cC = "\u001B[36m"
    val colors = listOf<String>(
        rC,
        gC,
        bC,
        wC,
        pC,
        cC
    )

    fun doDebug(onDebug: () -> Unit) {
        if (debug) {
            onDebug()
        }
    }

    infix fun CharSequence.censorPercent (percent: Int): String {
        var p = percent
        if (p > 100) {
            p = 100
        }
        if (p < 0 ) {
            p = 0
        }
        val charCount = this.length
        val charHide = floor((charCount * p / 100).toDouble()).toInt() - this.spanCount()
        doDebug {
            println("$charHide - $charCount - $p")
        }
        var hiddenWord = StringBuilder().append(this)
        var hiddenChars = hiddenWord.censorCount()
        while (hiddenChars != charHide) {
            for (i in hiddenWord.indices) {
                if (Random.nextBoolean() && hiddenWord[i] != ' ') {
                    hiddenWord[i] = '_'
                } else {
                    hiddenWord[i] = this[i]
                }
            }
            hiddenChars = hiddenWord.censorCount()
        }
        return hiddenWord.toString()
    }

    fun CharSequence.censorCount (): Int {
        var count = 0
        for (i in this.indices) {
            if (this[i] == '_') {
                count++
            }
        }
        return count
    }

    fun CharSequence.spanCount() : Int {
        var c = 0
        for (i in this.indices) {
            if (this[i] == ' ') {
                c++
            }
        }
        return c
    }

    fun CharSequence.toRainbow(): String {
        val st = StringBuilder()
        for (i in this.indices) {
            val r = Random.nextInt(colors.size)
            st.append(colors[r])
            st.append(this[i])
            st.append(wC)
        }
        return st.toString()
    }

    fun CharSequence.toBlue(): String {
        return "$bC$this$wC"
    }

    fun CharSequence.toInput(): String {
        return "$pC$this$wC"
    }

    fun CharSequence.toInfo(): String {
        return "$cC$this$wC"
    }

    fun CharSequence.toError(): String {
        return "${rC}$this$wC"
    }

    fun String.title(): String {
        val scores = headerLen - this.length
        var scoreString = ""
        for (i in 0..scores / 2) {
            scoreString += "-"
        }
        return "$scoreString$this$scoreString"
    }

    fun StringBuilder.replaceAt(char: Char, index: Collection<Int>) {
        if (index.any { it !in this.indices}) return
        for(i in index) {
            this[i] = char
        }
        doDebug {
            println(this.toString())
        }
    }

    fun printChoker (currentChances: Int) {
        val errors = (currentChances * 100 / errorChances)
        doDebug { println(errors) }
        when (errors) {
            0 -> {
                println("""
                    _________
                    |       ( )
                    |       -|-
                    |       / \
                    |
                    [[[[[Oxigeno: 0%]]]]]
                """.trimIndent().toError())
            }
            in 1..33 -> {
                println("""
                    _________
                    |       ( )
                    |       -|-
                    |       / \
                    |
                    [[[[[Oxigeno: $errors%]]]]]
                """.trimIndent())
            }
            in 34..66 -> {
                println("""
                    _________
                    |       ( )
                    |       -|-
                    |   
                    |
                    [[[[[Oxigeno: $errors%]]]]]
                """.trimIndent())
            }
            in 67..99 -> {
                println("""
                    _________
                    |       ( )
                    |        |    
                    |  
                    |
                    [[[[[Oxigeno: $errors%]]]]]
                """.trimIndent())
            }
            in 100..Int.MAX_VALUE -> {
                println("""
                    _________
                    |       |
                    |       |
                    |       |
                    |
                    [[[[[Oxigeno: 100%]]]]]
                """.trimIndent().toBlue())
            }
            else -> {
                println("""
                    _________
                    |       ( )
                    |       -|-
                    |       / \
                    |
                    [[[[[Oxigeno: 0%]]]]]
                """.trimIndent().toError())
            }
        }
    }
}