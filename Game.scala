package tetris

object Game {
    object Variables {
        var gameOff:          Boolean = false //for game loop
        var increaseGravity:  Boolean = false
        var rotateThisFrame:  Boolean = false //to relocate Tetromino.rotate() from handleEvents() to updateFalling()
        var isFilled:         Boolean = true
        var timer:            Int     = 0     //to make tetromino not fall every frame
        var delay:            Int     = 300   //delay between falls in ms
        var landDisplayState: Int     = 1
        var points:           Int     = 0
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
            case 0 => Color.black
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

    //game matrix (no shit)
    val matrix: Array[Array[Int]] = Array.ofDim[Int](21, 12)
}

class Game() {
    import Game._
    import Variables._
    import Tetromino._
    val window = new RenderWindow()

    //initializing matrix
    for(i <- 0 until 20;
        j <- 0 until 12)
        matrix(i)(j) = 0

    //floor beneath screen to check for collisions in empty matrix
    for(i <- 0 until 12)
        matrix(20)(i) = 12

    //side pillars
    for(i <- 0 until 20)
        matrix(i)(0)   = 8
        matrix(i)(11)  = 8

    def drawLanded(): Unit = {
        for(i <- 0 until matrix.length;
            j <- 0 until matrix(0).length) {
                if(matrix(i)(j) != 0) //if certain block is occupied, draw shit ????? looking back, what????
                    window.draw(j, i, drawTheRightColour(matrix(i)(j)))
                else if(matrix(i)(j) == 0)
                    window.clear(j, i)
            }
    }

    //erase entire tetromino
    def eraseFalling(): Unit = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if(currTet(i)(j) != 0)
                    window.clear(j + topLeft(0), i + topLeft(1))
            }
    }

    //draw entire tetromino
    def drawFalling(): Unit = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if(currTet(i)(j) != 0)
                    window.draw(j + nextPos(0), i + nextPos(1), drawTheRightColour(currTet(i)(j)))
            }
    }

    //checks for collision under falling tetromino
    def canFall(): Boolean = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if (currTet(i)(j) != 0)
                    if (matrix(i + nextPos._2)(j + nextPos._1) != 0) //is tetrominoes nextPos occupied
                        return false
                }
        true
    }

    //checks for collision on left side of tetromino
    def canMoveLeft(): Boolean = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if (currTet(i)(j) != 0)
                    if (matrix(i + topLeft(1))(j + topLeft(0) - 1) != 0)
                        return false
                }
        true
    }

    //HAHAHAHAHAHAHAHAHAHAHAHAH
    def canMoveRight(): Boolean = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if (currTet(i)(j) != 0)
                    if (matrix(i + topLeft(1))(j + topLeft(0) + 1) != 0)
                        return false
                }
        true
    }

    //handle keyboard inputs and fucking with the window
    def handleEvents(): Unit = {
        var e = window.nextEvent()
        increaseGravity = false
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

    //add landed tetromino to all the shit at the bottom ukno
    def addToLanded(): Unit = {
        for(i <- 0 until currTet.length;
            j <- 0 until currTet(0).length) {
                if(currTet(i)(j) != 0)
                    matrix(i + topLeft(1))(j + topLeft(0)) = currTet(i)(j)
                }
    }

    def updateText(): Unit = {
        for(i <- 2 until 4;
            j <- 13 until 20)
            window.clear(j, i)
        window.typeWriter(s"Points: $points", 13, 2, java.awt.Color(0xff, 0xff, 0xff), 30, 0, 0)
    }

    def checkLinesAndClear(): Unit = {
        var lineBreakCounter = 0
        for(i <- 0 until matrix.length - 1) {
            isFilled = true
            for(j <- 1 until matrix(0).length - 1){
                //checks every line for empty space, if there is its not filled
                if(matrix(i)(j) == 0)
                    isFilled = false
            }
            //if line is filled, give points and move every line above down one step
            if(isFilled)
                lineBreakCounter += 1
                for(y <- 0 until i;
                    x <- 1 until 11)
                    matrix(i - y)(x) = matrix(i - 1 - y)(x)
        }
        //add points
        if (lineBreakCounter == 1)
            points += 400
        else if (lineBreakCounter == 2)
            points += 800
        else if (lineBreakCounter == 3)
            points += 1600
        else if (lineBreakCounter == 4)
            points += 3200
    }

    def checkForLoss(): Boolean = {
        for(i <- 1 until matrix(0).length - 1)
            if (matrix(1)(i) != 0) //if there is shit at y = 1
                println("YOU LOSE")
                return true
        false
    }

    //shit to do when stuff lands
    def updateLanded(): Unit = {
        addToLanded()
        checkLinesAndClear()
    }

    def nextPieceDisplay(): Unit = {
        window.typeWriter("Next: ", 13, 6, java.awt.Color(0xff, 0xff, 0xff), 30, 0, -15)
        
        for(i <- 7 until 11;
            j <- 13 until 17)
            window.clear(j, i)
        
        for(i <- 0 until nextTet.length;
            j <- 0 until nextTet(0).length)
            if(nextTet(i)(j) != 0)
                window.draw(j + 13, i + 7, drawTheRightColour(nextTet(i)(j)))
    }

    def gameOverSequence(): Unit = {
        for(i <- 0 until 20)
            for(j <- 1 until 11)
                window.draw(j, i, java.awt.Color(0x44, 0x44, 0x44))
            Thread.sleep(50)
    }

    def updateFalling(canThisShitFall: Boolean): Unit = {
        if (canThisShitFall) //if tetromino can fall
            eraseFalling()
            if(rotateThisFrame)
                rotateThisFrame = false
                rotate()
            drawFalling()
            updatePosition()
        else
            updateLanded()
            respawn()
            nextPieceDisplay()
            landDisplayState = 1 //tells display() to draw landed blocks
            if (checkForLoss())
                landDisplayState = 2
                gameOff = true
                println(Console.RED + "YOUR LOOSER !")
        
        stopMovinglmfao() //makes the tetromino stop moving left/right if moving HAHAHHAHAHAH
    }

    def display(state: Int): Unit = {
        if (state == 1)
            drawLanded()
            landDisplayState = 0
        if(state == 2)
            gameOverSequence()
    }

    def willFall(): Boolean = {
        if(timer >= (if(increaseGravity) {points += 5; 20} else delay))
            timer = 0
            true
        else
            false
    }

    def checkIfThisShitWIllFall(): Unit = { //exists only to leep gameLoop() looking nice
        if (willFall())
            fallNext(true)
        else
            fallNext(false)
    }

    def gameLoop(): Unit = {
        updateText() //initialize text
        nextPieceDisplay()
        while(!gameOff) {
            val t0: Long = System.currentTimeMillis

            handleEvents()
            checkIfThisShitWIllFall()
            updateFalling(canFall())
            display(landDisplayState)
            updateText()

            val deltaTime: Int = (System.currentTimeMillis - t0).toInt
            timer += ((80 - deltaTime) max 0) //add deltaTime to gravity timer
            Thread.sleep((80 - deltaTime) max 0) //make every frame longer as not to draw shit 100000 times / sec
        }
    }


    def initGame(): Unit =
        for(i <- 2 until 8;
            j <- 2 until 18){
                if(i == 2 || (i == 7 && (j <= 7 || j >= 12)) || j == 2 || j == 17 )
                    window.drawFull(j, i, java.awt.Color(0x1c, 0x2b, 0x5b))
                else
                    window.drawFull(j, i, java.awt.Color(0x0e, 0x16, 0x21))
            }
        for(i <- 8 until 13;
            j <- 7 until 13){
                if(j == 7 || j == 12 || i == 12)
                    window.drawFull(j, i, java.awt.Color(0x1c, 0x2b, 0x5b))
                else
                    window.drawFull(j, i, java.awt.Color(0x0e, 0x16, 0x21))
            }

        window.typeWriter("T",  3, 2, Color.red,    175,  3, 10)
        window.typeWriter("E",  5, 2, Color.orange, 175, 18, 10)
        window.typeWriter("T",  7, 2, Color.yellow, 175, 33, 10)
        window.typeWriter("R",  9, 2, Color.green,  175, 48, 10)
        window.typeWriter("I", 11, 2, Color.cyan,   175, 63, 10)
        window.typeWriter("S", 13, 2, Color.purple, 175, 78, 10)
        Thread.sleep(3000)
        for(i <- 0 until 20;
            j <- 0 until 20)
            window.clear(j, i)
    
    def startGame(): Unit =
        gameLoop()
}