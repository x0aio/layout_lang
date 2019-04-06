package io.x0a.layoutlang

class LayoutDefinition {

    val areas = mutableListOf<AreaDefinition>()

    val positions = mutableListOf<PositionDefinition>()

    val links = mutableListOf<LinkDefinition>()

    fun area(name: String, block: AreaDefinition.() -> Unit) =
        AreaDefinition(this@LayoutDefinition, name).also(block)

    operator fun PositionDefinition.plus(target: PositionDefinition) =
        LinkDefinition(this@LayoutDefinition, this, target)

    fun print() {
        areas.forEach { println(it) }
        positions.forEach { println(it) }
        links.forEach { println(it) }
    }
}


fun layout(block: LayoutDefinition.() -> Unit) = LayoutDefinition().also(block)