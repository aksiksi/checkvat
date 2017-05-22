package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Sweden extends VATFormat {
  override val country = "SE"
  override val lengths = List(12)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit)
      val X = mergeDigits(C.slice(10, 12).toList)

      val S = (i: Int) => C(i)/5 + (C(i)*2) % 10

      if (X >= 1 && X <= 94) {
        val R: Int = (0 until 9 by 2).map(S(_)).sum
        val evenC: Int = (1 until 8 by 2).map(C(_)).sum
        val checksum = (10 - (R + evenC) % 10) % 10

        return C(9) == checksum
      }
    }

    false
  }
}
