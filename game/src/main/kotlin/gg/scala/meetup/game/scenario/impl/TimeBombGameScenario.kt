package gg.scala.meetup.game.scenario.impl

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.commons.annotations.Listeners
import gg.scala.meetup.game.scenario.GameScenario
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
@Listeners
object TimeBombGameScenario : GameScenario, Listener
{
    @JvmStatic
    val GOLDEN_HEAD = ItemBuilder(Material.GOLDEN_APPLE)
        .data(0)
        .name("&6&lGolden Head")
        .addToLore(
            "&7Consume this special apple",
            "&7to receive health boosting effects!"
        )
        .build()

    fun startTimeBomb(
        player: Player,
        items: List<ItemStack?>
    )
    {
        val currentLocation = player.location
        currentLocation.block.type = Material.CHEST

        val chest = currentLocation.block.state as Chest
        currentLocation.add(1.0, 0.0, 0.0).block.type = Material.CHEST
        currentLocation.add(0.0, 1.0, 0.0).block.type = Material.AIR
        currentLocation.add(1.0, 1.0, 0.0).block.type = Material.AIR

        items.filter {
            it != null && it.type != Material.AIR
        }.forEach {
            chest.blockInventory.addItem(it)
        }

        chest.inventory.addItem(GOLDEN_HEAD)

        TimeBombGameScenarioTimer(
            chest, currentLocation, player
        )
    }

    class TimeBombGameScenarioTimer(
        private val chest: Chest,
        private val location: Location,
        private val player: Player
    ) : BukkitRunnable()
    {
        private var ticks = 31

        init
        {
            runTaskTimer(CgsGameEngine.INSTANCE.plugin, 0L, 20L)
        }

        override fun run()
        {
            ticks--

            if (ticks == 0)
            {
                cancel()

                chest.inventory.clear()
                chest.block.type = Material.AIR
                chest.update()

                location.world.createExplosion(
                    location, 3.0F
                )

                CgsGameEngine.INSTANCE
                    .sendMessage("${player.name}'s ${CC.SEC}corpse has exploded!")
            }
        }
    }
}
