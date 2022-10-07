package org.example.sudoku

case class Field(row: Int, column: Int) {
  def right(): Field = {
    Field(row, column + 1)
  }

  def down(): Field = {
    Field(row + 1, column)
  }
}
