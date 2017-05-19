package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/18/17.
  */
class Cyprus extends VATFormat {
  override val country = "CY"
  override val lengths = List(9)
  override val alpha = List(8)

  override def check(vat: String): Boolean = {
    if (lengths.contains(vat.length)) {
      // Last symbol MUST be a letter and remainder MUST be digits
      if (!vat(8).isDigit && vat.slice(0, 9).count(_.isDigit) == 8) {
        val C = vat.slice(0, 8).map(_.asDigit).toArray

        val rule1 = {
          // C1 must be one of these numbers
          Seq(0,1,3,4,5,9).contains(C(0)) &&
          // [C1 C2] cannot be 12
          (C(0)*10 + C(1)) != 12
        }

        if (rule1) {
          // Re-map odd digits as defined in spec (pg. 15)
          for (i <- 0 until 7 by 2) {
            C(i) = C(i) match {
              case 0 => 1
              case 1 => 0
              case 2 => 5
              case 3 => 7
              case 4 => 9
              case 5 => 13
              case 6 => 15
              case 7 => 17
              case 8 => 19
              case 9 => 21
            }
          }

          var A1 = 0

          for (i <- 0 until 8)
            A1 += C(i)

          val R = A1 % 26

          // Map R into ASCII character and compare with last VAT symbol
          val RC: Char = (R + 65).toChar
          return RC == vat(8)
        }
      }
    }

    false
  }
}
