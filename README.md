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


## Ejemplo

Esto es un ejemplo de como se describiría una instalación con las siguientes caracterísicas
 - Punto de entrada con los siguientes destinos:
    - 5 barras
    - continuar al punto de picking
 - Punto de picking con los siguientes destinos:
    - 2 salidas
    - recircular a la entrada
    

```
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
            + BusinessName("BAR_$i")
            + Bar
        }
    }
    
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

        default = + enterPosition with {
            alias = "PLC1_FORWARD"
        }
    }

    enterPosition + pickPosition with {
        alias = "PLC1_FORWARD"
        weight = 99
    }
}
```




