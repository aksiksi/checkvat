package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/18/17.
  */
class Belgium extends VATFormat {
  override val country = "BE"
  override val lengths = List(10)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.length)) {
      if (vat.head == '0') {
        val C: Seq[Int] = vat.map(_.asDigit)

        if (C.contains(-1) || C(1) == 0)
          return false

        // Build a single number out of C0-C7
        val num: Long = mergeDigits(C.slice(0, 8).toList)

        // Compute checksum
        val checksum: Long = 97 - (num % 97)

        return (C(8)*10 + C(9)) == checksum
      }
    }

    false
  }
}
