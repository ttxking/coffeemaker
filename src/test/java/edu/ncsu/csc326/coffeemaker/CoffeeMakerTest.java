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

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

import static org.junit.Assert.*;

/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Anusid Wachiracharoenwong
 */
public class CoffeeMakerTest {
	
	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker} 
	 * object we wish to test.
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();
		
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}
	/**
	 * Given a coffee maker with the recipe book
	 * When we add a recipe,only three recipes may be added to the coffee maker.
	 * If we add the fourth recipe the program should return False, otherwise True
	 */
	@Test
	public void testAddRecipe(){
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
	public void testAddDuplicateRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe1));
	}
	/**
	 * Given a coffee maker with the recipe book
	 * When we want to delete recipe, if it exists in the list of recipes
	 * Then it will return the name of deleted recipe, otherwise return null
	 */
	@Test
	public void testDeleteRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertEquals(recipe1.getName(),coffeeMaker.deleteRecipe(0));
		assertNull(coffeeMaker.deleteRecipe(0)); // Test already delete recipe
		assertNull(coffeeMaker.deleteRecipe(1)); // Test do not exist recipe
	}
	/**
	 * Given a coffee maker with the recipe book
	 * When we want to edit recipe, if it exists in the list of recipes
	 * Then it will return the name of successfully edited recipe, otherwise return null
	 */
	@Test
	public void testEditRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertEquals(recipe1.getName(),coffeeMaker.editRecipe(0, recipe2));
		assertNull(coffeeMaker.editRecipe(1, recipe2));
	}
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
		coffeeMaker.addInventory("0","0","0","0");
		coffeeMaker.addInventory("3","6","9","12"); // this should not fail
		coffeeMaker.addInventory("10","10","10","10");// this should not fail
	}
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
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
	  *
	  * @throws InventoryException  if there was an error parsing the quanity
	  * 		to a positive integer.
	 */
	@Test
	public void testCheckInventory() throws InventoryException {
		String defaultInventory = "Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n"; // default contain 15 of each recipe
		assertEquals(defaultInventory, coffeeMaker.checkInventory());
		coffeeMaker.addInventory("0","5","0","3");
		String updatedInventory = "Coffee: 15\nMilk: 20\nSugar: 15\nChocolate: 18\n";
		assertEquals(updatedInventory, coffeeMaker.checkInventory());
	}
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee and paying more than the coffee costs
	 * Then we get the correct change back.
	 * Otherwise, if we pay the exact price we get zero change.
	 */
	@Test
	public void testPurChaseBeverage() {
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
		assertEquals(25, coffeeMaker.makeCoffee(0,25)); // not enough amount paid
	}
	/**
	 * Given a coffee maker with default inventory
	 * When we make beverage, which doesn't have enough recipe in the inventory
	 * Then we get the money back
	 */
	@Test
	public void testPurChaseBeverageWithNotEnoughInventory() {
		coffeeMaker.addRecipe(recipe2); // add mocha which use 20 chocolate while we only have 15 chocolate
		assertEquals(75, coffeeMaker.makeCoffee(1,75)); // not enough recipe
	}

}
