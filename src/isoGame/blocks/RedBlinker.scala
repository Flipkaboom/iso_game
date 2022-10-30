package isoGame.blocks
import isoGame.Renderer
import processing.core.PImage

class RedBlinker extends BlueBlinker{
    override val name = "RedBlinker"

    @transient override lazy val offTexture: PImage = Renderer.textures("RedBlinkerOff")

    counter = 90
}
