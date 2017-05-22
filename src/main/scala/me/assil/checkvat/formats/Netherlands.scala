package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Netherlands extends VATFormat {
  override val country = "NL"
  override val lengths = List(12)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (
      vat.length == lengths.head &&
      vat.count(_.isDigit) == lengths.head-1 &&
      vat.count(_.isLetter) == 1 &&
      vat(9) == 'B'
    ) {

      // Remove B character and convert to Ints
      val C: Seq[Int] = vat.map(c => if (c == 'B') 0 else c.asDigit)

      val A1: Int = (2 to 9).reverse.zip(C).map(p => p._1*p._2).sum
      val A2: Int = A1 % 11

      return {
        if (A2 == 10)
          false
        else
          C(8) == A2 && mergeDigits(List(C(10), C(11))) > 0
      }
    }

    false
  }
}
