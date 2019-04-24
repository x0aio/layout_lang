package io.x0a.layoutlang

class AreaDefinition(
    private val layout: LayoutDefinition,
    val name: String
) {
    init {
        layout.areas += this
    }

    var badges = mutableSetOf<Badge>()

    operator fun invoke(name: String, block: PositionDefinition.() -> Unit) =
        PositionDefinition(layout, name).also(block)

    operator fun invoke(name: String) =
        PositionDefinition(layout, name)

    operator fun Badge.unaryPlus() {
        badges.add(this)
    }

    override fun toString(): String = "Area($name, $badges)"
}