package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/18/17.
  */
class Czech extends VATFormat {
  override val country = "CZ"
  override val lengths = List(8, 9, 10)
  override val alpha = List(8)

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.length)) {
      val C = vat.map(_.asDigit)

      if (C(0) == 9) return false

      val A1 = (2 to 8).reverse.map(i => C(8-i)*i).sum

      val A2: Int =
        if (A1 % 11 == 0)
          A1 + 11
        else
          Math.ceil(A1/11.0).toInt * 11

      val D = A2 - A1

      return C(7) == D % 10
    }

    false
  }
}
