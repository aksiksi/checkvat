### VAT checking logic

* Ideas
    - Single `VATNumber` class that takes an input string and performs pattern matching on its values.
    - How to abstract away the rules from the implementation
        - Switch based on length of VAT number, whether or not contains numbers and letters, etc.