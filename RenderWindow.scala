package tetris

import javax.swing.event.DocumentEvent.EventType

class RenderWindow(
    val windowSize: (Int, Int) = (20, 20), //SHOULD BE (20, 20)
    val blockSize:  Int        = 45,
    val title:      String     = "Тетрис"
) {
    import introprog.PixelWindow

    val window = PixelWindow(windowSize._1 * blockSize, windowSize._2 * blockSize, title)
    window.setPosition(300, 0)


    //draw black without the frame
    def clear2(x: Int, y: Int): Unit =
        window.fill(x * blockSize, y * blockSize, blockSize, blockSize, java.awt.Color(0xff, 0xff, 0xff))
    
    def clear(x: Int, y: Int): Unit =
        window.fill(x * blockSize, y * blockSize, blockSize, blockSize, java.awt.Color(0xd0, 0xd0, 0xd0))
        window.fill(x * blockSize + 1, y * blockSize + 1, blockSize - 2, blockSize - 2, java.awt.Color(0xff, 0xff, 0xff))

    def draw(x: Int, y: Int, color: java.awt.Color): Unit =
        window.fill(x * blockSize, y * blockSize, blockSize, blockSize, java.awt.Color(0x18, 0x13, 0x1a)) //frame
        window.fill(x * blockSize + 2, y * blockSize + 2, blockSize - 4, blockSize - 4, color) //colored block
    
    def drawFull(x: Int, y: Int, color: java.awt.Color): Unit =
        window.fill(x * blockSize, y * blockSize, blockSize, blockSize, color)
    
    def typeWriter(text:    String,
                   x:       Int,
                   y:       Int,
                   clr:     java.awt.Color,
                   fntSize: Int,
                   xOffset: Int,
                   yOffset: Int): Unit =
                   window.drawText(text, x * blockSize + xOffset, y * blockSize + yOffset, clr, fntSize)
    

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