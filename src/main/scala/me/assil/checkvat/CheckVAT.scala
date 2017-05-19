package me.assil.checkvat

import me.assil.checkvat.formats._

object CheckVAT {
  // Build a map of VAT formats for all EU member states
  lazy val FORMATS: Map[String, VATFormat] = Map(
    "AT" -> new Austria,
    "BE" -> new Belgium,
    "BG" -> new Bulgaria,
    "CY" -> new Cyprus,
    "CZ" -> new Czech
  )
}

/**
  * Checks a VAT number for validity.
  */
class CheckVAT {
  import CheckVAT._

  /**
    * Checks if an input String is a valid VAT number. Enumerates all defined VAT
    * formats, as defined in `FORMATS` above).
    *
    * @param input Input String to be checked
    * @return Valid VAT number or not
    */
  def check(input: String): Boolean = {
    // Ensure input is valid sequence of characters
    val vat: String = input.trim.filter(_.isLetterOrDigit)

    // Check if given VAT matches against any of the defined formats
    for ((_, fmt) <- FORMATS) {
      if (fmt.check(vat))
        return true
    }

    false
  }

  /**
    * Checks if an input String is a valid VAT number based on given country code.
    *
    * @param input Input String to be checked
    * @param country Two letter country code
    * @return Valid VAT number or not
    */
  def check(input: String, country: String): Boolean = {
    require(FORMATS.values.exists(_.country == country), "Invalid country code.")

    val vat: String = input.trim.filter(_.isLetterOrDigit)

    // Grab correct format from map
    val fmt: Option[VATFormat] = FORMATS.get(country)

    fmt match {
      case None => false
      case Some(v) => v.check(vat)
    }
  }
}
