package me.assil.checkvat

import me.assil.checkvat.formats._

import scalaj.http._

/**
  * Checks a VAT number for validity.
  */
class CheckVAT {
  // Params: country code, country code, VAT
  val REMOTE_URL = "http://ec.europa.eu/taxation_customs/vies/viesquer.do?selectedLanguage=PL&ms=%s&iso=%s&vat=%s"

  // Build a map of VAT formats for all EU member states
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
    * Checks if an input String is a valid VAT number. Enumerates all defined VAT
    * formats, as defined in `FORMATS` above).
    *
    * @param input Input String to be checked
    * @return VAT is valid or not valid
    */
  def check(input: String): Boolean = {
    // Ensure input is valid sequence of characters
    val vat: String = input.trim.filter(c => c.isLetterOrDigit || List('+', '*').contains(c))

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
    * @param country Two letter ISO country code
    * @return Valid VAT number or not
    */
  def check(input: String, country: String): Boolean = {
    // VAT can only contain digits or letters OR +, * in the case of Ireland
    val vat: String = input.trim.filter(c => c.isLetterOrDigit || List('+', '*').contains(c))

    // Grab correct format from map
    val lookup: Option[VATFormat] = FORMATS.get(country)

    lookup match {
      case None => false
      case Some(v) => true
    }
  }

  /**
    * Checks an input VAT number and country against the EU database.
    *
    * @param vat VAT number in string format
    * @param country Two letter ISO country code
    * @return
    */
  def remoteCheck(vat: String, country: String): Boolean = {
    // VAT can only contain digits or letters OR +, * in the case of Ireland
    val clean: String = vat.trim.filter(c => c.isLetterOrDigit || List('+', '*').contains(c))

    // Perform API request
    val url = REMOTE_URL.format(country, country, clean)
    val body = Http(url).asString.body

    // Check valid/invalid based on returned HTML
    if (body.contains("invalidStyle"))
      false
    else if (body.contains("validStyle"))
      true
    else
      false
  }
}
