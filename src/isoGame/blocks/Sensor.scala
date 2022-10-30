package isoGame.blocks
import isoGame.{GameState, Point3}

class Sensor extends WireBlock {
    val name: String = "Sensor"
    var collision: Boolean = true
    var transparent: Boolean = false
    var visible: Boolean = true

    val neighbourPlusX: Boolean = true
    val neighbourMinX: Boolean = true
    val neighbourPlusY: Boolean = true
    val neighbourMinY: Boolean = true
    val neighbourPlusZ: Boolean = false
    val neighbourMinZ: Boolean = true

    override def update(p: Point3): Unit = {
        super.update(p)
        if(GameState.terrain(p + Point3(z = 1)).name != "Air"){
            power(p, p)
        }
        else {
            unPower(p, p)
        }
    }
}
