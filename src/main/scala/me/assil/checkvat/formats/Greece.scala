package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Greece extends VATFormat {
  override val country = "EL"
  override val lengths = List(9)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)
      val weights = Vector(256, 128, 64, 32, 16, 8, 4, 2)
      val A1 = weights.zip(C).map(p => p._1*p._2).sum
      val A2 = A1 % 11
      return C(8) == A2 % 10
    }

    false
  }

}
