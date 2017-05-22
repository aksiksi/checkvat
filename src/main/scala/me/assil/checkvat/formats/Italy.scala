package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Italy extends VATFormat {
  override val country = "IT"
  override val lengths = List(11)
  override val alpha = List()

  def checkDigits(C: Seq[Int]): Boolean = {
    val digits = mergeDigits(List(C(7), C(8), C(9)))

    digits > 0 && digits < 101 ||
    List(120, 121, 888, 999).contains(digits)
  }

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)

      val D = (i: Int) => C(i)/5 + (2*C(i)) % 10

      if (checkDigits(C)) {
        val S1 = C(0) + C(2) + C(4) + C(6) + C(8)
        val S2 = D(1) + D(3) + D(5) + D(7) + D(9)
        val checksum = (10 - (S1 + S2) % 10) % 10
        return C(10) == checksum
      }
    }

    false
  }
}
