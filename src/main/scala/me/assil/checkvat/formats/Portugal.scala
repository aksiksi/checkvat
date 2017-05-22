package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Portugal extends VATFormat {
  override val country = "PT"
  override val lengths = List(9)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit)

      if (C(0) > 0) {
        val A1: Int = (2 to 9).reverse.zip(C).map(p => p._1 * p._2).sum

        val R: Int = 11 - A1 % 11

        return {
          if (R == 10 || R == 11)
            C(8) == 0
          else
            C(8) == R
        }
      }
    }

    false
  }
}
