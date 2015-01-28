import java.io.{File, PrintWriter}
import scala.io.Source

object BoggleTest extends App
{
  if (args.length != 3) println("Usage: BoggleTest <dictionary_filename> <board_filename> <output_filename>")
  else {
    //try load board file
    try {
      val boardSource = Source.fromFile(args(1))
      //try load dictionary file
      try {
        val dictSource = Source.fromFile(args(0))
        //lowercase board to match incoming dict data
        val boardLines = boardSource.getLines().toList.map(_.toLowerCase)
        val dictLines = dictSource.getLines().toList
        val resultList = new BoggleSolver(Board.makeBoard(boardLines), dictLines).solve

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
