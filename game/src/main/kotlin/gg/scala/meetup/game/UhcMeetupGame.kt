package gg.scala.meetup.game

import gg.scala.cgs.common.information.arena.CgsGameArenaHandler
import gg.scala.cloudsync.shared.discovery.CloudSyncDiscoveryService
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.core.plugin.*
import gg.scala.meetup.game.handler.BorderHandler
import gg.scala.meetup.game.listener.UhcMeetupListener
import gg.scala.meetup.game.runnable.UhcMeetupWorldGenerationRunnable
import gg.scala.meetup.game.scenario.impl.NoCleanGameScenario
import gg.scala.meetup.game.scenario.impl.TimeBombGameScenario
import gg.scala.meetup.shared.UhcMeetupCgsInfo
import gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode
import org.bukkit.Bukkit

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
@Plugin(
    name = "UHC-Mini",
    version = "%remote%/%branch%/%id%"
)
@PluginAuthor("Scala")
@PluginWebsite("https://scala.gg")
@PluginDependencyComposite(
    PluginDependency("scala-commons"),
    PluginDependency("ScGameFramework"),
    PluginDependency("Lemon"),
    PluginDependency("cloudsync", soft = true)
)
class UhcMeetupGame : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        val engine = UhcMeetupEngine(
            this,
            UhcMeetupCgsInfo,
            UhcMeetupSoloGameMode
        )

        UhcMeetupEngine.INSTANCE = engine
        CgsGameArenaHandler.engine = engine

        engine.initialLoad()
        CgsGameArenaHandler.configure(UhcMeetupSoloGameMode)

        UhcMeetupWorldGenerationRunnable.initialLoad()
        CgsGameArenaHandler.world = Bukkit.getWorld("meetup")

        CloudSyncDiscoveryService
            .discoverable.assets
            .add("gg.solara.uhc.minis:game:uhc-mini-game")

        BorderHandler.setBorder(100)
    }
}
