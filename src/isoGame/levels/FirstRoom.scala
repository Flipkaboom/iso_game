package isoGame.levels
import isoGame.blocks.Transporter
import isoGame.{Point3, Point3Double}

class FirstRoom extends Level {
    var name: String = "FirstRoom"
    val chunkSize: Point3 = Point3(8, 8, 16)
    val loadFromFile: Boolean = true
    override val playerSpawn: Point3Double = Point3Double(4.5, 4.5, 4)

    def buildLevel(): Unit = {
        terrain(Point3(3,0,4)) = new Transporter("Blinker1", 'A')
        terrain(Point3(4,0,4)) = new Transporter("Blinker1", 'A')
    }
}
