package org.example.sudoku

import org.example.sudoku.logic.{Clause, Formula, Literal}

class Sudoku(val fieldValues: Map[Field, Int]) {

  /**
   * For the given values in the puzzle, we add single-variable clauses to represent these values.
   * @return a sequences of clauses, one for each known value
   */
  def knownValueClauses(): Seq[Clause] = {
    fieldValues.map { case (field, value) => Sudoku.fieldHasSpecifiedValue(field.row, field.column, value) }.toSeq
  }

  /**
   * @return a formula representing a specific Sudoku puzzle. Consists of clauses for the general rules of Sudoku and
   *         clauses for the given values in this puzzle.
   */
  def sudokuFormula(): Formula = {
    logic.Formula(Sudoku.sudokuRules() ++ knownValueClauses(), Sudoku.numberOfVariables)
  }

  def rowContents(row: Int): Seq[Int] = {
    Sudoku.values().map(column => fieldValues.getOrElse(Field(row, column), 0))
  }

  override def toString: String = {
    Sudoku.values().map(row => rowContents(row).mkString("")).mkString("\n")
  }
}

object Sudoku {

  val root = 3;
  val size = root * root; // 9
  val numberOfVariables = literalIndex(size, size, size) // 729

  def fromLines(lines: Seq[String]): Sudoku = {
    new Sudoku(lines.zipWithIndex.flatMap { case (lineContent, lineIndex) => knownValues(lineContent, lineIndex + 1) }.toMap)
  }

  def knownValues(rowContents: String, rowIndex: Int): Seq[(Field, Int)] = {
    rowContents.zipWithIndex.filter { case (content, _) => content != '0'}.
      map { case (content, columnIndex) => (Field(rowIndex, columnIndex + 1), content.asDigit)
    }
  }

  def values(): Range = {
    1 to size
  }

  def fieldIndex(row: Int, column: Int): Int = {
    (row - 1) * size + column
  }

  /**
   * Index of the variable that is true iff the field at row {@code row} and column {@code column} has value {@code value}.
   */
  def literalIndex(row: Int, column: Int, value: Int): Int = {
    fieldIndex(row, column) + (value - 1) * size * size
  }

  def fieldAndValueFromLiteralIndex(index: Int): (Field, Int) = {
    val fieldIndex = (index - 1) % (size * size) + 1
    val row = (fieldIndex - 1) / size + 1
    val column = (fieldIndex - 1) % size + 1
    val value = (index - 1) / (size * size) + 1
    (Field(row, column), value)
  }

  def fieldLiteral(row: Int, column: Int, value: Int, isTrue: Boolean): Literal = {
    Literal(literalIndex(row, column, value), isTrue)
  }

  def fieldHasSpecifiedValue(row: Int, column: Int, value: Int): Clause = {
    Clause(Seq(Literal(literalIndex(row, column, value), true)))
  }

  def sudokuRules(): Seq[Clause] = {
    allFieldsContainSomething() ++ allFieldsContainAtMostOneValue() ++
      values().flatMap(row => rowContainsAllValues(row)) ++
      values().flatMap(column => columnContainsAllValues(column)) ++
      values().flatMap(square => squareContainsAllValues(square))
  }

  def allFieldsContainSomething(): Seq[Clause] = {
    values().flatMap(row => values().map(column => fieldContainsAtLeastOneValue(row, column)))
  }

  def fieldContainsAtLeastOneValue(row: Int, column: Int): Clause = {
    Clause(values().map(value => Literal(literalIndex(row, column, value), true)))
  }

  def allFieldsContainAtMostOneValue(): Seq[Clause] = {
    values().flatMap(row => values().flatMap(column => fieldContainsAtMostOneValue(row, column)))
  }

  def fieldContainsAtMostOneValue(row: Int, column: Int): Seq[Clause] = {
    values().flatMap(v => ifFieldContainsThisValueThenItContainsNothingElse(row, column, v))
  }

  // for 1:
  // rc1 -> !(rc2 | rc3 | ... | rc9)
  // !rc1 | !(rc2 | rc3 | ... | rc9)
  // !rc1 | (!rc2 & !rc3 & ... & !rc9)
  // (!rc1 | !rc2) & (!rc1 | !rc3) & ... & (!rc1 | !rc9)
  def ifFieldContainsThisValueThenItContainsNothingElse(row: Int, column: Int, value: Int): Seq[Clause] = {
    values().filter(v => v != value).map(v => Clause(Seq(
      Literal(literalIndex(row, column, value), false),
      Literal(literalIndex(row, column, v), false)
    )))
  }

  def rowContainsAllValues(row: Int): Seq[Clause] = {
    values().map(v => rowContainsValue(row, v))
  }

  // r1v | r2v | ... | r9v
  def rowContainsValue(row: Int, value: Int): Clause = {
    Clause(values().map(column => Literal(literalIndex(row, column, value), true)))
  }

  def columnContainsAllValues(column: Int): Seq[Clause] = {
    values().map(v => columnContainsValue(column, v))
  }

  // 1cv | 2cv | ... | 9cv
  def columnContainsValue(column: Int, value: Int): Clause = {
    Clause(values().map(row => Literal(literalIndex(row, column, value), true)))
  }

  def squareContainsAllValues(square: Int): Seq[Clause] = {
    values().map(v => squareContainsValue(square, v))
  }

  def squareContainsValue(square: Int, value: Int): Clause = {
    Clause(fieldsInSquare(square).map(field => Literal(literalIndex(field.row, field.column, value), true)))
  }

  // starting position:
  //   1 => (1, 1)
  //   2 => (1, 4)
  //   3 => (1, 7)
  //   4 => (4, 1)
  //   5 => (4, 4)
  //   6 => (4, 7)
  //   7 => (7, 1)
  //   8 => (7, 4)
  //   9 => (7, 7)
  def fieldsInSquare(square: Int): Seq[Field] = {
    val start = Field(((square - 1) / root) * root + 1, ((square - 1) % root)  * root + 1)
    val leftmostFields = Seq.fill(root)(start).zipWithIndex.map{ case (field, index) => Field(field.row + index, field.column) }
    leftmostFields.flatMap(leftField => Seq.fill(root)(leftField).zipWithIndex.map{ case (field, index) => Field(field.row, field.column + index) } )
  }
}
