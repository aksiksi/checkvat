package me.assil.checkvat

import me.assil.checkvat.formats._

/**
  * Checks a VAT number for validity.
  */
class CheckVAT {
  // Map of VAT formats for all EU member states
  val FORMATS = Map(
    "AT" -> new Austria,
    "BE" -> new Belgium,
    "BG" -> new Bulgaria,
    "CY" -> new Cyprus,
    "CZ" -> new Czech,
    "DE" -> new Germany,
    "DK" -> new Denmark,
    "EE" -> new Estonia,
    "EL" -> new Greece,
    "ES" -> new Spain,
    "FI" -> new Finland,
    "FR" -> new France,
    "UK" -> new UnitedKingdom,
    "HU" -> new Hungary,
    "IE" -> new Ireland,
    "IT" -> new Italy,
    "LT" -> new Lithuania,
    "LU" -> new Luxembourg,
    "LV" -> new Latvia,
    "MT" -> new Malta,
    "NL" -> new Netherlands,
    "PL" -> new Poland,
    "PT" -> new Portugal,
    "RO" -> new Romania,
    "SE" -> new Sweden,
    "SI" -> new Slovenia,
    "SK" -> new Slovakia
  )

  val COUNTRIES = FORMATS.keySet.toVector

  /**
    * Checks if an input VAT `String` is a valid VAT number. Enumerates all defined VAT
    * formats, as defined in the variable `FORMATS` above.
    *
    * @param input Input VAT `String` to be checked
    * @return VAT valid (true) or invalid (false)
    */
  def check(input: String): Boolean = {
    // VAT can only contain digits or letters OR + and * in the case of Ireland
    val vat: String = input.trim.filter(c => c.isLetterOrDigit || List('+', '*').contains(c))

    // Check if given VAT matches against any of the defined formats
    for ((_, fmt) <- FORMATS) {
      if (fmt.check(vat))
        return true
    }

    false
  }

  /**
    * Checks if an input VAT `String` is a valid VAT number based on given country code.
    *
    * @param input Input VAT `String` to be checked
    * @param country Two letter ISO country code
    * @return VAT valid (true) or invalid (false)
    */
  def check(input: String, country: String): Boolean = {
    // VAT can only contain digits or letters OR +, * in the case of Ireland
    val vat: String = input.trim.filter(c => c.isLetterOrDigit || List('+', '*').contains(c))

    // Grab correct format from map
    val lookup: Option[VATFormat] = FORMATS.get(country)

    lookup match {
      case None => false
      case Some(v) => v.check(vat)
    }
  }
}
