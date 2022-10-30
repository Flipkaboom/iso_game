package isoGame.levels
import isoGame.blocks.Transporter
import isoGame.{Point3, Point3Double}

class PickUpBox1 extends Level {
    var name: String = "PickUpBox1"
    val chunkSize: Point3 = Point3(12, 12, 16)
    val loadFromFile: Boolean = true

    def buildLevel(): Unit = {
        terrain(Point3(0, 7, 2)) = new Transporter("Blinker1", 'B')
        terrain(Point3(6, 0, 4)) = new Transporter("SimpleDoor", 'C')
    }
}
