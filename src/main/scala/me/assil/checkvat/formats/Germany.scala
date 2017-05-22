package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Germany extends VATFormat {
  override val country = "DE"
  override val lengths = List(9)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit).toArray

      if (C(0) > 0) {
        var P = 10

        C.slice(0, 8).foreach { Cn =>
          val S = Cn + P
          var M = S % 10
          if (M == 0) M = 10
          P = (2*M) % 11
        }

        val R = 11 - P

        return {
          if (R == 10)
            C(8) == 0
          else
            C(8) == R
        }
      }
    }

    false
  }
}
