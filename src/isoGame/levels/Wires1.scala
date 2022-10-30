package isoGame.levels
import isoGame.blocks.Transporter
import isoGame.{Point3, Point3Double}

class Wires1 extends Level {
    var name: String = "Wires1"
    val chunkSize: Point3 = Point3(16, 16, 16)
    val loadFromFile: Boolean = true
    override val playerSpawn: Point3Double = Point3Double(8,8,2)

    def buildLevel(): Unit = {
        terrain(Point3(8, 15, 1)) = new Transporter("SimpleDoor", 'D')
        terrain(Point3(7, 1, 7)) = new Transporter("MultiBox", 'E')
        terrain(Point3(8, 1, 7)) = new Transporter("MultiBox", 'E')
    }
}
