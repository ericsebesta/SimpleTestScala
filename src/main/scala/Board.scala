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
  def makeBoard(boardLines: Seq[String]) =
  {
    //detect board size, then create a board
    val boardHeight = boardLines.length
    if (boardHeight == 0) throw new Exception("no lines found in board file")
    val lineLength = boardLines(0).length
    //check that all line lengths are equal
    val otherLengthLines = boardLines.filter(_.length != lineLength)
    if (otherLengthLines.length != 0) throw new Exception("non-rectangular board detected")

    val boardWidth = (lineLength + 1) / 2 //each character except for last is followed by a single space
    val boardArray: Array[Array[Char]] = Array.ofDim[Char](boardHeight, boardWidth)
    var lineNumber = 0
    for (line <- boardLines) {
      var charCounter = 0
      for (i <- 0 until boardWidth) {
        boardArray(lineNumber)(i) = line(charCounter)
        charCounter += 2
      }
      lineNumber += 1
    }
    new Board(boardArray)
  }
}
