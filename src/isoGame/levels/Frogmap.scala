package isoGame.levels
import isoGame.{Point3, Point3Double}
import isoGame.blocks.Transporter
import isoGame.entities.Frog

class Frogmap extends Level {
    var name: String = "Frogmap"
    val chunkSize: Point3 = Point3(16, 16, 24)
    val loadFromFile: Boolean = true

    def buildLevel(): Unit = {
        terrain(Point3(10,11,8)) = new Transporter("MultiBox", 'F')
        spawnEntity(new Frog(Point3Double(14.5, 10.5, 2), Point3Double(y = 0.05)))
        spawnEntity(new Frog(Point3Double(13.5, 10.5, 2), Point3Double(y = 0.05)))
        spawnEntity(new Frog(Point3Double(12.5, 10.5, 2), Point3Double(y = 0.05)))
    }
}
