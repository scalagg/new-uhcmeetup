package gg.scala.meetup.shared.loadout

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupLoadoutHandler
{
    fun setupDefaultInventory(defaultInventory: MutableMap<Int, ItemStack>)
    {
        defaultInventory.clear()
        defaultInventory[0] = ItemStack(Material.DIAMOND_SWORD)
        defaultInventory[1] = ItemStack(Material.FISHING_ROD)
        defaultInventory[2] = ItemStack(Material.BOW)
        defaultInventory[3] = ItemStack(Material.COOKED_BEEF)
        defaultInventory[4] = ItemStack(Material.GOLDEN_APPLE)
        defaultInventory[5] = ItemStack(Material.APPLE)
        defaultInventory[6] = ItemStack(Material.DIAMOND_AXE)
        defaultInventory[7] = ItemStack(Material.FLINT_AND_STEEL)
        defaultInventory[8] = ItemStack(Material.COBBLESTONE)
        defaultInventory[9] = ItemStack(Material.ARROW)
        defaultInventory[10] = ItemStack(Material.LAVA_BUCKET)
        defaultInventory[11] = ItemStack(Material.WATER_BUCKET)
        defaultInventory[14] = ItemStack(Material.DIAMOND_PICKAXE)
        defaultInventory[15] = ItemStack(Material.ENCHANTMENT_TABLE)
        defaultInventory[16] = ItemStack(Material.ANVIL)
        defaultInventory[17] = ItemStack(Material.EXP_BOTTLE)
    }
}
