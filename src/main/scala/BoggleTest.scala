import java.io.{File, PrintWriter}
import scala.io.Source

//TODO write unit tests that pass in data and test it

object BoggleTest extends App
{
  if (args.length != 3)
  {
    println("Usage: BoggleTest <dictionary_filename> <board_filename> <output_filename>")
  }
  else
  {
    //try load board file
    try
    {
      val boardSource = Source.fromFile(args(1))
      //try load dictionary file
      try
      {
        val dictSource = Source.fromFile(args(0))

        val boardLines = boardSource.getLines().toList.map(_.toLowerCase)
        val dictLines = dictSource.getLines().toList.map(_.toLowerCase)
        val resultList = new BoggleTest(dictLines, boardLines).run

        //write results
        val outputFileWriter = new PrintWriter(new File(args(2)))
        for (word <- resultList) {outputFileWriter.write(word + System.lineSeparator())}
        outputFileWriter.flush()
      }
      catch {case e: java.io.FileNotFoundException => println("ERROR: Cannot open dict file.")}
    }
    catch {case e: java.io.FileNotFoundException => println("ERROR: Cannot open board file.")}
  }
}

class BoggleTest(val words: Seq[String], val boardLines: Seq[String])
{
  def run =
  {
    //detect board size, then create a board
    val boardHeight = boardLines.length
    if (boardHeight == 0) throw new Exception("no lines found in board file")
    val lineLength = boardLines(0).length

    {
      //check that all line lengths are equal
      val otherLengthLines = boardLines.filter(_.length != lineLength)
      if (otherLengthLines.length != 0) throw new Exception("non-rectangular board detected")
    }

    val boardWidth = (lineLength + 1) / 2 //each character except for last is followed by a single space
    val boardArray = Array.ofDim[Char](boardHeight, boardWidth)
    var lineNumber = 0
    for (line <- boardLines)
    {
      var charCounter = 0
      for (i <- 0 until boardWidth)
      {
        boardArray(lineNumber)(i) = line(charCounter)
        charCounter += 2
      }
      lineNumber += 1
    }
    val board = new Board(boardArray)

    //test each word against board
    //lowercase all words, just to be safe
    //encapsulate solve in a class instance to ease transition to parallelized solvers
    new BoggleSolver(board, words).solve
  }
}
