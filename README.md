# Layout Lang

Este proyecto es un experimento de [DSL](https://en.wikipedia.org/wiki/Domain-specific_language) 
pero orientado a modelar un dominio real. Pretende ser capaz de describir los mapas de instalaciones
automáticas en fábricas y almacenes.

## Componentes

### Badge
Etiqueta que aporta un comportamiento o completa la información de Area y Position.
Se añadir los comportamientos necesarias para modelar el sistema.

### Area
Zona a la que pertenecen las posiciones. Se crea como `area("NOMBRE_AREA")`
Se pueden añadir badges con el operador `+`

### Position
Posición dentro de un área. Se crean como `variable_area("NOMBRE_POSICION")`
Se pueden añadir badges con el operador `+`. Puede asociarsele un Link por defecto.

### Link
Enlace entre dos posiciones. Se crea sumando (`+`) dos posiciones 

Puede tener un alias o un weight (peso), que se definen con `with { ... }'



```
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
```




