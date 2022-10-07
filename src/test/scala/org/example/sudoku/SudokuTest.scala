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

}
