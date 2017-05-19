package me.assil.checkvat.formats

/**
  * Created by aksiksi on 5/18/17.
  */
class Czech extends VATFormat {
  // TODO
  override val country = "CZ"
  override val lengths = List(9)
  override val alpha = List(8)

  override def check(vat: String): Boolean = {
    false
  }
}

