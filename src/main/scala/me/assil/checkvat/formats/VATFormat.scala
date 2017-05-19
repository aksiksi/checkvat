package me.assil.checkvat.formats

object VATFormat {
  // Helper function that merges single digits into one number
  def mergeDigits(digits: List[Int]): Int = {
    if (digits.isEmpty) 0
    else digits.head * Math.pow(10, digits.length-1).toInt + mergeDigits(digits.tail)
  }
}

/**
  * Defines VAT format for a given country.
  */
trait VATFormat {
  // Country
  val country: String

  // Accepted VAT number lengths
  val lengths: List[Int]

  // Indicates which indices are allowed to be letters
  val alpha: List[Int]

  // Check if given number adheres to the format
  def check(vat: String): Boolean
}
