package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Finland extends VATFormat {
  override val country = "FI"
  override val lengths = List(8)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit)
      val weights = Vector(7, 9, 10, 5, 8, 4, 2)
      val sum = weights.zip(C).map(p => p._1*p._2).sum
      val R = 11 - sum % 11

      return {
        if (R == 10)
          false
        else if (R == 11)
          C(7) == 0
        else
          C(7) == R
      }
    }

    false
  }

}
