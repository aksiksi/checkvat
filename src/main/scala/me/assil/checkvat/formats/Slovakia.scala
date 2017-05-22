package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Slovakia extends VATFormat {
  override val country = "SK"
  override val lengths = List(10)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit)

      val check = {
        C(0) != 0 &&
        List(2,3,4,7,8,9).contains(C(2))
      }

      if (check) {
        val num: Long = mergeDigits(C.toList)
        return num % 11 == 0
      }
    }

    false
  }
}
