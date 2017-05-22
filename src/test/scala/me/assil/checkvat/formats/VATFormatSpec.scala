package me.assil.checkvat.formats

import org.scalatest.FunSuite

class VATFormatSpec extends FunSuite {
  test("Austria VAT") {
    val checker = new Austria

    assert(checker.check("U10223006"))
    assert(!checker.check("U10223007"))
  }

  test("Belgium VAT") {
    val checker = new Belgium

    assert(checker.check("0776091951"))
    assert(!checker.check("1776091951"))
  }

  test("Bulgaria VAT") {
    val checker = new Bulgaria

    // Type 1
    assert(checker.check("101004508"))

    // Type 2
    assert(checker.check("0041010002"))

    // Type 3
    assert(checker.check("0000100159"))

    // Type 4
    assert(checker.check("0000100153"))
  }

  test("Cyprus VAT") {
    val checker = new Cyprus

    assert(checker.check("00532445O"))
    assert(!checker.check("00532445J"))
  }

  test("Czech VAT") {
    val checker = new Czech

    // Type 1
    assert(checker.check("46505334"))

    // Type 2
    assert(checker.check("395601439"))

    // Type 3
    assert(checker.check("640903926"))

    // Type 4
    assert(checker.check("7103192745"))

    // Invalid
    assert(!checker.check("46505335"))
  }

  test("Germany VAT") {
    val checker = new Germany

    assert(checker.check("111111125"))
    assert(!checker.check("111111124"))
  }

  test("Denmark VAT") {
    val checker = new Denmark

    assert(checker.check("88146328"))
    assert(!checker.check("88146327"))
  }

  test("Estonia VAT") {
    val checker = new Estonia

    assert(checker.check("100207415"))
    assert(!checker.check("100207416"))
  }

  test("Greece VAT") {
    val checker = new Greece

    assert(checker.check("040127797"))
    assert(!checker.check("040127796"))
  }

  test("Finland VAT") {
    val checker = new Finland

    assert(checker.check("09853608"))
    assert(!checker.check("09853607"))
  }

  test("Hungary VAT") {
    val checker = new Hungary

    assert(checker.check("21376414"))
    assert(checker.check("10597190"))
    assert(!checker.check("10597191"))
  }

  test("Italy VAT") {
    val checker = new Italy

    assert(checker.check("00000010215"))
    assert(!checker.check("00000010214"))
  }

  test("Luxembourg VAT") {
    val checker = new Luxembourg

    assert(checker.check("10000356"))
    assert(!checker.check("10000355"))
  }

  test("Malta VAT") {
    val checker = new Malta

    assert(checker.check("15121333"))
    assert(!checker.check("15121334"))
  }

  test("Netherlands VAT") {
    val checker = new Netherlands

    assert(checker.check("010000446B01"))
    assert(!checker.check("010000445B01"))
    assert(!checker.check("010000446B00"))
  }

  test("Poland VAT") {
    val checker = new Poland

    assert(checker.check("5260001246"))
    assert(!checker.check("5260001245"))
  }

  test("Portugal VAT") {
    val checker = new Portugal

    assert(checker.check("502757191"))
    assert(!checker.check("502757190"))
    assert(!checker.check("002757191"))
  }

  test("Romania VAT") {
    val checker = new Romania

    assert(checker.check("11198699"))
    assert(checker.check("99908"))
    assert(!checker.check("999087"))
  }

  test("Sweden VAT") {
    val checker = new Sweden

    assert(checker.check("556188840401"))
    assert(!checker.check("556188840501"))
    assert(!checker.check("556188840495"))
  }

  test("Slovenia VAT") {
    val checker = new Slovenia

    assert(checker.check("15012557"))
    assert(!checker.check("15012556"))
  }

  test("Slovakia VAT") {
    val checker = new Slovakia

    assert(checker.check("4030000007"))
    assert(!checker.check("5407062531"))
  }

  test("France VAT") {
    val checker = new France

    // Old style
    assert(checker.check("00300076965"))
    assert(checker.check("03552081317"))
    assert(checker.check("55440243988"))
    assert(checker.check("47323875187"))

    // New style
    assert(!checker.check("A0123456789"))
  }

  test("United Kingdom VAT") {
    val checker = new UnitedKingdom

    // Format 1
    assert(checker.check("GD103"))
    assert(!checker.check("GA103"))

    // Format 2
    assert(checker.check("434031494"))
    assert(checker.check("211527742"))
    assert(checker.check("004123443"))
    assert(!checker.check("970000073"))
    assert(!checker.check("999000690001"))
  }

  test("Ireland VAT") {
    val checker = new Ireland

    // Format 1
    assert(checker.check("8Z49289F"))
    assert(!checker.check("8Z49289G"))

    // Format 2
    assert(checker.check("3628739L"))
  }

  test("Spain VAT") {
    val checker = new Spain

    // Format 1
    assert(checker.check("A0011012B"))
    assert(checker.check("W7815395D"))
    assert(!checker.check("A0011012D"))

    // Format 2
    assert(checker.check("00000002W"))

    // Format 3
    assert(checker.check("A31000268"))
  }

  test("Latvia VAT") {
    val checker = new Latvia

    // Format 1
    assert(checker.check("40003009497"))

    // Format 2
    assert(checker.check("07091910933"))
    assert(!checker.check("64091910933")) // Invalid date
  }

  test("Lithuania VAT") {
    val checker = new Lithuania

    // Format 1
    assert(checker.check("213179412"))

    // Format 2
    assert(checker.check("290061371314"))
  }
}
