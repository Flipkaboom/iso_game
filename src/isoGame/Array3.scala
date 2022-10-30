package isoGame

import scala.collection.mutable.ArrayBuffer

class Array3[T](
                   var data: ArrayBuffer[ArrayBuffer[ArrayBuffer[T]]]
               ) extends Serializable {
    def apply(x: Int, y: Int, z: Int): T = {
        data(x)(y)(z)
    }

    def update(x: Int, y: Int, z: Int, newVal: T): Unit = {
        data(x)(y)(z) = newVal
    }

    def apply(p: Point3): T = {
        data(p.x)(p.y)(p.z)
    }

    def update(p: Point3, newVal: T): Unit = {
        data(p.x)(p.y)(p.z) = newVal
    }

    /** Fill a rectangular cuboid with the two corners p1 and p2 */
    def fillRect(p1: Point3, p2: Point3, fillVal: T): Unit ={
        val pMin: Point3 = p1.combine(p2, _ min _)
        val pmax: Point3 = p1.combine(p2, _ max _)

        for(p: Point3 <- pMin until pmax + 1){
            this(p) = fillVal
        }
    }

    /** Fill a rectangular cuboid with the two corners p1 and p2
      * @param id used to create instance of T using f
      * @param f factory method to make instances of T
      */
    def fillRect[A](p1: Point3, p2: Point3, id: A, f: A => T): Unit = {
        val pMin: Point3 = p1.combine(p2, _ min _)
        val pmax: Point3 = p1.combine(p2, _ max _)

        for (p: Point3 <- pMin until pmax + 1) {
            this (p) = f(id)
        }
    }

}

object Array3{
    def apply[T](size: Point3, initialVal: T): Array3[T] = {
        val newData = ArrayBuffer[ArrayBuffer[ArrayBuffer[T]]]()
        for(x <- 0 to size.x){
            newData.append(ArrayBuffer[ArrayBuffer[T]]())
            for(y <- 0 to size.y){
                newData(x).append(ArrayBuffer[T]())
                for(z <- 0 to size.z){
                    newData(x)(y).append(initialVal)
                }
            }
        }
        new Array3[T](newData)
    }
}
