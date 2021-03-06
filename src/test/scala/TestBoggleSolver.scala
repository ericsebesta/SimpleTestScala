package esebesta.BoggleSolverScala

import org.specs2.mutable.Specification
import scala.io.Source

class TestBoggleSolver extends Specification
{
	val board1 = Board.makeBoard(Source.fromFile("src/main/resources/board_1.txt").getLines().toList.map(_.toLowerCase))
	val board2 = Board.makeBoard(Source.fromFile("src/main/resources/board_2.txt").getLines().toList.map(_.toLowerCase))
	val board3 = Board.makeBoard(Source.fromFile("src/main/resources/board_3_pvt.txt").getLines().toList.map(_.toLowerCase))
	//loading boards 4 and 5 should throw, so do that in tests instead of here
	val board6 = Board.makeBoard(Source.fromFile("src/main/resources/board_6_pvt.txt").getLines().toList.map(_.toLowerCase))

	sequential

	"solve" should
	{
		"solve a simple board" in
		{
			val boardLines = List(("p w y r"), ("e n t h"), ("g s i q"), ("o l s a"))
			val dictLines = List("foozle", "quit")
			val resultList = new BoggleSolver(Board.makeBoard(boardLines), dictLines.iterator).solve
			resultList mustEqual List("quit")
		}

		"solve board_1 with a regular dictionary" in
		{
			val dictionaryList = Source.fromFile("src/main/resources/dictionary.txt").getLines()
			solve(board1,
				dictionaryList,
				"src/main/resources/board_1_solution.txt")
		}

		"solve board_2 with a regular dictionary" in
		{
			val dictionaryList = Source.fromFile("src/main/resources/dictionary.txt").getLines()
			solve(board2,
				dictionaryList,
				"src/main/resources/board_2_solution.txt")
		}

		"solve board_3 with a regular dictionary" in {
			val dictionaryList = Source.fromFile("src/main/resources/dictionary.txt").getLines()
			solve(board3,
				dictionaryList,
				"src/main/resources/board_3_solution.txt")
		}

		"throw when trying to solve board_4 with a regular dictionary" in
		{
			{val board4 = Board.makeBoard(Source.fromFile("src/main/resources/board_4_pvt.txt").getLines().toList.map(_.toLowerCase))} must throwA[Exception]
		}

		"throw when trying to solve board_5 with a regular dictionary" in
		{
			{val board5 = Board.makeBoard(Source.fromFile("src/main/resources/board_5_pvt.txt").getLines().toList.map(_.toLowerCase))} must throwA[Exception]
		}

		"solve board_6 with a regular dictionary" in
		{
			val dictionaryList = Source.fromFile("src/main/resources/dictionary.txt").getLines()
			solve(board6,
				dictionaryList,
				"src/main/resources/board_6_solution.txt")
		}

		"solve board_1 with a large dictionary" in
			{
				val dictionaryLargeList = Source.fromFile("src/main/resources/dictionary_large.txt").getLines()
				solve(board1,
					dictionaryLargeList,
					"src/main/resources/board_1_solution_large.txt")
			}

		"solve board_2 with a large dictionary" in
		{
			val dictionaryLargeList = Source.fromFile("src/main/resources/dictionary_large.txt").getLines()
			solve(board2,
				dictionaryLargeList,
				"src/main/resources/board_2_solution_large.txt")
		}

		"solve board_3 with a large dictionary" in {
			val dictionaryList = Source.fromFile("src/main/resources/dictionary_large.txt").getLines()
			solve(board3,
				dictionaryList,
				"src/main/resources/board_3_solution_large.txt")
		}

		"throw when trying to solve board_4 with a large dictionary" in {
			{
				val board4 = Board.makeBoard(Source.fromFile("src/main/resources/board_4_pvt.txt").getLines().toList.map(_.toLowerCase))
			} must throwA[Exception]
		}

		"throw when trying to solve board_5 with a large dictionary" in {
			{
				val board5 = Board.makeBoard(Source.fromFile("src/main/resources/board_5_pvt.txt").getLines().toList.map(_.toLowerCase))
			} must throwA[Exception]
		}

		"solve board_6 with a large dictionary" in
		{
			val dictionaryLargeList = Source.fromFile("src/main/resources/dictionary_large.txt").getLines()
			solve(board6,
				dictionaryLargeList,
				"src/main/resources/board_6_solution_large.txt")
		}
	}

	def solve(board: Board, dict: Iterator[String], solutionFile: String) =
	{
		//lowercase everything for simplicity and durability
		val resultList = new BoggleSolver(board, dict).solve
		val solutionSource = Source.fromFile(solutionFile)
		val solutionList = solutionSource.getLines().toList
		resultList mustEqual solutionList
	}
}
