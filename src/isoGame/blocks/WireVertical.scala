package isoGame.blocks

class WireVertical extends WireBlock {
    val name: String = "WireVertical"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    val neighbourPlusX: Boolean = false
    val neighbourMinX: Boolean = false
    val neighbourPlusY: Boolean = false
    val neighbourMinY: Boolean = false
    val neighbourPlusZ: Boolean = true
    val neighbourMinZ: Boolean = true
}
