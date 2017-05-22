package me.assil.checkvat.formats

import java.time.LocalDate

/**
  * Created by aksiksi on 5/18/17.
  */
class Czech extends VATFormat {
  import VATFormat.mergeDigits

  override val country = "CZ"
  override val lengths = List(8, 9, 10)
  override val alpha = List(8)

  def legalEntities(C: Seq[Int]): Boolean = {
    if (C(0) == 9) return false

    // A1 = 8*C1 + 7*C2 + 6*C3 + 5*C4 + 4*C5 + 3*C6 + 2*C7
    val A1 = (2 to 8).reverse.map(i => C(8-i)*i).sum

    val A2: Int =
      if (A1 % 11 == 0)
        A1 + 11
      else
        Math.ceil(A1/11.0).toInt * 11

    val D = A2 - A1

    C(7) == D % 10
  }

  def individualsBasic(C: Seq[Int]): Boolean = {
    val birthYear = mergeDigits(List(C(0), C(1)))
    val birthMonth = mergeDigits(List(C(2), C(3)))
    val birthDay = mergeDigits(List(C(4), C(5)))

    val yearCheck = birthYear >= 0 && birthYear <= 53
    val monthCheck = {
      (birthMonth >= 1 && birthMonth <= 12) ||
      (birthMonth >= 51 && birthMonth <= 62)
    }

    if (yearCheck && monthCheck) {
      val dayCheck =
        if (List(2, 52).contains(birthMonth))
          birthDay >= 1 && birthDay <= 29
        else if (List(4, 54, 6, 56, 9, 59, 11, 61).contains(birthMonth))
          birthDay >= 1 && birthDay <= 30
        else
          birthDay >= 1 && birthDay <= 31

      return yearCheck && monthCheck && dayCheck
    }

    false
  }

  def individualsLong(C: Seq[Int]): Boolean = {
    if (mergeDigits(C.toList) % 11 == 0) {
      val birthYear = mergeDigits(List(C(0), C(1)))
      val birthMonth = mergeDigits(List(C(2), C(3)))
      val birthDay = mergeDigits(List(C(4), C(5)))

      val currentYear = LocalDate.now().getYear % 100

      val yearCheck = {
        birthYear >= 0 && birthYear <= currentYear ||
        birthYear >= 54 && birthYear <= 99
      }
      val monthCheck = {
        (birthMonth >= 1 && birthMonth <= 12) ||
        (birthMonth >= 21 && birthMonth <= 32) ||
        (birthMonth >= 51 && birthMonth <= 62) ||
        (birthMonth >= 71 && birthMonth <= 82)
      }

      if (yearCheck && monthCheck) {
        val dayCheck =
          if (List(2, 22, 52, 72).contains(birthMonth))
            birthDay >= 1 && birthDay <= 29
          else if (List(4, 24, 54, 74, 6, 26, 56, 76, 9, 29, 59, 79, 11, 31, 61, 81).contains(birthMonth))
            birthDay >= 1 && birthDay <= 30
          else
            birthDay >= 1 && birthDay <= 31

        if (dayCheck) {
          val A1 = birthYear + birthMonth + birthDay +
            mergeDigits(List(C(6), C(7))) + mergeDigits(List(C(8), C(9)))

          return A1 % 11 == 0
        }
      }
    }

    false
  }

  def specialIndividuals(C: Seq[Int]): Boolean = {
    if (C.head == 6) {
      // A1 = 8*C2 + 7*C3 + 6*C4 + 5*C5 + 4*C6 + 3*C7 + 2*C8
      val A1 = (2 to 8).reverse.map(i => C(9-i)*i).sum

      val A2: Int =
        if (A1 % 11 == 0)
          A1 + 11
        else
          Math.ceil(A1/11.0).toInt * 11

      val D = A2 - A1

      val mapping = (1 to 8).reverse ++ Seq(0, 9, 8)

      if (D > 0)
        return C(8) == mapping(D-1)
    }

    false
  }

  override def check(vat: String): Boolean = {
    if (vat.count(!_.isDigit) == 0) {
      val C = vat.map(_.asDigit)

      if (vat.length == 8)
        return legalEntities(C)
      else if (vat.length == 9)
        return individualsBasic(C) || specialIndividuals(C)
      else if (vat.length == 10)
        return individualsLong(C)
    }

    false
  }
}
