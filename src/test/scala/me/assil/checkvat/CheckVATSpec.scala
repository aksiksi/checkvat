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

  describe("Germany VAT") {
    val checker = new Germany

    it("should detect valid VAT") {
      assert(checker.check("111111125"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("111111124"))
    }
  }

  describe("Denmark VAT") {
    val checker = new Denmark

    it("should detect valid VAT") {
      assert(checker.check("88146328"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("88146327"))
    }
  }

  describe("Estonia VAT") {
    val checker = new Estonia

    it("should detect valid VAT") {
      assert(checker.check("100207415"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("100207416"))
    }
  }

  describe("Greece VAT") {
    val checker = new Greece

    it("should detect valid VAT") {
      assert(checker.check("040127797"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("040127796"))
    }
  }

  describe("Finland VAT") {
    val checker = new Finland

    it("should detect valid VAT") {
      assert(checker.check("09853608"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("09853607"))
    }
  }

  describe("Hungary VAT") {
    val checker = new Hungary

    it("should detect valid VAT") {
      assert(checker.check("21376414"))
      assert(checker.check("10597190"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("10597191"))
    }
  }

  describe("Italy VAT") {
    val checker = new Italy

    it("should detect valid VAT") {
      assert(checker.check("00000010215"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("00000010214"))
    }
  }

  describe("Luxembourg VAT") {
    val checker = new Luxembourg

    it("should detect valid VAT") {
      assert(checker.check("10000356"))
    }

    it("should detect invalid VAT") {
      assert(!checker.check("10000355"))
    }
  }
}
