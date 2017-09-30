package com.gabrieljadderson.nightplanetgame.player;

import com.gabrieljadderson.nightplanetgame.map.items.ItemObject;
import com.gabrieljadderson.nightplanetgame.utils.MaxSizeHashMap;

/**
 * @author Gabriel Jadderson
 */
public class Inventory
{
	
	/**
	 * inventory max size of {@code  15}
	 */
	final int INVENTORY_MAXSIZE = 15;
	/**
	 * inventory stack size. not implemented yet::: TODO: fix this.
	 */
	final int MAX_ITEM_STACK_SIZE = 0000000; //not used for now. TODO: implement item stacks and sizes.
	/**
	 * inventory map with {@code <ItemObject, Integer>}
	 */
	final MaxSizeHashMap<ItemObject, Integer> inventory;
	
	public Inventory()
	{
		inventory = new MaxSizeHashMap<ItemObject, Integer>(INVENTORY_MAXSIZE);
	}
	
	/**
	 * adds an item to the inventory, if the item already exists the amount is added to that item.
	 *
	 * @param item   - {@code ItemObject} to be added.
	 * @param amount - {@code Integer} amount of items
	 */
	public void addItem(ItemObject item, int amount)
	{
		if (!inventory.containsKey(item))
		{
			//if does not contain put new item with x-amount. 
			inventory.put(item, amount);
		} else
		{
			//if contains item, get the amount and increment by the new amount invoked. 
			inventory.put(item, inventory.get(item) + amount);
		}
	}
	
	/**
	 * removes an item from the inventory completely and deletes the whole stack amount.
	 *
	 * @param id
	 */
	public void removeItem(ItemObject item)
	{
		removeItem(item, -1);
	}
	
	/**
	 * removes x-amount of item from an inventory item.
	 *
	 * @param item
	 * @param amount
	 */
	public void removeItem(ItemObject item, int amount)
	{
		if (inventory.containsKey(item))
		{
			if (amount != -1)
			{
				inventory.put(item, inventory.get(item) - amount);
			} else
			{
				inventory.remove(item);
			}
		}
	}
	
	/**
	 * returns the inventory hashmap.
	 *
	 * @return inventory
	 */
	public MaxSizeHashMap<ItemObject, Integer> getInventory()
	{
		return inventory;
	}
	
	/**
	 * gets the amount of items in the inventory.
	 *
	 * @return the amount of items contained in the inventory.
	 */
	public int getInventorySize()
	{
		return inventory.size();
	}
	
	/**
	 * returns the amount of free inventory space left.
	 *
	 * @return
	 */
	public int getFreeSpace()
	{
		return INVENTORY_MAXSIZE - inventory.size();
	}
	
	/**
	 * checks if the inventory is full.
	 *
	 * @return true if the inventory is full, false otherwise.
	 */
	public boolean isInventoryFull()
	{
		if (inventory.size() == INVENTORY_MAXSIZE)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	/**
	 * clears the inventory completely.
	 */
	public void clearInventory()
	{
		inventory.clear();
	}
	
}
