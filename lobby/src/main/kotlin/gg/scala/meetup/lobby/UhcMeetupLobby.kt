package gg.scala.meetup.lobby

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
        PluginDependency("CGS-Lobby"),
        PluginDependency("Grape")
    ]
)
class UhcMeetupLobby : ExtendedScalaPlugin()
{
    override fun enable()
    {

    }

    override fun disable()
    {

    }
}
