package io.x0a.layoutlang

//Los comportamientos especificos de los componentes se modelan como Badges.
data class BusinessName(val name: String) : Badge

object RequestTarget : Badge {

    override fun toString() = "RequestTarget"
}

object Output : Badge {

    override fun toString() = "Output"
}

object Bar : Badge {

    override fun toString() = "Bar"
}

data class Device(val host: String, val port: Int) : Badge



fun main() {


    layout {
        //Este layout solo tiene una area gestionada por el PLC1
        val inductionsArea = area("PLC1") {
            + Device(host = "localhost", port = 80)
        }

        // Se crea la posición de entrada
        val enterPosition = inductionsArea("PLC1_001") {
            // Definicion de nombre de negocio
            + BusinessName("INPUT")
            // Comportamiento de solicitar destino
            + RequestTarget
        }

        // Se crean 5 barras con el comportamiento barra.
        for (i in 1..5) {
            enterPosition + inductionsArea("PLC1_10$i") {
                +BusinessName("BAR_$i")
                +Bar
            }
        }

        // Posición donde se decide si recircular o salir.
        val pickPosition = inductionsArea("PLC1_201") {
            + RequestTarget

            + inductionsArea("PLC1_202") {
                + BusinessName("OUTPUT_1")
                + Output
            }

            + inductionsArea("PLC1_203") {
                + BusinessName("OUTPUT_1")
                + Output
            }

            // En la conexión con la entrada se debe enviar la posicion "FORWARD", sería el destino por defecto
            default = + enterPosition with {
                alias = "PLC1_FORWARD"
            }
        }

        // Se añade la conexión posición de entrada, posición de pick
        enterPosition + pickPosition with {
            alias = "PLC1_FORWARD"
            weight = 99
        }
    }.print()
}
