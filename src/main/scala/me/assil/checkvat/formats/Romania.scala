package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/19/17.
  */
class Romania extends VATFormat {
  override val country = "RO"
  override val lengths = (1 to 10).toList
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.length) && vat.count(_.isLetter) == 0) {
      if (vat(0) != '0') {
        // Zero-pad due to variable length requirement
        val numZeroes = lengths.last - vat.length
        val C = Seq.fill(numZeroes)(0) ++ vat.map(_.asDigit)

        val weights = Vector(7, 5, 3, 2, 1, 7, 5, 3, 2)
        val A1: Int = weights.zip(C).map(p => p._1*p._2).sum
        val A2 = A1 * 10

        val R1 = A2 % 11

        return {
          if (R1 == 10)
            C(9) == 0
          else
            C(9) == R1
        }
      }
    }

    false
  }
}
