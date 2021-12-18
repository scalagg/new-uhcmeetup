package gg.scala.meetup.game

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.information.arena.CgsGameArenaHandler
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.lemon.Lemon
import gg.scala.meetup.game.handler.BorderHandler
import gg.scala.meetup.game.listener.UhcMeetupListener
import gg.scala.meetup.game.runnable.UhcMeetupWorldGenerationRunnable
import gg.scala.meetup.game.scenario.impl.NoCleanGameScenario
import gg.scala.meetup.game.scenario.impl.TimeBombGameScenario
import gg.scala.meetup.shared.UhcMeetupCgsInfo
import gg.scala.meetup.shared.UhcMeetupCgsStatistics
import gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode
import me.lucko.helper.Events
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.Bukkit

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class UhcMeetupGame : ExtendedScalaPlugin()
{
    override fun enable()
    {
        UhcMeetupWorldGenerationRunnable.initialLoad()

        CgsGameArenaHandler.initialLoad(
            UhcMeetupSoloGameMode
        )

        val engine = UhcMeetupEngine(
            this, UhcMeetupCgsInfo,
            UhcMeetupSoloGameMode
        )
        engine.statisticType = UhcMeetupCgsStatistics::class
        engine.initialLoad()

        server.pluginManager.registerEvents(
            UhcMeetupListener, this
        )

        listOf(TimeBombGameScenario, NoCleanGameScenario)
            .forEach { scenario ->
                scenario.getListeners().forEach {
                    server.pluginManager.registerEvents(
                        it, this
                    )
                }
            }

        UhcMeetupEngine.INSTANCE = engine
        CgsGameArenaHandler.world = Bukkit.getWorld("meetup")

        // TEMPORARY
        Tasks.asyncTimer({
            Lemon.instance.localInstance
                .metaData["game-state"] = CgsGameEngine.INSTANCE.gameState
                .name.replace("STARTED", "IN_GAME")

            Lemon.instance.localInstance
                .metaData["remaining"] = Bukkit.getOnlinePlayers()
                .count { !it.hasMetadata("spectator") }.toString()
        }, 0L, 20L)
        // TEMPORARY

        BorderHandler.setBorder(100)
    }
}
