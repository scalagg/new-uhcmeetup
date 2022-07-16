package gg.scala.meetup.lobby

import gg.scala.cgs.lobby.gamemode.CgsGameLobby
import gg.scala.cloudsync.shared.discovery.CloudSyncDiscoveryService
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
@Plugin(
    name = "UHCMeetup-Lobby",
    depends = [
        PluginDependency("scala-commons"),
        PluginDependency("Lemon"),
        PluginDependency("CGS-Lobby")
    ]
)
class UhcMeetupLobby : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        CgsGameLobby.INSTANCE = UhcMeetupLobbyEngine

        CloudSyncDiscoveryService
            .discoverable.assets
            .apply {
                add("gg.scala.meetup:lobby:uhc-meetup-lobby")
            }
    }
}
