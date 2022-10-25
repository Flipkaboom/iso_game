package isoGame

//TODO: abstract because it won't work on it's own?
class Renderer extends BaseEngine{

    override def settings(): Unit = {
        size(width, height)
    }

    override def setup(): Unit = {
        surface.setTitle("Iso")
        surface.setResizable(true)
    }


}

object Renderer {

}