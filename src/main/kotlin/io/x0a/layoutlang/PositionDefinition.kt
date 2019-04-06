package io.x0a.layoutlang

class PositionDefinition(
    private val layout: LayoutDefinition,
    val name: String
) {
    init {
        layout.positions += this
    }

    var default: LinkDefinition? = null

    var badges = mutableSetOf<Badge>()

    operator fun PositionDefinition.unaryPlus() = LinkDefinition(layout,this@PositionDefinition, this)

    operator fun Badge.unaryPlus() {
        badges.add(this)
    }

    override fun toString() = "Pos($name, $badges, default=${default?.to?.name})"

}

