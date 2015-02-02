package esebesta.BoggleSolverScala

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
        //choose to NOT lowercase dictionary data (it is supposed to come in lowercase, trust this)
        //leave dictionary as an iterator to avoid loading entire file into memory
        val dictLinesIterator = dictSource.getLines()
        val resultList = new BoggleSolver(Board.makeBoard(boardLines), dictLinesIterator).solve

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
