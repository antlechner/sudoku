package org.example.sudoku

import scala.io.Source

object Main {

  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("example-sudoku")
    val sudoku = Sudoku.fromLines(source.getLines().toSeq)
    val formula = sudoku.sudokuFormula()
    println(sudoku)
    println("----------------")
    println(formula.toMinisat())

    // TODO Use command-line args instead of commenting out sections of this function
//    val solutionFromMinisat = Source.fromFile("example-output").getLines().toSeq
//    val solvedSudoku = Result.resultFromMinisat(solutionFromMinisat.head, solutionFromMinisat(1))
//    solvedSudoku.foreach(println)
  }
}
