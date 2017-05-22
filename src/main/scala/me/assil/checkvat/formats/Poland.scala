package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Poland extends VATFormat {
  override val country = "PL"
  override val lengths = List(10)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)
      val weights = Vector(6, 5, 7, 2, 3, 4, 5, 6, 7)
      val A1: Int = weights.zip(C).map(p => p._1*p._2).sum
      val R = A1 % 11

      return {
        if (R == 10)
          false
        else
          C(9) == R
      }
    }

    false
  }

}
