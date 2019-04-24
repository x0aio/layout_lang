package io.x0a.layoutlang

//Los comportamientos especificos de los componentes se modelan como Badges.
data class BusinessName(val name: String) : Badge

object RequestTarget : Badge {

    override fun toString() = "RequestTarget"
}

data class Output(val name: String) : Badge {

    override fun toString() = "Output"
}

data class Bar(val name: String, val length: Length) : Badge {

    override fun toString() = "Bar"
}

data class Length(val value: Float)

val Int.meters: Length
    get() = Length(toFloat())

data class Device(val host: String, val port: Int) : Badge



fun main() {


    layout {
        val inductionsArea = area("PLC1") {
            + Device(host = "localhost", port = 80)
        }

        val enterPosition = inductionsArea("PLC1_001") {
            + BusinessName("INPUT")
            + RequestTarget
        }

        for (i in 1..5) {
            enterPosition + inductionsArea("PLC1_10$i") {
                + Bar("BAR_$i", 5.meters)
            }
        }

        val pickPosition = inductionsArea("PLC1_201") {
            + RequestTarget

            + inductionsArea("PLC1_202") {
                + Output("OUTPUT_1")
            }

            + inductionsArea("PLC1_203") {
                + Output("OUTPUT_1")
            }

            default = + enterPosition with {
                alias = "PLC1_FORWARD"
            }
        }

        enterPosition + pickPosition with {
            alias = "PLC1_FORWARD"
            weight = 99
        }
    }.print()
}
