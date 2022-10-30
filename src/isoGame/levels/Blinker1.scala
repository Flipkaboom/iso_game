package isoGame.levels
import isoGame.{Point3, Point3Double}

class Blinker1 extends Level {
    var name: String = "Blinker1"
    val chunkSize: Point3 = Point3(8,8,16)
    val loadFromFile: Boolean = true
    val playerSpawn: Point3Double = Point3Double(7.5, 4.5, 3)

    def buildLevel(): Unit = {}
}
