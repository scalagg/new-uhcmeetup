package gg.scala.meetup.shared.gamemode

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.meetup.shared.arena.UhcMeetupRandomArena
import net.evilblock.cubed.util.CC
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object UhcMeetupTriosGameMode : CgsGameMode
{
    override fun getId() = "trios"
    override fun getName() = "Trios"

    override fun getMaterial() = Pair(Material.GOLDEN_APPLE, 0)

    override fun getDescription() = "${CC.GRAY}A trios game of UHC Meetup!"

    override fun getArenas() = listOf(
        UhcMeetupRandomArena
    )

    override fun getTeamSize() = 3
    override fun getMaxTeams() = 8
}
