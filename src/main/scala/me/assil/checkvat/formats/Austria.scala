package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/18/17.
  */
class Austria extends VATFormat {
  override val country = "AT"
  override val lengths = List(9)
  override val alpha = List(0)

  // Spec parameters
  val S = (c: Int) => { (c / 5) + (c * 2) % 10 }

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.length)) {
      if (vat.head == 'U') {
        // Prepend placeholder to use same notation as spec
        val C: Seq[Int] = Seq(0, 0) ++ vat.tail.map(_.asDigit)

        val R: Int = S(C(3)) + S(C(5)) + S(C(7))
        val checksum: Int = (10 - (R + C(2) + C(4) + C(6) + C(8) + 4) % 10) % 10

        return C(9) == checksum
      }
    }

    false
  }
}
