package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Lithuania extends VATFormat {
  override val country = "LT"
  override val lengths = List(9, 12)
  override val alpha = List()

  def format1(C: Seq[Int]): Boolean = {
    if (C(7) == 1) {
      val A1 = (1 to 8).zip(C).map { case (a, b) => a*b }.sum
      val R1 = A1 % 11

      if (R1 != 10)
        return C(8) == R1
      else {
        val A2 = ((3 to 9) ++ Seq(1)).zip(C).map { case (a, b) => a*b }.sum
        val R2 = A2 % 11

        return {
          if (R2 == 10)
            C(8) == 0
          else
            C(8) == R2
        }
      }
    }

    false
  }

  def format2(C: Seq[Int]): Boolean = {
    if (C(10) == 1) {
      val A1 = ((1 to 9) ++ Seq(1, 2)).zip(C).map { case (a, b) => a*b }.sum
      val R1 = A1 % 11

      if (R1 != 10)
        return C(11) == R1
      else {
        val A2 = ((3 to 9) ++ (1 to 4)).zip(C).map { case (a, b) => a*b }.sum
        val R2 = A2 % 11

        return {
          if (R2 == 10)
            C(11) == 0
          else
            C(11) == R2
        }
      }
    }

    false
  }

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.count(_.isDigit))) {
      val C = vat.map(_.asDigit)

      if (C.length == lengths.head)
        return format1(C)
      else if (C.length == lengths(1))
        return format2(C)
    }

    false
  }
}
