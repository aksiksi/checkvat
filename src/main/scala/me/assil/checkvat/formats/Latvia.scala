package me.assil.checkvat.formats

import java.text.SimpleDateFormat

import scala.util.{Success, Try}

/**
  * Created by aksiksi on 5/21/17.
  */
class Latvia extends VATFormat {
  override val country = "LV"
  override val lengths = List(11)
  override val alpha = List()

  override def check(vat: String): Boolean = {
    if (vat.length == lengths.head && vat.count(_.isDigit) == lengths.head) {
      val C = vat.map(_.asDigit)

      if (C(0) > 3) {
        // Format 1: Legal Persons
        val weights = Vector(9,1,4,8,3,10,2,5,7,6)
        val A1 = weights.zip(C).map { case (a,b) => a*b }.sum

        val R = 3 - (A1 % 11)

        return {
          if (R < -1)
            C(10) == R + 11
          else if (R > -1)
            C(10) == R
          else
            false
        }
      }

      else if (C(0) <= 3) {
        // Validate C1-C6 as a birth date
        val df = new SimpleDateFormat("DDMMYY")
        val dateString = vat.substring(0, 6)

        val status =
          Try {
            df.parse(dateString)
          } match {
            case Success(v) => true
            case _ => false
          }

        return status
      }
    }

    false
  }
}
