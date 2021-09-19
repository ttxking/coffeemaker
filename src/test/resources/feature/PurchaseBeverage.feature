Feature: Purchase beverage from coffeemaker
  The user selects a beverage and inserts an amount of money.
  The money must be an integer. If the beverage is in the RecipeBook and the user paid enough money the beverage will be dispensed and any change will be returned.
  The user will not be able to purchase a beverage if they do not deposit enough money into the CoffeeMaker.
  A user's money will be returned if there is not enough inventory to make the beverage.
  Upon completion, the Coffee Maker displays a message about the purchase status and is returned to the main menu.

  Recipe in coffeemaker
  ------------
  latte 50 Baht
  americano 60 Baht (use 20 milk)
  ------------

  Inventory
  ==========
  coffee: 15
  milk: 15
  chocolate: 15
  sugar: 15


  Background:
    Given coffeemaker is in the waiting state

  Scenario: purchase beverage with exact payment
    When I purchase beverage that cost 50 Baht from coffeemaker
    And  I deposit 50 Baht
    Then the coffeemaker return 0 Baht

  Scenario: purchase beverage with not enough payment
    When I purchase beverage that cost 50 Baht from coffeemaker
    And  I deposit 40 Baht
    Then the coffeemaker return 40 Baht

  Scenario: purchase beverage with over payment
    When I purchase beverage that cost 50 Baht from coffeemaker
    And  I deposit 70 Baht
    Then the coffeemaker return 20 Baht

  Scenario: purchase beverage that don't have enough inventory
    When I purchase beverage that cost 60 Baht from coffeemaker
    And  I deposit 60 Baht
    Then the coffeemaker return 60 Baht

