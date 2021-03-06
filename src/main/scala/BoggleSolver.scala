package esebesta.BoggleSolverScala

/**
 * Immutable class instance that can solve a boggle board, given a dictionary
 * Also note that BoggleSolver does not modify any input values, making it purely functional
 *
 * @param board a boggle board
 * @param dict a sequence of lowercase words, passed as a an iterator to allow lazy evaluation (load) of large data sets
 * @param minWordLength the minimum length a word in the dict list must be to be considered a valid word
 */
class BoggleSolver(val board: Board, val dict: Iterator[String], val minWordLength: Int = 3)
{
  /**
   * Find the words that are on the Boggle board, given the rule set
   * @return the found words
   */
  def solve = dict.filter(isOnBoard).toList

  private def isOnBoard(word: String): Boolean = {
    //if word < 3 letters, not valid
    if (word.length < minWordLength) false
    else {
      //test for 'q' not followed by 'u', which is not valid
      if (word.contains('q') && ! word.contains("qu")) false
      else isOnBoard(word, 0, Nil)
    }
  }

  private def isOnBoard(word: String, currentLetterIndex: Int, traversedCells: List[(Int, Int)]): Boolean = {
    if (currentLetterIndex >= word.length) true
    else {
      //walk the whole board when starting, otherwise work from the current location when recursing
      val rangeX = if (traversedCells == Nil) (0 until board.width) else (traversedCells.head._1 - 1 to traversedCells.head._1 + 1)
      val rangeY = if (traversedCells == Nil) (0 until board.height) else (traversedCells.head._2 - 1 to traversedCells.head._2 + 1)

      var wordFound = false
      val currentLetter = word(currentLetterIndex)
      for (i <- rangeX if !wordFound) {
        for (j <- rangeY if !wordFound) {
          if (board.has(i, j) && board.get(i, j) == currentLetter && !traversedCells.contains((i, j))) {
            val advanceAmount = if (currentLetter == 'q') 2 else 1
            if (isOnBoard(word, currentLetterIndex + advanceAmount, (i, j) :: traversedCells)) wordFound = true
          }
        }
      }
      wordFound
    }
  }
}
