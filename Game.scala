package tetris

object Game {
    object Variables {
        var gameOff:         Boolean = false
        var increaseGravity: Boolean = false
        var rotateThisFrame: Boolean = false
        var timer:           Int     = 0
        var delay:           Int     = 300
    }

    object Color {
        import java.awt.Color as JavaMegaGay
        val cerise = JavaMegaGay(0xe9, 0x38, 0x67)
        val black  = JavaMegaGay(0x00, 0x00, 0x00) //erasing tetrominoes
        val green  = JavaMegaGay(0x72, 0xcb, 0x3b) //1
        val blue   = JavaMegaGay(0x03, 0x41, 0xae) //2
        val yellow = JavaMegaGay(0xff, 0xd5, 0x00) //3
        val orange = JavaMegaGay(0xff, 0x97, 0x1c) //4
        val red    = JavaMegaGay(0xff, 0x31, 0x13) //5
        val cyan   = JavaMegaGay(0x00, 0xff, 0xff) //6
        val purple = JavaMegaGay(0x80, 0x00, 0x80) //7
        val pillar = JavaMegaGay(0x33, 0xaa, 0xff) //8
    }

    def drawTheRightColour(colourIndex: Int): java.awt.Color = {
        colourIndex match {
            case 1 => Color.green
            case 2 => Color.blue
            case 3 => Color.yellow
            case 4 => Color.orange
            case 5 => Color.red
            case 6 => Color.cyan
            case 7 => Color.purple
            case 8 => Color.pillar
            case _ => Color.cerise
        }
    }

    val matrix: Array[Array[Int]] = Array.ofDim[Int](21, 12)
}

class Game() {
    import Game._
    import Variables._
    import Tetromino._
    val window = new RenderWindow()

    //init(?) matrix
    for(i <- 0 until 20;
        j <- 0 until 12) {
        matrix(i)(j) = 0
    }

    for(i <- 0 until 12) {
        matrix(20)(i) = 12
        //matrix(i)(i) = 12
        //matrix(17)(i) = 5
        //matrix(15)(i) = 7
    }

    //SIDES - CHANGE COLOUR CERISE IS NOT THE BEST FIT FFS
    for(i <- 0 until 20)
        matrix(i)(0)  = 8
        matrix(i)(11) = 8

    def drawLanded(): Unit = {
        for(i <- 0 until matrix.length;
            j <- 0 until matrix(0).length) {
                if(matrix(i)(j) != 0) //if certain block is occupied, draw shit
                    window.draw(j, i, drawTheRightColour(matrix(i)(j)))
            }
    }

    def eraseFalling(): Unit = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if(currTet(i)(j) != 0)
                    window.clear(j + topLeft(0), i + topLeft(1))
            }
    }

    def drawFalling(): Unit = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if(currTet(i)(j) != 0)
                    window.draw(j + nextPos(0), i + nextPos(1), drawTheRightColour(currTet(i)(j)))
            }
    }

    def canFall(): Boolean = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if (currTet(i)(j) != 0)
                    if (matrix(i + nextPos._2)(j + nextPos._1) != 0) //is tetrominoes nextPos occupied
                        return false
                }
        true
    }

    def canMoveLeft(): Boolean = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if (currTet(i)(j) != 0)
                    if (matrix(i + topLeft(1))(j + topLeft(0) - 1) != 0)
                        return false
                }
        true
    }
    def canMoveRight(): Boolean = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if (currTet(i)(j) != 0)
                    if (matrix(i + topLeft(1))(j + topLeft(0) + 1) != 0)
                        return false
                }
        true
    }

    def handleEvents(): Unit = {
        var e = window.nextEvent()
        while (e != RenderWindow.Event.Undefined) {
            e match
                case RenderWindow.Event.KeyPressed(key) =>
                    if(canMoveLeft() && key == "Left")
                        moveLeft()
                    if(canMoveRight() && key == "Right")
                        moveRight()
                    if(key == "Up")
                        rotateThisFrame = true
                    if(key == "Down")
                        increaseGravity = true
                    else
                        increaseGravity = false
                case RenderWindow.Event.WindowClosed =>
                    println(Console.RED + "WHY U NO WANNA PLAY BITCHASS")
                    gameOff = true
            e = window.nextEvent()
        }
    }

    def addToLanded(): Unit = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if(currTet(i)(j) != 0)
                    matrix(i + topLeft(1))(j + topLeft(0)) = currTet(i)(j)
                }
    }

    def updateLanded(): Unit = {
        addToLanded()
        //check lines
    }

    def updateFalling(canThisShitFall: Boolean): Unit = {
        if (canThisShitFall)
            eraseFalling()
            if(rotateThisFrame)
                rotateThisFrame = false
                rotate()
            drawFalling()
            updatePosition()
        else
            updateLanded()
            respawn()
    }

    def display(state: Int): Unit = {
        if (state == 1)
            drawLanded()
    }

    def willFall(): Boolean = {
        if(timer >= (if(increaseGravity) 50 else delay))
            timer = 0
            true
        else
            false
    }

    def gameLoop(): Unit = {
        while(!gameOff) {
            val t0: Long = System.currentTimeMillis

            handleEvents()
            
            if (willFall())
                fallNext(true)
            else
                fallNext(false)

            updateFalling(canFall())
            stopMovinglmfao()
            display(1)

            val deltaTime: Int = (System.currentTimeMillis - t0).toInt
            timer += ((80 - deltaTime) max 0)
            Thread.sleep((80 - deltaTime) max 0)
        }
    }


    def initGame(): Unit =
        println(Console.GREEN + "initializing game !")
    
    def startGame(): Unit =
        gameLoop()
}







