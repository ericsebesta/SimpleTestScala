package esebesta.BoggleSolverScala

/**
 * An immutable class to store a series of letters
 * Board shouldn't care about the fact that boggle requires 4x4
 */
class Board(val letters: Array[Array[Char]])
{
  if (letters.length == 0 || letters(0).length == 0) throw new Exception("Board must have at least 1 letter")
  private val _height = letters.length
  private val _width = letters(0).length

  def height = _height
  def width = _width
  def get(i: Int, j: Int) = letters(i)(j)
  def has(i: Int, j: Int) = (i >= 0) && (i < _height) && (j >= 0) && (j < _width)
}

object Board
{
  /** Take lines of text and turn it into a Board
    *
    * @param boardLines the lines of lowercase ascii text
    * @return a Board
    *
    *         Whitespace such as spaces and tabs are removed. The lines should form a "rectangular" board of any size.
    *         There must be at least 1 line of text.
    *         Throw if there are no lines of text, or if we cannot find a rectangular set of characters.
    */
  def makeBoard(boardLines: Seq[String]) =
  {
    //detect board size, then create a board
    val boardHeight = boardLines.length
    if (boardHeight == 0) throw new Exception("no lines found in board file")
    val boardArray = boardLines.map(parseLine).toArray
    val lineLength = boardArray.length
    //check that all line lengths are equal, if not the board cannot be rectangular as requried
    val otherLengthLines = boardArray.filter(_.length != lineLength)
    if (otherLengthLines.length != 0) throw new Exception("non-rectangular board detected")
    new Board(boardArray)
  }

  /**
   * Pull only valid letters from a string of text.
   * @param boardLine The line of text
   * @return The valid letters
   */
  private def parseLine(boardLine: String): Array[Char] = {
    val validLetters = for (char <- boardLine if char.isLetter) yield char
    validLetters.toArray
  }
}
