package me.assil.checkvat

import org.scalatest.FunSpec

import java.io.File

import scala.io.Source
import scala.util.Random

class CheckVATSpec extends FunSpec {
  describe("CheckVAT") {
    val checker = new CheckVAT

    it("should detect valid VATs from all EU countries") {

    }

    it("should detect a VAT given an EU country code") {

    }

    it("should detect invalid VAT numbers") {

    }

    it("should make a successful check via EU database") {
      assert(checker.remoteCheck("U64938189", "AT"))
      assert(!checker.remoteCheck("U6493818", "AT"))
    }
  }
}
