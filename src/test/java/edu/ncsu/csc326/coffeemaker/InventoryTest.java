package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    private Inventory inventory;


    @Before
    public void setUp() throws RecipeException {
        inventory = new Inventory();
    }

    /**
     * Given a default inventory
     * When we want to set chocolate value, if the amount is >= 0
     * Then it will return the new amount, otherwise no change
     */
    @Test
    public void testSetChocolate() {
        inventory.setChocolate(0);
        assertEquals(0, inventory.getChocolate());
        inventory.setChocolate(30);
        assertEquals(30, inventory.getChocolate());
        inventory.setChocolate(-15);
        assertEquals(30, inventory.getChocolate());
    }

    /**
     * Given a default inventory
     * When we want to add chocolate value, if the amount is not a number
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddChocolateWithNonInteger() throws InventoryException {
        inventory.addChocolate("choco");
    }

    /**
     * Given a default inventory
     * When we want to add chocolate value, if the amount is a negative integer
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddChocolateWithNegativeInteger() throws InventoryException {
        inventory.addChocolate("-1");
    }

    /**
     * Given a default inventory
     * When we want to set coffee value, if the amount is >= 0
     * Then it will return the new amount, otherwise no change
     */
    @Test
    public void testSetCoffee() {
        inventory.setCoffee(0);
        assertEquals(0, inventory.getCoffee());
        inventory.setCoffee(30);
        assertEquals(30, inventory.getCoffee());
        inventory.setCoffee(-15);
        assertEquals(30, inventory.getCoffee());
    }

    /**
     * Given a default inventory
     * When we want to add coffee value, if the amount is not a number
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddCoffeeWithNonInteger() throws InventoryException {
        inventory.addCoffee("coffee");
    }

    /**
     * Given a default inventory
     * When we want to add coffee value, if the amount is a negative integer
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddCoffeeWithNegativeInteger() throws InventoryException {
        inventory.addCoffee("-1");
    }

    /**
     * Given a default inventory
     * When we want to set milk value, if the amount is >= 0
     * Then it will return the new amount, otherwise no change
     */
    @Test
    public void testSetMilk() {
        inventory.setMilk(0);
        assertEquals(0, inventory.getMilk());
        inventory.setMilk(30);
        assertEquals(30, inventory.getMilk());
        inventory.setMilk(-15);
        assertEquals(30, inventory.getMilk());
    }

    /**
     * Given a default inventory
     * When we want to add milk value, if the amount is not a number
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddMilkWithNonInteger() throws InventoryException {
        inventory.addMilk("milk");
    }

    /**
     * Given a default inventory
     * When we want to add milk value, if the amount is a negative integer
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddMilkWithNegativeInteger() throws InventoryException {
        inventory.addMilk("-1");
    }

    /**
     * Given a default inventory
     * When we want to set sugar value, if the amount is >= 0
     * Then it will return the new amount, otherwise no change
     */
    @Test
    public void testSetSugar() {
        inventory.setSugar(0);
        assertEquals(0, inventory.getSugar());
        inventory.setSugar(30);
        assertEquals(30, inventory.getSugar());
        inventory.setSugar(-15);
        assertEquals(30, inventory.getSugar());
    }

    /**
     * Given a default inventory
     * When we want to add sugar value, if the amount is not a number
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddSugarWithNonInteger() throws InventoryException {
        inventory.addSugar("milk");
    }

    /**
     * Given a default inventory
     * When we want to add sugar value, if the amount is a negative integer
     *
     * @throws InventoryException if there was an error parsing the quantity
     *                            to a positive integer.
     */
    @Test(expected = InventoryException.class)
    public void testAddSugarWithNegativeInteger() throws InventoryException {
        inventory.addSugar("-1");
    }

    /**
     * Given a default special recipe which its amount of ingredient used
     * exceed number of ingredient in the inventory
     * When we check if there is enough ingredients
     * Then it returns True when there is, otherwise False.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         *                   amount when setting up the recipe.
     */
    @Test
    public void testEnoughIngredients() throws RecipeException {
        Recipe specialRecipe = new Recipe();
        assertTrue(inventory.enoughIngredients(specialRecipe)); // every thing should be 0 as default
        specialRecipe.setAmtChocolate("20");
        assertFalse(inventory.enoughIngredients(specialRecipe));
        specialRecipe.setAmtChocolate("0"); // set it to zero to test specific ingredients
        specialRecipe.setAmtCoffee("20");
        assertFalse(inventory.enoughIngredients(specialRecipe));
        specialRecipe.setAmtCoffee("0"); // set it to zero to test specific ingredients
        specialRecipe.setAmtMilk("20");
        assertFalse(inventory.enoughIngredients(specialRecipe));
        specialRecipe.setAmtMilk("0"); // set it to zero to test specific ingredients
        specialRecipe.setAmtSugar("20");
        assertFalse(inventory.enoughIngredients(specialRecipe));
    }

}
