package isoGame

import processing.core.PApplet

class IsoEditor extends IsoGame {

}

object IsoEditor{
    def main(args: Array[String]): Unit = {
        if(args.length == 4) {
            IsoGame.editorSize = Point3(args(1).toInt, args(2).toInt, args(3).toInt)
            IsoGame.newLevelName = args(0)
        }
        else if(args.length == 1) {
            IsoGame.startingLevel = args(0)
        }

        IsoGame.editorMode = true

        println("Editing level: " + IsoGame.newLevelName)

        PApplet.main("isoGame.IsoEditor")
    }
}