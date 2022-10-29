package isoGame

case class Point3Double(x: Double = 0, y: Double = 0, z: Double = 0){
    def +(rhs: Point3Double): Point3Double = {
        Point3Double(x + rhs.x, y + rhs.y, z + rhs.z)
    }

    def -(rhs: Point3Double): Point3Double = {
        Point3Double(x - rhs.x, y - rhs.y, z - rhs.z)
    }

    def +(rhs: Double): Point3Double = {
        Point3Double(x + rhs, y + rhs, z + rhs)
    }

    def -(rhs: Double): Point3Double = {
        Point3Double(x - rhs, y - rhs, z - rhs)
    }

    def toPoint3: Point3 = {
        Point3(x.toInt, y.toInt, z.toInt)
    }

    def combine(other: Point3, f: (Double, Double) => Double): Point3Double = {
        Point3Double(f(x, other.x), f(y, other.y), f(z, other.z))
    }

    def diagonalRow: Double = {
        x + y
    }
}

object Point3Double{
    def apply(value: Double): Point3Double = {
        Point3Double(value, value, value)
    }
}
