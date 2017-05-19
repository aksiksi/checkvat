package me.assil.checkvat

/**
  * Created by aksiksi on 5/18/17.
  */
object Main {
  def main(args: Array[String]) = {
    println("Hello!")

    val check = new CheckVAT

    // Check Austrian VAT
    println(check.check("U10223006"))

    // Check Belgian VAT
    println(check.check("1776091951"))

    // Check Bulgarian VAT
//    println(check.check("101004508"))
  }
}
