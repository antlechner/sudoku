package org.example.sudoku.view

import org.example.sudoku.{Field, Sudoku}

import java.awt.{Color, Font}
import javax.swing.border.LineBorder
import scala.io.Source
import scala.swing._

class UI extends MainFrame {
  title = "GUI variables"
  preferredSize = new Dimension(999, 999)
  // Easy solution but doesn't draw thicker black border between squares
  // val grid = new GridPanel(9, 9) {
  //   for (i <- 1 to 81) {
  //     val label = new Label("x" + i)
  //     label.border = LineBorder.createBlackLineBorder()
  //     contents += label
  //   }
  // }
  val grid = new GridPanel(3, 3) {
    for (i <- 1 to 9) {
      val subgrid = new GridPanel(3, 3) {
        for (j <- 1 to 9) {
          val index = Draw.indexFromSubIndices(i, j)
          val label = new Label("x" + index)
          label.border = new LineBorder(Color.black, 1)
          label.font = new Font("Dialog", Font.BOLD, 23)
          contents += label
        }
      }
      subgrid.border = new LineBorder(Color.black, 3)
      contents += subgrid
    }
  }
  grid.border = new LineBorder(Color.white, 9)
  contents = grid
}

class SudokuUI extends MainFrame {
  title = "GUI example sudoku"
  val source = Source.fromFile("example-solution")
  val sudoku = Sudoku.fromLines(source.getLines().toSeq)
  preferredSize = new Dimension(999, 999)
  val grid = new GridPanel(3, 3) {
    for (i <- 1 to 9) {
      val subgrid = new GridPanel(3, 3) {
        for (j <- 1 to 9) {
          val index = Draw.indexFromSubIndices(i, j)
          val field = Draw.fieldFromIndex(index)
          val label = new Label(sudoku.fieldValues.getOrElse(field, "").toString)
          label.border = new LineBorder(Color.black, 1)
          label.font = new Font("Dialog", Font.BOLD, 23)
          contents += label
        }
      }
      subgrid.border = new LineBorder(Color.black, 3)
      contents += subgrid
    }
  }
  grid.border = new LineBorder(Color.white, 9)
  contents = grid
}

object Draw {
  def main(args: Array[String]) {
    val ui = new SudokuUI
    ui.visible = true
    println("Done")
  }

  def indexFromSubIndices(i: Int, j: Int): Int = {
    val startIndexForSubgrid = ((i - 1) / 3) * 27 + ((i - 1) % 3) * 3 + 1
    val indexToAdd = ((j - 1) / 3) * 9 + (j - 1) % 3
    startIndexForSubgrid + indexToAdd
  }

  def fieldFromIndex(i: Int): Field = {
    val row = (i - 1) / 9 + 1
    val column = (i - 1) % 9 + 1
    Field(row, column)
  }
}
