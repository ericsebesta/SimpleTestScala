/**
 * An immutable class to store a series of letters
 * Board shouldn't care about the fact that boggle requires 4x4
 */
class Board(val letters: Array[Array[Char]])
{
  private val _height = letters.length
  private val _width = letters(0).length

  def height = _height
  def width = _width
  def get(i: Int, j: Int) = letters(i)(j)
  def has(i: Int, j: Int) = (i >= 0) && (i < _height) && (j >= 0) && (j < _width)
}
