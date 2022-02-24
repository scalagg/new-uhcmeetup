package gg.scala.meetup.shared.arena

import gg.scala.cgs.common.information.arena.CgsGameArena
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.nio.file.Path

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object UhcMeetupRandomArena : CgsGameArena
{
    override fun getId() = "uhc_meetup"
    override fun getName() = "Random"

    override fun getMaterial() = Pair(Material.ENDER_PEARL, 0)
    override fun getDescription() = "UHC Meetup games have randomly generated arenas!"

    override fun getDirectory(): Path? = null

    override fun getBukkitWorldName() = "meetup"

    override fun getPreLobbyLocation() = Location(
        Bukkit.getWorld("world"),
        0.5, 35.0, 0.5
    )

    override fun getSpectatorLocation() = Location(
        Bukkit.getWorld("meetup"), 0.5, Bukkit.getWorld("meetup").getHighestBlockYAt(0, 0) + 15.0, 0.5
    )
}
