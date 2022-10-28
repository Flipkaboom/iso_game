package isoGame

case class Point3(x: Int, y: Int, z: Int){
//    def +(rhs: Point3): Point3 = {
//        Point3(x + rhs.x, y + rhs.y, z + rhs.z)
//    }
//    
//    def -(rhs: Point3): Point3 = {
//        Point3(x - rhs.x, y - rhs.y, z - rhs.z)
//    }
    
    def +(rhs: Int): Point3 = {
        Point3(x + rhs, y + rhs, z + rhs)
    }

    def -(rhs: Int): Point3 = {
        Point3(x - rhs, y - rhs, z - rhs)
    }

    //TODO: make more efficient if needed? Iterator?
    def until(p: Point3): IndexedSeq[Point3] = {
        for (xi <- x until p.x;
             yi <- y until p.y;
             zi <- z until p.z) yield {
            Point3(xi, yi, zi)
        }
    }

    def combine(other: Point3, f: (Int, Int) => Int): Point3 = {
        Point3(f(x, other.x), f(y, other.y), f(z, other.z))
    }

    def diagonalRow(): Int = {
        x + y
    }
}

object Point3{
    def apply(value: Int): Point3 = {
        Point3(value, value, value)
    }
}
