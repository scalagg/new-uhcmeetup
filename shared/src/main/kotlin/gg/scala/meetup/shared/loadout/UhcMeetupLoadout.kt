package gg.scala.meetup.shared.loadout

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class UhcMeetupLoadout
{
    val itemStacks = mutableMapOf<Int, ItemStack>()

    fun resetAndApplyDefault()
    {
        UhcMeetupLoadoutHandler.setupDefaultInventory(itemStacks)
    }

    fun getLocationOf(material: Material): Int
    {
        return itemStacks.entries.firstOrNull {
            it.value.type == material
        }?.key ?: -1
    }
}
