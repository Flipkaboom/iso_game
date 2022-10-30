package isoGame.levels
import isoGame.blocks.Transporter
import isoGame.{Point3, Point3Double}

class Blinker1 extends Level {
    var name: String = "Blinker1"
    val chunkSize: Point3 = Point3(8,8,16)
    val loadFromFile: Boolean = true

    def buildLevel(): Unit = {
        terrain(Point3(0,7,3)) = new Transporter("FirstRoom", 'A')
        terrain(Point3(7,0,4)) = new Transporter("PickUpBox1", 'B')
    }
}
