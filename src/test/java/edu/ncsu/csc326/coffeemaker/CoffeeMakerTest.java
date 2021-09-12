/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Anusid Wachiracharoenwong
 */
public class CoffeeMakerTest {

    /**
     * The object under test
     */
    private CoffeeMaker coffeeMaker;

    /**
     * CoffeeMaker with mock RecipeBook
     */
    private CoffeeMaker coffeeMaker2;
    // Sample recipes to use in testing.
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;

    private RecipeBook recipeBook;
    private Recipe[] recipes;

    /**
     * Method to create recipes with name, chocolate, coffee, milk, sugar
     * and price.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    public Recipe createRecipe(String name, String chocolate, String coffee, String milk, String sugar, String price) throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setAmtChocolate(chocolate);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setPrice(price);
        return recipe;
    }

    /**
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for coffee
        recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");

        //Set up for mocha
        recipe2 = createRecipe("Mocha", "20", "3", "1", "1", "75");

        //Set up for latte
        recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");

        //Set up for hot chocolate
        recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");

        recipeBook = mock(RecipeBook.class);

        recipes = new Recipe[]{recipe1, recipe2, recipe3, recipe4};

        coffeeMaker2 = new CoffeeMaker(recipeBook, new Inventory());


    }

    /**
     * Given a coffee maker with the recipe book
     * When we add a recipe,only three recipes may be added to the coffee maker.
     * If we add the fourth recipe the program should return False, otherwise True
     */
    @Test
    public void testAddRecipe() {
        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe2));
        assertTrue(coffeeMaker.addRecipe(recipe3));
        assertFalse(coffeeMaker.addRecipe(recipe4)); // this should pass, Only three recipes may be added to the CoffeeMaker.
    }

    /**
     * Given a coffee maker with the recipe book
     * When we add a recipe, each recipe name must be unique in the recipe list
     * If we add the recipe with duplicate name the program should return False, otherwise True
     */
    @Test
    public void testAddDuplicateRecipe() {
        coffeeMaker.addRecipe(recipe1);
        assertFalse(coffeeMaker.addRecipe(recipe1));
    }

    /**
     * Given a coffee maker with the recipe book
     * When we want to delete recipe
     * Then it will return the name of deleted recipe
     */
    @Test
    public void testDeleteRecipe() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1.getName(), coffeeMaker.deleteRecipe(0));
        assertNull(coffeeMaker.deleteRecipe(0)); // Test already delete recipe
    }

    /**
     * Given a coffee maker without the recipe book
     * When we want to delete recipe that doesn't exist
     * It will return null
     */
    @Test
    public void testDeleteNonExistRecipe() {
        assertNull(coffeeMaker.deleteRecipe(1)); // Test do not exist recipe
    }

    /**
     * Given a coffee maker with the recipe book
     * When we want to edit recipe
     * Then it will return the name of successfully edited recipe
     */
    @Test
    public void testEditRecipe() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1.getName(), coffeeMaker.editRecipe(0, recipe2));
    }

    /**
     * Given a coffee maker with the recipe book
     * When we want to edit recipe the doesn't exist in coffee maker
     * Then it will return null
     */
    @Test
    public void testEditNonExistRecipe() {
        coffeeMaker.addRecipe(recipe1);
        assertNull(coffeeMaker.editRecipe(1, recipe2));
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with well-formed quantities
     * Then we do not get an exception trying to read the inventory quantities.
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test
    public void testAddInventory() throws InventoryException {
        coffeeMaker.addInventory("4", "7", "0", "9");
        coffeeMaker.addInventory("0", "0", "0", "0");
        coffeeMaker.addInventory("3", "6", "9", "12"); // this should not fail
        coffeeMaker.addInventory("10", "10", "10", "10");// this should not fail
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with malformed quantities (i.e., a negative
     * quantity and a non-numeric string)
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddInventoryException() throws InventoryException {
        coffeeMaker.addInventory("4", "-1", "asdf", "3");
        coffeeMaker.addInventory("coffee", "milk", "sugar", "choco");

    }

    /**
     * Given a coffee maker with the default inventory
     * When we check inventory
     * Then the units of each item in the inventory are displayed.
     */
    @Test
    public void testCheckInventory() {
        String defaultInventory = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n"; // default contain 15 of each recipe
        assertEquals(defaultInventory, coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with the default inventory
     * When we updated the coffee inventory
     * Then the units of coffee in the inventory should be updated to the correct amount.
     *
     * @throws InventoryException if there was an error parsing the quanity
     *                            to a positive integer.
     */
    @Test
    public void testUpdateCoffeeInventory() throws InventoryException {
        coffeeMaker.addInventory("5", "0", "0", "0");
        String updatedInventory = "Coffee: 20\nMilk: 15\nSugar: 15\nChocolate: 15\n";
        assertEquals(updatedInventory, coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with the default inventory
     * When we updated the milk inventory
     * Then the units of milk in the inventory should be updated to the correct amount.
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test
    public void testUpdateMilkInventory() throws InventoryException {
        coffeeMaker.addInventory("0", "5", "0", "0");
        String updatedInventory = "Coffee: 15\nMilk: 20\nSugar: 15\nChocolate: 15\n";
        assertEquals(updatedInventory, coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with the default inventory
     * When we updated the sugar inventory
     * Then the units of sugar in the inventory should be updated to the correct amount.
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test
    public void testUpdateSugarInventory() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "5", "0");
        String updatedInventory = "Coffee: 15\nMilk: 15\nSugar: 20\nChocolate: 15\n";
        assertEquals(updatedInventory, coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with the default inventory
     * When we updated the chocolate inventory
     * Then the units of chocolate in the inventory should be updated to the correct amount.
     *
     * @throws InventoryException if there was an error parsing the quanity
     *                            to a positive integer.
     */
    @Test
    public void testUpdateChocolateInventory() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "5");
        String updatedInventory = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 20\n";
        assertEquals(updatedInventory, coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee and paying more than the coffee costs
     * Then we get the correct change back.
     * Otherwise, if we pay the exact price we get zero change.
     */
    @Test
    public void testPurchaseBeverage() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(25, coffeeMaker.makeCoffee(0, 75));
        assertEquals(0, coffeeMaker.makeCoffee(0, 50));
    }

    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, paying less than the coffee costs
     * Then we get the money back
     */
    @Test
    public void testPurChaseBeverageWithNotEnoughMoney() {
        coffeeMaker.addRecipe(recipe1); // cost 50
        assertEquals(25, coffeeMaker.makeCoffee(0, 25)); // not enough amount paid
    }

    /**
     * Given a coffee maker with default inventory
     * When we make beverage, which doesn't have enough ingredients in the inventory
     * Then we get the money back
     */
    @Test
    public void testPurChaseBeverageWithNotEnoughInventory() {
        coffeeMaker.addRecipe(recipe2); // add mocha which use 20 chocolate while we only have 15 chocolate
        assertEquals(75, coffeeMaker.makeCoffee(0, 75)); // not enough recipe
    }

    /**
     * Given a coffee maker with default inventory
     * When we make beverage, which doesn't have the recipe in the inventory
     * Then we get the money back
     */
    @Test
    public void testPurChaseBeverageWithNonValidRecipe() {
        coffeeMaker.addRecipe(recipe3); // add mocha which use 20 chocolate while we only have 15 chocolate
        assertEquals(75, coffeeMaker.makeCoffee(1, 75)); // non valid recipe
    }

    /**
     * Test that the RecipeBook was initialized (mocked)
     * and some methods in the class.
     */
    @Test
    public void testInitializedMockRecipeBook() {
        // test that recipe book was really initialized (mocked)
        assertNotNull(recipeBook);
        // a real recipe book would add recipe and return true
        assertFalse(recipeBook.addRecipe(recipe1));
        // methods that return an object reference will return nul
        assertNull(recipeBook.getRecipes());
    }

    /**
     * Verify that mock RecipeBook is call at least 4 four time for
     * a successful purchase.
     */
    @Test
    public void PurchaseBeverageWithMock() {
        when(recipeBook.getRecipes()).thenReturn(recipes);
        // recipe1 price is 50
        assertEquals(0, coffeeMaker2.makeCoffee(0, 50));
        // for a successful purchase in the CoffeeMaker gerRecipes() is called four time at least
        verify(recipeBook, atMost(4)).getRecipes();
    }

    @Test
    public void testPurChaseBeverageWithNotEnoughMoneyMock() {
        when(recipeBook.getRecipes()).thenReturn(recipes);
        // recipe1 price is 50
        assertEquals(25, coffeeMaker2.makeCoffee(0, 25));
        // for unsuccessful purchase in the CoffeeMaker gerRecipes() is called at least once
        verify(recipeBook, atLeastOnce()).getRecipes();
    }

    @Test
    public void testPurChaseBeverageWithNotEnoughInventoryMock() {
        when(recipeBook.getRecipes()).thenReturn(recipes);
        // recipe2 used 20 chocolate, but the default inventory contains only 15
        assertEquals(75, coffeeMaker2.makeCoffee(0, 75));
        // for unsuccessful purchase in the CoffeeMaker gerRecipes() is called at least once
        verify(recipeBook, atLeastOnce()).getRecipes();
    }

    @Test
    public void testPurChaseBeverageWithNonValidRecipeMock() {
        // we simulate the out of range as null which is default in recipeBook
        when(recipeBook.getRecipes()).thenReturn(new Recipe[]{recipe1, recipe2, recipe3, recipe4, null});
        // CoffeeMaker only contains 4 recipe, but we want to purchase the 5th
        assertEquals(50, coffeeMaker2.makeCoffee(4, 50));
        // for unsuccessful purchase in the CoffeeMaker gerRecipes() is called at least once
        verify(recipeBook, atMostOnce()).getRecipes();
    }

    @Test
    public void testNumberOfGetAmountMethodCalled() {
        // recipe that is being used
        recipes[0] = mock(Recipe.class);
        // another recipe that is not being used
        recipes[1] = mock(Recipe.class);
        when(recipeBook.getRecipes()).thenReturn(recipes);
        // make coffee with recipe 1
        coffeeMaker2.makeCoffee(0, 50);

        // the recipe that being used
        verify(recipes[0], atLeastOnce()).getAmtChocolate();
        verify(recipes[0], atLeastOnce()).getAmtCoffee();
        verify(recipes[0], atLeastOnce()).getAmtMilk();
        verify(recipes[0], atLeastOnce()).getAmtSugar();
        verify(recipes[0], atLeastOnce()).getPrice();

        // the recipe1 will never be called
        verify(recipes[1], never()).getAmtChocolate();
        verify(recipes[1], never()).getAmtCoffee();
        verify(recipes[1], never()).getAmtMilk();
        verify(recipes[1], never()).getAmtSugar();
        verify(recipes[1], never()).getPrice();

    }

}
