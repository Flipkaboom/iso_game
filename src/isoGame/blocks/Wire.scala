package isoGame.blocks

class Wire extends WireBlock {
    val name: String = "Wire"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    val neighbourPlusX: Boolean = false
    val neighbourMinX: Boolean = false
    val neighbourPlusY: Boolean = true
    val neighbourMinY: Boolean = true
    val neighbourPlusZ: Boolean = false
    val neighbourMinZ: Boolean = false
}
