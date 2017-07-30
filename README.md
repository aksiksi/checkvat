# CheckVAT

A Scala library that checks for the validity of a given EU VAT as defined by VIES.

## Usage

Below is a fully working Scala snippet that demonstrates how to use the library:

```
import me.assil.checkvat.CheckVAT

class Main extends App {
  // Create new instance for checking
  val checker = new CheckVAT
  
  // Let's check a Swedish VAT number
  val c1 = checker.check("556188840401", "SE")
  
  // We can also check the same number without specifying the ISO country code
  val c2 = checker.check("556188840401")
  
  assert(c1 == c2 == true)
}
```

## Build

Install the latest version of `sbt`, then run: `sbt compile`.

## Test

Simply run: `sbt test`.
