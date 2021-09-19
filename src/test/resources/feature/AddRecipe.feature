Feature: Add recipes to the coffee maker
  Only three recipes may be added to the CoffeeMaker.
  A recipe consists of a name, price, units of coffee, units of milk, units of sugar, and units of chocolate.
  Each recipe name must be unique in the recipe list. Price must be handled as an integer.
  A status message is printed to specify if the recipe was successfully added or not. Upon completion, the CoffeeMaker is returned to the waiting state

  Background:
    Given coffeemaker is in the waiting state

  Scenario: add 1 recipe to the coffeemaker
    When I add 1 recipe to coffeemaker
    Then coffeemaker has 1 recipe and coffeemaker return true

  Scenario: add 2 recipes to the coffeemaker
    When I add 2 recipes to coffeemaker
    Then coffeemaker has 3 recipe and coffeemaker return true

  Scenario: add 1 recipe to the coffeemaker
    When I add 1 recipe to coffeemaker
    Then coffeemaker has 3 recipe and coffeemaker return false

