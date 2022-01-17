package gg.scala.meetup.game.loadout

import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.meetup.game.UhcMeetupEngine
import gg.scala.meetup.game.scenario.impl.TimeBombGameScenario
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupExtendedLoadoutHandler
{
    fun handleInventory(player: Player)
    {
        val inventory = player.inventory

        val statistics = UhcMeetupEngine.INSTANCE
            .getStatistics(
                CgsPlayerHandler.find(player)!!
            )

        inventory.helmet = ItemBuilder(getRandomMaterial("helmet"))
            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
        inventory.chestplate = ItemBuilder(getRandomMaterial("chestplate"))
            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
        inventory.leggings = ItemBuilder(getRandomMaterial("leggings"))
            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
        inventory.boots = ItemBuilder(getRandomMaterial("boots"))
            .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()

        var hasAnythingDiamond = false

        for (stack in inventory.armorContents)
        {
            if (stack.type.name.startsWith("DIAMOND_"))
            {
                hasAnythingDiamond = true
                break
            }
        }

        if (!hasAnythingDiamond)
        {
            inventory.chestplate = ItemBuilder(getRandomMaterial("chestplate"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
            inventory.leggings = ItemBuilder(getRandomMaterial("leggings"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
        } else
        {
            val hasEverythingDiamond = (inventory.helmet.type.name.startsWith("DIAMOND")
                    && inventory.chestplate.type.name.startsWith("DIAMOND")
                    && inventory.leggings.type.name.startsWith("DIAMOND")
                    && inventory.boots.type.name.startsWith("DIAMOND"))
            if (hasEverythingDiamond)
            {
                inventory.helmet = ItemBuilder(Material.IRON_HELMET)
                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
                inventory.boots = ItemBuilder(Material.IRON_BOOTS)
                    .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, getLevel()).build()
            }
        }

        val sword = ItemStack(getRandomMaterial("sword"))
        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, getLevel())

        val bow = ItemStack(Material.BOW)
        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, Random.nextInt(3) + 1)

        if (Random.nextInt(10) == 7)
        {
            bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
        }
        if (Random.nextInt(100) >= 95)
        {
            bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1)
        }

        val head = TimeBombGameScenario.GOLDEN_HEAD
        val loadout = statistics.loadout

        head.amount = Random.nextInt(1, 3)

        inventory.setItem(loadout.getLocationOf(Material.DIAMOND_SWORD), sword)
        inventory.setItem(loadout.getLocationOf(Material.FISHING_ROD), ItemStack(Material.FISHING_ROD))
        inventory.setItem(loadout.getLocationOf(Material.BOW), bow)
        inventory.setItem(loadout.getLocationOf(Material.COOKED_BEEF), ItemStack(Material.COOKED_BEEF, 64))

        inventory.setItem(
            loadout.getLocationOf(Material.GOLDEN_APPLE),
            ItemStack(Material.GOLDEN_APPLE, Random.nextInt(4) + 4)
        )

        inventory.setItem(loadout.getLocationOf(Material.APPLE), head)
        inventory.setItem(loadout.getLocationOf(Material.DIAMOND_AXE), ItemStack(Material.DIAMOND_AXE))
        inventory.setItem(loadout.getLocationOf(Material.FLINT_AND_STEEL), ItemStack(Material.FLINT_AND_STEEL))
        inventory.setItem(loadout.getLocationOf(Material.COBBLESTONE), ItemStack(Material.COBBLESTONE, 64))
        inventory.setItem(loadout.getLocationOf(Material.ARROW), ItemStack(Material.ARROW, 64))

        inventory.setItem(
            loadout.getLocationOf(Material.LAVA_BUCKET),
            ItemBuilder(Material.LAVA_BUCKET).amount(1).build()
        )

        inventory.setItem(
            loadout.getLocationOf(Material.WATER_BUCKET),
            ItemBuilder(Material.WATER_BUCKET).amount(1).build()
        )

        inventory.setItem(loadout.getLocationOf(Material.DIAMOND_PICKAXE), ItemStack(Material.DIAMOND_PICKAXE))
        inventory.setItem(loadout.getLocationOf(Material.ENCHANTMENT_TABLE), ItemStack(Material.ENCHANTMENT_TABLE))
        inventory.setItem(loadout.getLocationOf(Material.ANVIL), ItemStack(Material.ANVIL, Random.nextInt(2) + 1))
        inventory.setItem(loadout.getLocationOf(Material.EXP_BOTTLE), ItemStack(Material.EXP_BOTTLE, 64))

        inventory.setItem(inventory.firstEmpty(), ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
        inventory.setItem(inventory.firstEmpty(), ItemBuilder(Material.WATER_BUCKET).amount(1).build())

        player.updateInventory()
    }

    private fun getRandomMaterial(type: String): Material
    {
        val random = Random.nextInt(100)

        return when (type)
        {
            "helmet" -> if (random >= 50) Material.DIAMOND_HELMET else Material.IRON_HELMET
            "chestplate" -> if (random >= 60) Material.DIAMOND_CHESTPLATE else Material.IRON_CHESTPLATE
            "leggings" -> if (random >= 60) Material.DIAMOND_LEGGINGS else Material.IRON_LEGGINGS
            "boots" -> if (random >= 50) Material.DIAMOND_BOOTS else Material.IRON_BOOTS
            "sword" -> if (random >= 50) Material.DIAMOND_SWORD else Material.IRON_SWORD
            else -> Material.GRASS
        }
    }

    private fun getLevel(): Int
    {
        val random = Random.nextInt(100)
        return if (random >= 65) 3 else if (random >= 35) 2 else 1
    }
}
