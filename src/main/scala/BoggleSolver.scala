/**
 * Immutable class instance that can solve a boggle board, given a dictionary
 * Also note that BoggleSolver does not modify any input values, making it purely functional
 *
 * @param board a boggle board
 * @param dict a sequence of lowercase words
 */
class BoggleSolver(val board: Board, val dict: Seq[String], val minWordLength: Int = 3)
{
  /**
   * Find the words that are on the Boggle board, given the rule set
   * @return the found words
   */
  def solve = dict.filter(isOnBoard)

  private def isOnBoard(word: String): Boolean = {
    if (word.length < minWordLength) false
    else isOnBoard(word, Nil)
  }

  private def isOnBoard(word: String, traversedCells: List[(Int, Int)]): Boolean = {
    var wordFound = false
    if (word.length == 0) true
    else
    {
      val currentLetter = word(0)
      if (traversedCells == Nil) {
        for (i <- 0 until board.height if !wordFound) {
          for (j <- 0 until board.width if !wordFound) {
            if (board.get(i, j) == currentLetter)
            {
              val dropAmount = if (currentLetter == 'q' && word(1) == 'u') 2 else 1
              if (isOnBoard(word.drop(dropAmount), List((i, j)))) wordFound = true
            }
          }
        }
      }
      else {
        val currentLocation = traversedCells.head
        for (i <- currentLocation._1 - 1 to currentLocation._1 + 1 if !wordFound) {
          for (j <- currentLocation._2 - 1 to currentLocation._2 + 1 if !wordFound) {
            if (board.has(i, j) && currentLetter == board.get(i, j) && !hasTraversed(traversedCells, (i, j))) {
              val dropAmount = if (currentLetter == 'q' && word(1) == 'u') 2 else 1
              if (isOnBoard(word.drop(dropAmount), (i, j) :: traversedCells)) wordFound = true
            }
          }
        }
      }
      wordFound
    }
  }

  private def hasTraversed(traversedCells: List[(Int, Int)], cell: (Int, Int)) = {
    traversedCells.contains(cell)
  }
}
