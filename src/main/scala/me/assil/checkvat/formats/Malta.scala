package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Malta extends VATFormat {
  override val country = "MT"
  override val lengths = List(8)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit)
      val C16 = C.slice(0, 6).toList

      if (mergeDigits(C16) > 100000) {
        val weights = Vector(3, 4, 6, 7, 8, 9)
        val A1 = weights.zip(C16).map(p => p._1 * p._2).sum
        val R = 37 - (A1 % 37)

        val C78 = mergeDigits(C.slice(6, 8).toList)

        return {
          if (R == 0)
            C78 == 37
          else
            C78 == R
        }
      }
    }

    false
  }
}
