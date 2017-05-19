package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Denmark extends VATFormat {
  override val country = "DK"
  override val lengths = List(8)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit).toArray

      if (C(0) > 0) {
        val weights: Vector[Int] = Vector(2, 7, 6, 5, 4, 3, 2, 1)

        // = (2*C1 + 7*C2 + 6*C3 + 5*C4 + 4*C5 + 3*C6 + 2*C7 + C8)
        val R = weights.zipWithIndex.map(p => p._1*C(p._2)).sum

        return R % 11 == 0
      }
    }

    false
  }
}
