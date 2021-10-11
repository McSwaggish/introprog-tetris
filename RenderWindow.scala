package tetris

import javax.swing.event.DocumentEvent.EventType

class RenderWindow(
    val windowSize: (Int, Int) = (12, 20), //SHOULD BE (20, 20)
    val blockSize:  Int        = 45,
    val title:      String     = "Тетрис"
) {
    import introprog.PixelWindow

    val window = PixelWindow(windowSize._1 * blockSize, windowSize._2 * blockSize, title)
    window.setPosition(300, 0)


    //draw black without the frame
    def clear(x: Int, y: Int): Unit =
        window.fill(x * blockSize, y * blockSize, blockSize, blockSize, java.awt.Color(0x00, 0x00, 0x00))

    def draw(x: Int, y: Int, color: java.awt.Color): Unit =
        window.fill(x * blockSize, y * blockSize, blockSize, blockSize, java.awt.Color(0x22, 0x22, 0x22)) //frame
        window.fill(x * blockSize + 3, y * blockSize + 3, blockSize - 6, blockSize - 6, color) //colored block
    

    def nextEvent(): RenderWindow.Event.EventType = {
        import RenderWindow.Event._
        window.awaitEvent(10)
        window.lastEventType match
            case PixelWindow.Event.KeyPressed    => KeyPressed(window.lastKey)
            case PixelWindow.Event.WindowClosed  => WindowClosed
            case _                               => Undefined
    }


}

object RenderWindow {
    object Event:
        trait EventType
        case class  KeyPressed(key: String) extends EventType
        case object WindowClosed            extends EventType
        case object Undefined               extends EventType
}