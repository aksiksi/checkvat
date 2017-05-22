package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Slovenia extends VATFormat {
  override val country = "SI"
  override val lengths = List(8)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)
      val X = mergeDigits(C.slice(0, 7).toList)

      if (X >= 1000000 && X <= 9999999) {
        val A1 = (2 to 8).reverse.zip(C).map { case (a, b) => a*b }.sum
        val R = 11 - (A1 % 11)

        return {
          if (R == 10)
            C(7) == 0
          else if (R == 11)
            false
          else
            C(7) == R
        }
      }
    }

    false
  }
}
