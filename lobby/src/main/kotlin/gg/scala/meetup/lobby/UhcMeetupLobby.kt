package gg.scala.meetup.lobby

import gg.scala.cgs.lobby.gamemode.CgsGameLobby
import gg.scala.cloudsync.shared.discovery.CloudSyncDiscoveryService
import gg.scala.commons.ExtendedScalaPlugin
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
@Plugin(
    name = "UHCMeetup-Lobby",
    depends = [
        PluginDependency("Cubed"),
        PluginDependency("helper"),
        PluginDependency("Lemon"),
        PluginDependency("CGS-Lobby")
    ]
)
class UhcMeetupLobby : ExtendedScalaPlugin()
{
    override fun enable()
    {
        CgsGameLobby.INSTANCE = UhcMeetupLobbyEngine

        CloudSyncDiscoveryService
            .discoverable.assets
            .apply {
                add("gg.scala.meetup:lobby:uhc-meetup-lobby")
            }
    }
}
