package org.example.sudoku.logic

case class Literal(index: Int, isTrue: Boolean) {
  def toMinisat(): String = {
    (if (isTrue) "" else "-") + index
  }
}

/** Literals connected by OR */
case class Clause(literals: Seq[Literal]) {
  def toMinisat(): String = {
    literals.map(_.toMinisat()).mkString(" ") + " 0"
  }
}

/** Clauses connected by AND */
case class Formula(clauses: Seq[Clause], numberOfVariables: Int) {
  def toMinisat(): String = {
    val numberOfClauses = clauses.length
    s"p cnf ${numberOfVariables} ${numberOfClauses}\n" +
      clauses.map(_.toMinisat()).mkString("\n")
  }
}
