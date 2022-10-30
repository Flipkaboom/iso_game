package isoGame.levels
import isoGame.Point3
import isoGame.blocks.Transporter

class SimpleDoor extends Level {
    var name: String = "SimpleDoor"
    val chunkSize: Point3 = Point3(8, 8, 16)
    val loadFromFile: Boolean = true

    def buildLevel(): Unit = {
        terrain(Point3(3, 7, 1)) = new Transporter("PickUpBox1", 'C')
        terrain(Point3(4, 7, 1)) = new Transporter("PickUpBox1", 'C')
        terrain(Point3(4, 0, 3)) = new Transporter("Wires1", 'D')
        terrain(Point3(3, 0, 3)) = new Transporter("Wires1", 'D')
    }
}
