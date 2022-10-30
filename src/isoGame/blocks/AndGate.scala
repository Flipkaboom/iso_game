package isoGame.blocks

import isoGame.Point3

class AndGate extends WireBlock {
    val name: String = "AndGate"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    val neighbourPlusX: Boolean = false
    val neighbourMinX: Boolean = true
    val neighbourPlusY: Boolean = false
    val neighbourMinY: Boolean = true
    val neighbourPlusZ: Boolean = false
    val neighbourMinZ: Boolean = false

    var leftPowered: Boolean = false
    var rightPowered: Boolean = false

    override def power(pOrig: Point3, pDest: Point3): Unit = {
        if(pOrig.x > pDest.x) rightPowered = true
        else if(pOrig.y > pDest.y ) leftPowered = true

        if(rightPowered && leftPowered) super.power(pDest, pDest)
    }

    override def unPower(pOrig: Point3, pDest: Point3): Unit = {
        if (pOrig.x > pDest.x) rightPowered = false
        if (pOrig.y > pDest.y) leftPowered = false

        if(!(rightPowered && leftPowered)) super.unPower(pDest, pDest)
    }
}
