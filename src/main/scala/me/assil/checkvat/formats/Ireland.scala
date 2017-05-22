package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Ireland extends VATFormat {
  override val country = "IE"
  override val lengths = List(8)
  override val alpha = List(1, 7)

  def checkChar(v: Int): Char = {
    if (v == 0)
      'W'
    else
      (v + 64).toChar
  }

  def oldStyle(vat: String): Boolean = {
    val digits = (List(vat(0)) ++ vat.substring(2, 7)).map(_.asDigit)

    if (!digits.contains(-1)) {
      // C2 and C8 have constraints on their values
      val check = {
        vat(1) >= 65 && vat(1) <= 90 || vat(1) == '+' || vat(1) == '*' &&
        vat(7) >= 65 && vat(7) <= 87
      }

      if (check) {
        // N = [N1 N2 N3 N4 N5 N6 N7] = [0 C3 C4 C5 C6 C7 C1]
        val N: Vector[Int] = Vector(0) ++ digits.slice(1, 6).toVector ++ Vector(digits.head)
        val R = (2 to 8).reverse.zip(N).map { case (a, b) => a*b }.sum % 23

        return checkChar(R) == vat(7)
      }
    }

    false
  }

  def newStyle(vat: String): Boolean = {
    val digits = vat.substring(0, 7).map(_.asDigit)

    if (!digits.contains(-1)) {
      // A-W for C8
      if (vat(7) >= 65 && vat(7) <= 87) {
        val R = (2 to 8).reverse.zip(digits).map { case (a,b) => a*b }.sum % 23
        return checkChar(R) == vat(7)
      }
    }

    false
  }

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head)
      return oldStyle(vat) || newStyle(vat)

    false
  }
}
