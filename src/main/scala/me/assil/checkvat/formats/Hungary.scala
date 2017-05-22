package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Hungary extends VATFormat {
  override val country = "HU"
  override val lengths = List(8)
  override val alpha = List()

  def getOnesDigit(num: Int): Int = {
    String.valueOf(num).toCharArray.last.asDigit
  }

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)
      val weights = Vector(9, 7, 3, 1, 9, 7, 3)

      val A1 = weights.zip(C).map(p => p._1*p._2).sum
      val rightCol = getOnesDigit(A1) // Get rightmost digit of A1

      return {
        if (rightCol == 0)
          C(7) == 0
        else
          C(7) == 10 - rightCol
      }
    }

    false
  }
}
