package gg.scala.meetup.game

import gg.scala.cgs.common.information.arena.CgsGameArenaHandler
import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.cloudsync.shared.discovery.CloudSyncDiscoveryService
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.meetup.game.handler.BorderHandler
import gg.scala.meetup.game.listener.UhcMeetupListener
import gg.scala.meetup.game.runnable.UhcMeetupWorldGenerationRunnable
import gg.scala.meetup.game.scenario.impl.NoCleanGameScenario
import gg.scala.meetup.game.scenario.impl.TimeBombGameScenario
import gg.scala.meetup.shared.UhcMeetupCgsInfo
import gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency
import org.bukkit.Bukkit

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
@Plugin(
    name = "UHCMeetup",
    depends = [
        PluginDependency("scala-commons"),
        PluginDependency("Lemon"),
        PluginDependency("CGS-Engine"),
        PluginDependency("Grape")
    ]
)
class UhcMeetupGame : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        UhcMeetupWorldGenerationRunnable.initialLoad()

        CgsGameArenaHandler.configure(
            UhcMeetupSoloGameMode
        )

        val orchestration =
            loadConfig("orchestration.yml")

        val mode = orchestration.getString("mode")
            ?: "gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode"

        val modeClass = Class.forName(mode)

        val modeClassObject = modeClass
            .getField("INSTANCE")
            .get(null)

        val engine = UhcMeetupEngine(
            this, UhcMeetupCgsInfo,
            modeClassObject as CgsGameMode
        )

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

        CloudSyncDiscoveryService
            .discoverable.assets
            .add("gg.scala.meetup:game:uhc-meetup-game")

        BorderHandler.setBorder(100)
    }
}
