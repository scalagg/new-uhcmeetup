package gg.scala.meetup.game.listener

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.CgsGameState
import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.meetup.game.UhcMeetupEngine
import gg.scala.meetup.game.handler.BorderHandler
import gg.scala.meetup.game.loadout.UhcMeetupExtendedLoadoutHandler
import gg.scala.meetup.game.runnable.UhcMeetupBorderRunnable
import gg.scala.meetup.game.scenario.impl.TimeBombGameScenario
import gg.scala.meetup.game.sit
import gg.scala.meetup.game.teleportToRandomLocationWithinArena
import net.evilblock.cubed.util.CC
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupListener : Listener
{
    val killsTracker = mutableMapOf<UUID, Int>()

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent)
    {
        if (event.block.type == Material.CHEST)
        {
            if (UhcMeetupEngine.INSTANCE.gameState.isAfter(CgsGameState.STARTED))
            {
                event.isCancelled = true
                event.player.sendMessage("${CC.RED}You cannot break chests when they are in time bomb mode.")
            }
        }
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent)
    {
        val gameWorld = Bukkit.getWorld("meetup")

        if (event.block.location.y >= gameWorld.getHighestBlockYAt(0, 0) + 30)
        {
            event.player.sendMessage("${CC.RED}You cannot place blocks higher than this point.")
            event.isCancelled = true
            return
        }
    }

    @JvmStatic
    val REGENERATION = PotionEffect(PotionEffectType.REGENERATION, 200, 1)

    @EventHandler
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent)
    {
        if (event.item != null && event.item.isSimilar(TimeBombGameScenario.GOLDEN_HEAD))
        {
            val player = event.player
            player.removePotionEffect(PotionEffectType.REGENERATION)
            player.addPotionEffect(REGENERATION)
        }
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageEvent)
    {
        if (event.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.entity is Player)
        {
            val statistics = UhcMeetupEngine.INSTANCE
                .getStatistics(
                    CgsPlayerHandler.find(event.entity as Player)!!
                )

            if (statistics.noCleanTimer != null)
            {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onGameStarting(
        event: CgsGameEngine.CgsGamePreStartEvent
    )
    {
        for (onlinePlayer in Bukkit.getOnlinePlayers())
        {
            onlinePlayer.teleportToRandomLocationWithinArena()
            onlinePlayer sit true

            UhcMeetupExtendedLoadoutHandler
                .handleInventory(onlinePlayer)
        }
    }

    @EventHandler
    fun onGameStart(
        event: CgsGameEngine.CgsGameStartEvent
    )
    {
        for (onlinePlayer in Bukkit.getOnlinePlayers())
        {
            onlinePlayer sit false

            killsTracker[onlinePlayer.uniqueId] = 0
        }

        UhcMeetupBorderRunnable.initialLoad()
    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    fun onParticipantConnect(
        event: CgsGameEngine.CgsGameParticipantConnectEvent
    )
    {
        if (CgsGameEngine.INSTANCE.gameState == CgsGameState.STARTING)
        {
            event.participant.teleportToRandomLocationWithinArena()
            event.participant sit true

            UhcMeetupExtendedLoadoutHandler
                .handleInventory(event.participant)
        }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST
    )
    fun onPlayerDeath(
        event: PlayerDeathEvent
    )
    {
        val items = mutableListOf<ItemStack>()

        mutableListOf(
            event.entity.inventory.armorContents,
            event.entity.inventory.contents
        ).forEach { itemStacks ->
            itemStacks.filter {
                it != null && it.type != Material.AIR
            }.forEach {
                items.add(it)
            }
        }

        if (event.entity.killer != null)
        {
            killsTracker.compute(event.entity.killer.uniqueId) { _, kills ->
                kills?.plus(1)
            }
        }

        event.drops.clear()
        event.droppedExp = 0

        TimeBombGameScenario.startTimeBomb(
            player = event.entity,
            items = items
        )
    }
}
