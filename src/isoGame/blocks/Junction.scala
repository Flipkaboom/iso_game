package isoGame.blocks

class Junction extends WireBlock {
    val name: String = "Junction"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    val neighbourPlusX: Boolean = true
    val neighbourMinX: Boolean = true
    val neighbourPlusY: Boolean = true
    val neighbourMinY: Boolean = true
    val neighbourPlusZ: Boolean = true
    val neighbourMinZ: Boolean = true
}
