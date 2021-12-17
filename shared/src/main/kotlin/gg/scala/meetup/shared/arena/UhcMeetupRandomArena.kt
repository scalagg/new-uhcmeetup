package gg.scala.meetup.shared.arena

import gg.scala.cgs.common.information.arena.CgsGameArena
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.nio.file.Path
import java.nio.file.Paths

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

    // TODO: 12/17/2021 match this with other shit
    override fun getPreLobbyLocation() = Location(
        Bukkit.getWorld("meetup"), 1.0, 64.0, 1.0
    )

    override fun getSpectatorLocation() = Location(
        Bukkit.getWorld("meetup"), 1.0, 64.0, 1.0
    )
}
