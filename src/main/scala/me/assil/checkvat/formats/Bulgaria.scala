package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/18/17.
  */
class Bulgaria extends VATFormat {
  override val country = "BG"
  override val lengths = List(9, 10)
  override val alpha = List()

  def legalEntity(C: Seq[Int]): Boolean = {
    var A1, A2, R1, R2, R: Int = 0

    for (i <- 1 to 8)
      A1 += i * C(i-1)

    R1 = A1 % 11

    if (R1 == 10) {
      for (i <- 1 to 8)
        A2 += (i+2) * C(i-1)

      R2 = A2 % 11

      if (R2 == 10)
        R = 0
      else
        R = R2
    } else {
      R = R1
    }

    C(8) == R
  }

  def physicalPerson(C: Seq[Int]): Boolean = {
    // 10 digit physical person
    val birthYear = mergeDigits(C.slice(0, 2).toList)
    val birthMonth = mergeDigits(C.slice(2, 4).toList)

    var A1, A2, R1, R2, R: Int = 0

    // Make sure birth month is valid
    val birthMonthCheck: Boolean = {
      // 1900-1999
      birthMonth >= 1 && birthMonth <= 12 ||
      // <1900
      birthMonth >= 1+20 && birthMonth <= 12+20 ||
      // >1999
      birthMonth >= 1+40 && birthMonth <= 12+40
    }

    if (birthMonthCheck) {
      val dateOfBirth = mergeDigits(C.slice(5, 7).toList)

      val birthCheck: Boolean = {
        // February
        val feb = List(2, 22, 42)
        if (feb.contains(birthMonth))
          dateOfBirth >= 1 && dateOfBirth <= 29
        else {
          // Months with 30 days
          val months30 = List(4, 24, 44, 6, 26, 46, 9, 29, 49, 11, 31, 51)
          if (months30.contains(birthMonth))
            dateOfBirth >= 1 && dateOfBirth <= 30

          // Months with 31 days otherwise
          else
            dateOfBirth >= 1 && dateOfBirth <= 31
        }
      }

      A1 += (2*C(0) + 4*C(1) + 8*C(2) + 5*C(3) + 10*C(4) + 9*C(5) + 7*C(6) + 3*C(7) + 6*C(8))
      R1 = A1 % 11

      if (R1 == 10)
        R = 0
      else
        R = R1

      return C(9) == R
    }

    false
  }

  def foreigner(C: Seq[Int]): Boolean = {
    val A1 = 21*C(0) + 19*C(1) + 17*C(2) + 13*C(3) + 11*C(4) + 9*C(5) + 7*C(6) + 3*C(7) + C(8)
    val R = A1 % 10
    C(9) == R
  }

  def otherCategory(C: Seq[Int]): Boolean = {
    val A1 = 4*C(0) + 3*C(1) + 2*C(2) + 7*C(3) + 6*C(4) + 5*C(5) + 4*C(6) + 3*C(7) + 2*C(8)
    val R1 = 11 - A1 % 11

    val R =
      if (R1 == 11)
        0
      else if (R1 == 10)
        -1
      else
        R1

    C(9) == R
  }

  override def check(vat: String): Boolean = {
    // Bulgaria has 4 different VAT formats
    // We will check based on VAT length

    // Common variables between formats
    var A1, A2, R1, R2, R: Int = 0

    // Only digits allowed in VAT
    val allDigits = vat.count(!_.isDigit) == 0

    if (allDigits) {
      val C: Seq[Int] = vat.map(_.asDigit)

      // 9 digit VAT for legal entities
      if (vat.length == 9) {
        return legalEntity(C)
      }

      // 3 possibilities
      else if (vat.length == 10) {
        return physicalPerson(C) || foreigner(C) || otherCategory(C)
      }
    }

    false
  }
}
