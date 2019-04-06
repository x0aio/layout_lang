package io.x0a.layoutlang


class LinkDefinition(
    layout: LayoutDefinition,
    val from: PositionDefinition,
    val to: PositionDefinition) {

    init {
        layout.links += this
    }

    var alias: String? = null
    var weight = 1

    infix fun with(block: LinkDefinition.() -> Unit) = this.also(block)

    override fun toString(): String = "Link(${from.name}>${to.name}, weight=$weight, alias=$alias)"
}