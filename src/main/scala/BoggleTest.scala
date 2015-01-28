import java.io.{File, PrintWriter}
import scala.io.Source

//TODO get sample data, run it to ensure things work
//TODO refactor BoggleTest to take Sources and output the list
//TODO write unit tests that pass in data and test it

object BoggleTest extends App
{
  if (args.length != 3)
  {
    println("Usage: BoggleTest <dictionary_filename> <board_filename> <output_filename>")
  }
  else
  {
    new BoggleTest(args(0), args(1), args(2)).run
  }
}

class BoggleTest(val dictFile: String, val boardFile: String, val outputFile: String)
{
  def run()
  {
    //try load board file
    try
    {
      val boardSource = Source.fromFile(boardFile)
      //try load dictionary file
      try
      {
        val dictSource = Source.fromFile(dictFile)

        //detect board size, then create a board
        val boardLines = boardSource.getLines().toList.map(_.toLowerCase)
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
        val words = dictSource.getLines().toList

        //test each word against board
        //lowercase all words, just to be safe
        //encapsulate solve in a class instance to ease transition to parallelized solvers
        val resultList = new BoggleSolver(board, words.map(_.toLowerCase)).solve

        //write results
        val outputFileWriter = new PrintWriter(new File(outputFile))
        for (word <- resultList) {outputFileWriter.write(word + System.lineSeparator())}
        outputFileWriter.flush()
      }
      catch {case e: java.io.FileNotFoundException => println("ERROR: Cannot open dict file.")}
    }
    catch {case e: java.io.FileNotFoundException => println("ERROR: Cannot open board file.")}
  }
}
