package me.assil.checkvat

import org.scalatest.FunSpec
import me.assil.checkvat.formats._

class CheckVATSpec extends FunSpec {
  describe("Austria VAT") {
    val checker = new Austria

    it("should detect valid VAT") {
      assert(checker.check("U10223006"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("U10223007"))
    }
  }

  describe("Belgium VAT") {
    val checker = new Belgium

    it("should detect valid VAT") {
      assert(checker.check("0776091951"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("1776091951"))
    }
  }

  describe("Bulgaria VAT") {
    val checker = new Bulgaria

    it("should detect valid Type 1 VAT") {
      assert(checker.check("101004508"))
    }

    it("should detect valid Type 2 VAT") {
      assert(checker.check("0041010002"))
    }

    it("should detect valid Type 3 VAT") {
      assert(checker.check("0000100159"))
    }

    it("should detect valid Type 4 VAT") {
      assert(checker.check("0000100153"))
    }
  }

  describe("Cyprus VAT") {
    val checker = new Cyprus

    it("should detect valid VAT") {
      assert(checker.check("00532445O"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("00532445J"))
    }
  }

  describe("Czech VAT") {
    val checker = new Czech

    it("should detect valid Type 1 VAT") {
      assert(checker.check("46505334"))
    }

    it("should detect valid Type 2 VAT") {
      assert(checker.check("395601439"))
    }

    it("should detect valid Type 3 VAT") {
      assert(checker.check("640903926"))
    }

    it("should detect valid Type 4 VAT") {
      assert(checker.check("7103192745"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("46505335"))
    }
  }
}
