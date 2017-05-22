package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class France extends VATFormat {
  override val country = "FR"
  override val lengths = List(11)
  override val alpha = List(0, 1)
  val allowedChars = "ABCDEFGHJKLMNPQRSTUVWXYZ"

  def oldStyle(vat: String): Boolean = {
    if (vat.count(_.isLetter) == 0) {
      val C = vat.map(_.asDigit)

      val checksum = mergeDigits(C.slice(0, 2).toList)
      val checkdigits = mergeDigits((C.slice(2, lengths.head) ++ List(1,2)).toList)

      return checksum == checkdigits % 97
    }

    false
  }

  def newStyle(vat: String): Boolean = {
    val check = {
      vat.substring(0, 2).count(c => c.isDigit && allowedChars.contains(c)) == 2 &&
      vat.substring(2, 11).count(_.isDigit) == 9
    }

    if (check) {
      val digits = vat.substring(2, 11).map(_.asDigit)

      val S1 = checkChar(vat(0))
      val S2 = checkChar(vat(1))

      var S = 0

      if (vat(0).isDigit && allowedChars.contains(vat(1))) {
        S = (S1 * 24) + (S2 - 10)
      }

      if (allowedChars.contains(vat(0))) {
        S = (S1 * 34) + (S2 - 100)
      }

      val P = (S/11) + 1
      val R1 = S % 11
      val R2 = (mergeDigits(digits.toList) + P) % 11

      return R1 == R2
    }

    false
  }

  def checkChar(c: Char): Int = {
    if (c.isDigit)
      c.asDigit
    else {
      val idx = allowedChars.indexOf(c)
      idx + 10
    }
  }

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head) {
      return oldStyle(vat) || newStyle(vat)
    }

    false
  }
}
