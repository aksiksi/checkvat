package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Estonia extends VATFormat {
  override val country = "EE"
  override val lengths = List(9)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)
      val weights = Vector(3, 7, 1, 3, 7, 1, 3, 7)
      val A1 = weights.zip(C).map(p => p._1*p._2).sum
      val A2 = Math.ceil(A1 / 10.0) * 10
      return C(8) == A2.toInt - A1
    }

    false
  }

}
