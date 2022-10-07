package org.example.sudoku

object Result {

  def resultFromMinisat(satOrUnsat: String, assignment: String): Option[Sudoku] = {
    satOrUnsat match {
      case "SAT" => Some(new Sudoku(
        assignment.split(" ").init.filter(literal => !literal.startsWith("-")).map(
          literal => Sudoku.fieldAndValueFromLiteralIndex(literal.toInt)
        ).toMap))
      case _ => None
    }
  }
}
