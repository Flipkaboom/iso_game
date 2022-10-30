package isoGame.blocks

class WireMirrored extends WireBlock {
    val name: String = "WireMirrored"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    val neighbourPlusX: Boolean = true
    val neighbourMinX: Boolean = true
    val neighbourPlusY: Boolean = false
    val neighbourMinY: Boolean = false
    val neighbourPlusZ: Boolean = false
    val neighbourMinZ: Boolean = false
}
