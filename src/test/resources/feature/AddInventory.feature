Feature: Add inventory to the coffeemaker
  Inventory may be added to the machine at any time from the main menu, and is added to the current inventory in the CoffeeMaker.
  The types of inventory in the CoffeeMaker are coffee, milk, sugar, and chocolate.
  The inventory is measured in integer units. Inventory may only be removed from the CoffeeMaker by purchasing a beverage.
  Upon completion, a status message is printed and the CoffeeMaker is returned to the waiting state.

  Inventory
  ==========
  coffee: 15
  milk: 15
  chocolate: 15
  sugar: 15

  Background:
    Given coffeemaker is in the waiting state

  Scenario: add coffee to inventory
    When I add 20 coffee to inventory
    Then inventory contains 35 coffee

  Scenario: add milk to inventory
    When I add 20 milk to inventory
    Then inventory contains 35 milk

  Scenario: add chocolate to inventory
    When I add 20 chocolate to inventory
    Then inventory contains 35 chocolate

  Scenario: add sugar to inventory, this should fail because this version of coffeemaker contains error adding sugar
    When I add 20 sugar to inventory
    Then inventory contains 35 sugar
