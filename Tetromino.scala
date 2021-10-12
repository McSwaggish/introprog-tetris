package tetris

object Tetromino {
    //rotation 0-3
    var rotation: Int = 0
    
    //offset for top left coord of tetromino array
    val spawnOffset: Vector[Int] = Vector(3, 0)
    val topLeft:     Array[Int]  = spawnOffset.toArray

    var tetrominoIndex:     Int = java.util.Random().nextInt(5) //cant start with S or Z
    var nextTetrominoIndex: Int = java.util.Random().nextInt(7)

    //potential next positions, if conditions are met
    var potentialOffsetX: Int = 0
    var potentialOffsetY: Int = 0


    def O_tetromino(rot: Int): Array[Array[Int]] = {
            Array( Array(0, 3, 3, 0),
                   Array(0, 3, 3, 0),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
    }

    def T_tetromino(rot: Int): Array[Array[Int]] = {
        if (rot == 0)
            Array( Array(0, 0, 7, 0),
                   Array(0, 7, 7, 7),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 1)
            Array( Array(0, 0, 7, 0),
                   Array(0, 0, 7, 7),
                   Array(0, 0, 7, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 2)
            Array( Array(0, 0, 0, 0),
                   Array(0, 7, 7, 7),
                   Array(0, 0, 7, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 3)
            Array( Array(0, 0, 7, 0),
                   Array(0, 7, 7, 0),
                   Array(0, 0, 7, 0),
                   Array(0, 0, 0, 0) )
        else
            Array( Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4) )
    }

    def I_tetromino(rot: Int): Array[Array[Int]] = {
        if (rot == 0)
            Array( Array(0, 0, 6, 0),
                   Array(0, 0, 6, 0),
                   Array(0, 0, 6, 0),
                   Array(0, 0, 6, 0) )
        else if (rot == 1)
            Array( Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0),
                   Array(6, 6, 6, 6),
                   Array(0, 0, 0, 0) )
        else if (rot == 2)
            Array( Array(0, 6, 0, 0),
                   Array(0, 6, 0, 0),
                   Array(0, 6, 0, 0),
                   Array(0, 6, 0, 0) )
        else if (rot == 3)
            Array( Array(0, 0, 0, 0),
                   Array(6, 6, 6, 6),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
        else
            Array( Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4) )
    }

    def J_tetromino(rot: Int): Array[Array[Int]] = {
        if (rot == 0)
            Array( Array(0, 2, 0, 0),
                   Array(0, 2, 2, 2),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 1)
            Array( Array(0, 0, 2, 2),
                   Array(0, 0, 2, 0),
                   Array(0, 0, 2, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 2)
            Array( Array(0, 0, 0, 0),
                   Array(0, 2, 2, 2),
                   Array(0, 0, 0, 2),
                   Array(0, 0, 0, 0) )
        else if (rot == 3)
            Array( Array(0, 0, 0, 2),
                   Array(0, 0, 0, 2),
                   Array(0, 0, 2, 2),
                   Array(0, 0, 0, 0) )
        else
            Array( Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4) )
    }

    def L_tetromino(rot: Int): Array[Array[Int]] = {
        if (rot == 0)
            Array( Array(0, 0, 0, 4),
                   Array(0, 4, 4, 4),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 1)
            Array( Array(0, 0, 4, 0),
                   Array(0, 0, 4, 0),
                   Array(0, 0, 4, 4),
                   Array(0, 0, 0, 0) )
        else if (rot == 2)
            Array( Array(0, 0, 0, 0),
                   Array(0, 4, 4, 4),
                   Array(0, 4, 0, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 3)
            Array( Array(0, 4, 4, 0),
                   Array(0, 0, 4, 0),
                   Array(0, 0, 4, 0),
                   Array(0, 0, 0, 0) )
        else
            Array( Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4) )
    }

    def S_tetromino(rot: Int): Array[Array[Int]] = {
        if (rot == 0)
            Array( Array(0, 0, 1, 1),
                   Array(0, 1, 1, 0),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 1)
            Array( Array(0, 0, 1, 0),
                   Array(0, 0, 1, 1),
                   Array(0, 0, 0, 1),
                   Array(0, 0, 0, 0) )
        else if (rot == 2)
            Array( Array(0, 0, 0, 0),
                   Array(0, 0, 1, 1),
                   Array(0, 1, 1, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 3)
            Array( Array(0, 1, 0, 0),
                   Array(0, 1, 1, 0),
                   Array(0, 0, 1, 0),
                   Array(0, 0, 0, 0) )
        else
            Array( Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4) )
    }

    def Z_tetromino(rot: Int): Array[Array[Int]] = {
        if (rot == 0)
            Array( Array(0, 5, 5, 0),
                   Array(0, 0, 5, 5),
                   Array(0, 0, 0, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 1)
            Array( Array(0, 0, 0, 5),
                   Array(0, 0, 5, 5),
                   Array(0, 0, 5, 0),
                   Array(0, 0, 0, 0) )
        else if (rot == 2)
            Array( Array(0, 0, 0, 0),
                   Array(0, 5, 5, 0),
                   Array(0, 0, 5, 5),
                   Array(0, 0, 0, 0) )
        else if (rot == 3)
            Array( Array(0, 0, 5, 0),
                   Array(0, 5, 5, 0),
                   Array(0, 5, 0, 0),
                   Array(0, 0, 0, 0) )
        else
            Array( Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4),
                   Array(1, 2, 3, 4) )
    }

    //current tetromino falling in game
    def currTet: Array[Array[Int]] = {
        tetrominoIndex match
            case 0 => O_tetromino(rotation)
            case 1 => T_tetromino(rotation)
            case 2 => I_tetromino(rotation)
            case 3 => J_tetromino(rotation)
            case 4 => L_tetromino(rotation)
            case 5 => S_tetromino(rotation)
            case 6 => Z_tetromino(rotation)
    }

    def nextTet: Array[Array[Int]] = {
        nextTetrominoIndex match
            case 0 => O_tetromino(0)
            case 1 => T_tetromino(0)
            case 2 => I_tetromino(0)
            case 3 => J_tetromino(0)
            case 4 => L_tetromino(0)
            case 5 => S_tetromino(0)
            case 6 => Z_tetromino(0)
    }

    //tells tetromino to fall next frame
    def fallNext(yes: Boolean): Unit =
        if(yes)
            potentialOffsetY = 1
        else
            potentialOffsetY = 0
    
    //move left
    def moveLeft(): Unit = {
        potentialOffsetX = -1
    }

    //HAHAHAHAHAHAHAH
    def moveRight(): Unit = {
        potentialOffsetX = 1
    }

    def rotate(): Unit = {
        if(rotation < 3)
            rotation += 1
        else
            rotation = 0
    }

    //VERY GOOD PROFESSIONAL CODING PRACTICE
    def stopMovinglmfao(): Unit =
        potentialOffsetX = 0

    //tetrominos next pose, if conditions are met
    def nextPos: (Int, Int) =
        (topLeft(0) + potentialOffsetX,
         topLeft(1) + potentialOffsetY)
    
    def updatePosition(): Unit =
        topLeft(0) = nextPos._1
        topLeft(1) = nextPos._2
    
    def respawn(): Unit =
        rotation = 0
        tetrominoIndex = nextTetrominoIndex
        nextTetrominoIndex = java.util.Random().nextInt(7)
        topLeft(0) = spawnOffset(0)
        topLeft(1) = spawnOffset(1)

}