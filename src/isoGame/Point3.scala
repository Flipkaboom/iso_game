package isoGame

case class Point3(x: Int, y: Int, z: Int){
    def +(rhs: Point3): Point3 = {
        Point3(x + rhs.x, y + rhs.y, z + rhs.z)
    }
    
    def -(rhs: Point3): Point3 = {
        Point3(x - rhs.x, y - rhs.y, z - rhs.z)
    }
}
