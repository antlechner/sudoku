package org.example.sudoku

import org.specs2.mutable.Specification

class SudokuTest extends Specification {

  "fieldIndex" should {
    "return the correct field index" in {
      Sudoku.fieldIndex(1, 1) mustEqual 1
      Sudoku.fieldIndex(1, 5) mustEqual 5
      Sudoku.fieldIndex(1, 9) mustEqual 9
      Sudoku.fieldIndex(2, 1) mustEqual 10
      Sudoku.fieldIndex(3, 1) mustEqual 19
      Sudoku.fieldIndex(3, 5) mustEqual 23
    }
  }

  "literalIndex" should {
    "return the correct literal index" in {
      Sudoku.literalIndex(1, 1, 1) mustEqual 1
      Sudoku.literalIndex(1, 1, 2) mustEqual 82
      Sudoku.literalIndex(1, 2, 1) mustEqual 2
      Sudoku.literalIndex(1, 2, 2) mustEqual 83
    }
  }

  "all fields in square with given index" should {
    "be correctly returned for square 1 in a 3x3 grid" in {
      Sudoku.fieldsInSquare(1) mustEqual Seq(
        Field(1, 1), Field(1, 2), Field(1, 3), Field(2, 1), Field(2, 2), Field(2, 3), Field(3, 1), Field(3, 2), Field(3, 3)
      )
    }
    "be correctly returned for square 5 in a 3x3 grid" in {
      Sudoku.fieldsInSquare(5) mustEqual Seq(
        Field(4, 4), Field(4, 5), Field(4, 6), Field(5, 4), Field(5, 5), Field(5, 6), Field(6, 4), Field(6, 5), Field(6, 6)
      )
    }
    "be correctly returned for square 9 in a 3x3 grid" in {
      Sudoku.fieldsInSquare(9) mustEqual Seq(
        Field(7, 7), Field(7, 8), Field(7, 9), Field(8, 7), Field(8, 8), Field(8, 9), Field(9, 7), Field(9, 8), Field(9, 9)
      )
    }
  }

}
