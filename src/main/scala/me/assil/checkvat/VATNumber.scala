package me.assil.checkvat

/**
  * Created by aksiksi on 5/18/17.
  */
class VATNumber(val input: String) {
  // Convert input to valid sequence of digits
  val number: Vector[Int] =
    input.trim.filter(_.isDigit).map(_.toInt)
         .toVector

  // Ensure that length is within VAT limits
  require()

  // Cleanup VAT number and parse into a Vector[Int]
  def parse() = {

  }

  // Check whether or not VAT number is correct based on set rules
  def verify(): Boolean
}
