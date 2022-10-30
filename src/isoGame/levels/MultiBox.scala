package isoGame.levels
import isoGame.blocks.Transporter
import isoGame.{Point3, Point3Double}

class MultiBox extends Level {
    var name: String = "MultiBox"
    val chunkSize: Point3 = Point3(16,16,16)
    val loadFromFile: Boolean = true
    override val playerSpawn: Point3Double = Point3Double(5,5,5)

    def buildLevel(): Unit = {
        //in
        terrain(Point3(7,15,1)) = new Transporter("Wires1", 'E')
        terrain(Point3(8,15,1)) = new Transporter("Wires1", 'E')
        //out
        terrain(Point3(8,1,1)) = new Transporter("Frogmap", 'F')
        terrain(Point3(9,1,1)) = new Transporter("Frogmap", 'F')
    }
}
