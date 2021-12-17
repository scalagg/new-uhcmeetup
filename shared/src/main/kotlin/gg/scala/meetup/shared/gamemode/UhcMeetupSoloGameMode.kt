package gg.scala.meetup.shared.gamemode

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.meetup.shared.arena.UhcMeetupRandomArena
import net.evilblock.cubed.util.CC
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object UhcMeetupSoloGameMode : CgsGameMode
{
    override fun getId() = "solo"
    override fun getName() = "Solo"

    override fun getMaterial() = Pair(Material.GOLDEN_APPLE, 0)

    override fun getDescription() = "${CC.GRAY}A solo game of UHC Meetup!"

    override fun getArenas() = listOf(
        UhcMeetupRandomArena
    )

    override fun getTeamSize() = 1
    override fun getMaxTeams() = 32
}
