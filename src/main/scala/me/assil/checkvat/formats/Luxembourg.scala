package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Luxembourg extends VATFormat {
  override val country = "LU"
  override val lengths = List(8)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)

      val A1 = mergeDigits(C.slice(0, 6).toList)
      val A2 = mergeDigits(C.slice(6, 8).toList)

      return A2 == A1 % 89
    }

    false
  }
}
