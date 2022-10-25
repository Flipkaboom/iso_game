package isoGame

import scala.collection.mutable.ArrayBuffer

class Array3[T](
                   var data: ArrayBuffer[ArrayBuffer[ArrayBuffer[T]]]
               ){
    def apply(p: Point3): T = {
        data(p.x)(p.y)(p.z)
    }

    def update(p: Point3, newVal: T): Unit = {
        data(p.x)(p.y)(p.z) = newVal
    }

}

object Array3{
    def apply[T](xSize: Int, ySize: Int, zSize: Int, initialVal: T): Array3[T] = {
        val newData = ArrayBuffer[ArrayBuffer[ArrayBuffer[T]]]()
        for(x <- 0 to xSize){
            newData.append(ArrayBuffer[ArrayBuffer[T]]())
            for(y <- 0 to ySize){
                newData(x).append(ArrayBuffer[T]())
                for(z <- 0 to zSize){
                    newData(x)(y).append(initialVal)
                }
            }
        }
        new Array3[T](newData)
    }
}
