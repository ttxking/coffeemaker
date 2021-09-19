package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class CucumberTest {
    boolean status;
    Integer amtDeposit;
    Inventory inventory;
    private CoffeeMaker coffeeMaker;

    @Given("coffeemaker is in the waiting state")
    public void coffeemakerIsInTheWaitingState() {
        coffeeMaker = new CoffeeMaker();
        inventory = new Inventory();
    }

    @When("I add {int} recipe(s) to coffeemaker")
    public void iAddRecipeToCoffeemaker(Integer int1) {
        for (int i = 1; i <= int1; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Coffee ver " + (i));
            status = coffeeMaker.addRecipe(recipe);
        }
    }


    @Then("coffeemaker has {int} recipe and coffeemaker return {word}")
    public void coffeemakerHasRecipeAndCoffeemakerReturnTrue(int int1, String word) {
        int numOfRecipe = 0;
        for (Recipe recipe : coffeeMaker.getRecipes()) {
            if (recipe == null || recipe.getClass() != Recipe.class) return;
            numOfRecipe++;
        }
        assertEquals(numOfRecipe, int1);
        assertEquals(status, Boolean.parseBoolean(word));
    }

    @When("I purchase beverage that cost {word} Baht from coffeemaker")
    public void iPurchaseBeverageThatCostBahtFromCoffeemaker(String price) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setPrice(price);
        if (price.equals("60")) {
            recipe.setAmtCoffee("10");
            recipe.setAmtMilk("20");
            recipe.setAmtChocolate("0");
            recipe.setAmtSugar("0");
        }
        coffeeMaker.addRecipe(recipe);
    }

    @And("I deposit {int} Baht")
    public void iDepositBaht(int deposit) {
        amtDeposit = deposit;
    }

    @Then("the coffeemaker return {int} Baht")
    public void theCoffeemakerReturnBaht(int change) {
        assertEquals(change, coffeeMaker.makeCoffee(0, amtDeposit));
    }

    @When("I add {word} {word} to inventory")
    public void AddToInventory(String amt, String ingredient) throws InventoryException {
        switch (ingredient) {
            case "coffee":
                inventory.addCoffee(amt);
                break;
            case "milk":
                inventory.addMilk(amt);
                break;
            case "chocolate":
                inventory.addChocolate(amt);
                break;
            case "sugar":
                inventory.addSugar(amt);
                break;
        }
    }

    @Then("inventory contains {int} {word}")
    public void inventoryContains(int amt, String ingredient) {
        switch (ingredient) {
            case "coffee":
                assertEquals(amt, inventory.getCoffee());
                break;
            case "milk":
                assertEquals(amt, inventory.getMilk());
                break;
            case "chocolate":
                assertEquals(amt, inventory.getChocolate());
                break;
            case "sugar":
                assertEquals(amt, inventory.getSugar());
                break;
        }
    }
}
