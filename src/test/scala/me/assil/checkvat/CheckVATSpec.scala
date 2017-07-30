package me.assil.checkvat

import org.scalatest.FunSpec

class CheckVATSpec extends FunSpec {
  describe("CheckVAT") {
    val checker = new CheckVAT

    it("should detect valid VATs from EU countries") {
      // Austria
      assert(checker.check("U10223006"))

      // France (old style)
      assert(checker.check("47323875187"))

      // UK (format 2)
      assert(checker.check("004123443"))

      // Ireland (format 1)
      assert(checker.check("8Z49289F"))
      assert(checker.check("6346967G"))

      // Spain (format 1)
      assert(checker.check("W7815395D"))

      // Latvia (format 2)
      assert(checker.check("07091910933"))

      // Lithuania (format 2)
      assert(checker.check("290061371314"))
    }

    it("should detect a VAT given an EU country code") {
      assert(checker.check("290061371314", "LT"))
      assert(checker.check("W7815395D", "ES"))
      assert(checker.check("47323875187", "FR"))
    }
  }
}
