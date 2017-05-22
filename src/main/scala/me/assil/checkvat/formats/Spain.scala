package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class Spain extends VATFormat {
  override val country = "ES"
  override val lengths = List(9)
  override val alpha = List(0, 8)

  def format1(C: Seq[Int], c1: Char, c9: Char): Boolean = {
    val D = (i: Int) => { C(i)/5 + (2*C(i)) % 10 }
    val checkChar = (v: Int) => (v + 64).toChar

    if (c9.isLetter) {
      // Judicial
      val allowed: List[Char] = Range(65, 73).toList.map(_.toChar) ++ List('N','P','Q','R','S','W')

      if (allowed.contains(c1)) {
        val S1 = C(2) + C(4) + C(6)
        val S2 = D(1) + D(3) + D(5) + D(7)
        val R = 10 - (S1 + S2) % 10

        return c9 == checkChar(R)
      }
    }

    false
  }

  def format2(C: Seq[Int], c1: Char, c9: Char): Boolean = {
    def checkChar(v: Int): Char = {
      val mapping = Vector(
        'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P',
        'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V',
        'H', 'L', 'C', 'K', 'E'
      )

      mapping(v-1)
    }

    if (c9.isLetter) {
      // IF C9 Alphabetic and C1= K, L, M, X, Y, Z or numeric
      val allowed = Array('K','L','M', 'X','Y','Z') ++ Range(48, 58).map(_.toChar)

      if (allowed.contains(c1)) {
        val C1 =
          if (c1.isLetter) {
            if (c1 == 'Y') 1
            else if (c1 == 'Z') 2
            else -1
          } else {
            c1.asDigit
          }

        var R: Long = 0

        val num =
          if (C1 != -1) {
          mergeDigits(List(C1) ++ C.slice(1, 8))
        } else {
          mergeDigits(C.slice(1, 8).toList)
        }

        R = num % 23 + 1

        return c9 == checkChar(R.toInt)
      }
    }

    false
  }

  def format3(C: Seq[Int], c9: Char): Boolean = {
    val D = (i: Int) => { C(i)/5 + (2*C(i)) % 10 }

    val S1 = C(2) + C(4) + C(6)
    val S2 = D(1) + D(3) + D(5) + D(7)
    val R = 10 - (S1 + S2) % 10

    c9.asDigit == (R % 10)
  }

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head) {
      val (c1, c9) = (vat(0), vat(8))

      // Padded so it's length = 9
      val C = Seq(0) ++ vat.substring(1, 8).map(_.asDigit)

      val check =
        if (c9.isLetter) {
          val allowedChars = Range(65, 73).toList.map(_.toChar) ++
            List('K','L','M','N','P','Q','R','S','W','X','Y','Z') ++
            Range(48, 58).map(_.toChar)
          allowedChars.contains(c1)
        } else if (c9.isDigit) {
          val allowedChars = Range(65, 73).toList.map(_.toChar) ++ List('J','U','V')
          allowedChars.contains(c1)
        } else false

      if (check && !C.contains(-1)) {
        if (c9.isLetter) {
          // Format 1: Juridical entities other than national ones
          val f1 = format1(C, c1, c9)

          // Format 2: Persons
          val f2 = format2(C, c1, c9)

          return f1 || f2
        } else {
          // Format 3: National juridical
          return format3(C, c9)
        }
      }
    }

    false
  }

}
