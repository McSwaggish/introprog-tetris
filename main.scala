package tetris

object main {
    def main(args: Array[String]): Unit = {
        val game = new Game()

        game.initGame()
        game.startGame()
    }
}