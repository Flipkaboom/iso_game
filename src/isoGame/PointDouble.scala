package isoGame

case class PointDouble(x: Double, y: Double) {
    def +(rhs: PointDouble): PointDouble = {
        PointDouble(x + rhs.x, y + rhs.y)
    }

    def -(rhs: PointDouble): PointDouble = {
        PointDouble(x - rhs.x, y - rhs.y)
    }
}
