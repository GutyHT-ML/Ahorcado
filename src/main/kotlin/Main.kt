import kotlin.math.ceil
import kotlin.math.floor
import kotlin.random.Random
import Utils
import Utils.censorCount
import Utils.censorPercent
import Utils.doDebug
import Utils.errorChances
import Utils.hidePercentage
import Utils.printChoker
import Utils.title
import Utils.toError
import Utils.toInfo
import Utils.toInput
import Utils.toRainbow

val availableWords = listOf(
    "glimpse of us",
    "slow dancing in the dark",
    "run",
    "pide al tiempo",
    "mi voz",
    "wait a minute"
)



fun main(args: Array<String>) {
    var cycle = true
    var chances = errorChances

    println("Ahorcado".title())
    while (cycle) {
        val word = availableWords[Random.nextInt(availableWords.size)]
        val hiddenWord = StringBuilder().append(word censorPercent hidePercentage)
        println(hiddenWord.toInfo())
        while (hiddenWord.toString() != word && chances > 0) {
            printChoker(chances)
            var tryGuess = false
            if (hiddenWord.censorCount() < hiddenWord.length / 4) {
                println("Intentar adivinar la frase/palabra? (s/n)".toInput())
                tryGuess = readln() == "s"
            }
            if (tryGuess) {
                println("Introduce la palabra".toInput())
                val w = readln().lowercase()
                if (true) {
                    if (w == word) {
                        for (i in hiddenWord.indices) {
                            hiddenWord[i] = w[i]
                        }
                    } else {
                        chances--
                    }
                } else {
                    println("Introduce un caracter alfabetico".toError())
                }
            } else {
                println("Introduce una letra".toInput())
                val letter = readln()
                if (letter.matches(Regex("([aA-zZ])"))) {
                    val l : Char = letter.first().lowercaseChar()
                    if (word.contains(l)) {
                        for (i in word.indices) {
                            if (word[i] == l) {
                                hiddenWord[i] = word[i]
                            }
                        }
                        println(hiddenWord.toInfo())
                    } else {
                        chances--
                    }
                }
                else {
                    println("Introduce un caracter alfabetico".toError())
                    doDebug {
                        println("Si le sabes al regex")
                    }
                }
            }
        }
        if (chances > 0) {
            println("Felicidades :)".toRainbow())
        }
        println("De nuevo? (s/n)".toInput())
        if (readln() == "s") {
            cycle = true
            chances = errorChances
        } else {
            cycle = false
        }
    }


}

