package me.assil.checkvat.formats

import VATFormat.mergeDigits

/**
  * Created by aksiksi on 5/19/17.
  */
class UnitedKingdom extends VATFormat {
  override val country = "UK"
  override val lengths = List(5, 9, 12)
  override val alpha = List(0, 1)

  def format1(vat: String): Boolean = {
    if (vat.length == lengths.head) {
      val header: String = vat.substring(0, 2)
      val digits = vat.substring(2, 5).map(_.asDigit)

      if (!digits.contains(-1)) {
        val num: Long = mergeDigits(digits.toList)

        return {
          if (header == "GD")
            num >= 0 && num <= 499
          else if (header == "HA")
            num >= 500 && num <= 999
          else
            false
        }
      }
    }

    false
  }

  def format2(vat: String): Boolean = {
    if (lengths.tail.contains(vat.length)) {
      val C = vat.map(_.asDigit)

      if (C.contains(-1))
        return false

      val C1 = mergeDigits(C.slice(0, 9).toList)

      // Check for both [C1-C9] > 0 and [C10-C12] > 0 (if length == 12)
      val cond: Boolean = {
        if (C.length == 12)
          mergeDigits(C.slice(9, 12).toList) > 0 && C1 > 0
        else
          C1 > 0
      }

      if (cond) {
        val sum = (2 to 8).reverse.zip(C).map { case (a, b) => a*b }.sum
        val C89 = mergeDigits(List(C(7), C(8)))

        val R1 = (sum + C89) % 97
        val R2 = (sum + C89 + 55) % 97

        val C2 = mergeDigits(C.slice(0, 7).toList)

        if (R1 == 0) {
          // Make sure C1-C7 DO NOT fall inside ranges below
          return {
            !inRange(C2, List(100000, 999999)) &&
            !inRange(C2, List(9490001, 9700000)) &&
            !inRange(C2, List(9990001, 9999999))
          }
        }

        else if (R2 == 0) {
          // Make sure C1-C7 DO NOT fall inside ranges below
          return {
            !inRange(C2, List(1, 100000)) &&
            !inRange(C2, List(100001, 1000000))
          }
        }
      }
    }

    false
  }

  def inRange(num: Long, range: List[Long]): Boolean = {
    if (range.length > 2)
      return false

    val result =
      if (num >= range.head && num <= range.last)
        true
      else
        false

    result
  }

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.length)) {
      return format1(vat) || format2(vat)
    }

    false
  }
}
