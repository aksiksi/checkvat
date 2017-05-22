package me.assil.checkvat

/**
  * Created by aksiksi on 5/18/17.
  */
object Main {
  def main(args: Array[String]) = {
    val checker = new CheckVAT

    val vats = Array(
      "U10223006",
      "1776091951",
      "101004508"
    )

    vats.foreach { vat => println(s"$vat is ${ if (checker.check(vat)) "valid" else "invalid" }") }
  }
}
