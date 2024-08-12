package gg.scala.meetup.game.scoreboard

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.cgs.common.player.scoreboard.CgsGameScoreboardRenderer
import gg.scala.cgs.common.runnable.state.StartingStateRunnable
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.meetup.game.UhcMeetupEngine
import gg.scala.meetup.game.handler.BorderHandler
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.nms.MinecraftReflection.getPing
import net.evilblock.cubed.util.time.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object UhcMeetupScoreboard : CgsGameScoreboardRenderer
{
    override fun getTitle() = "${CC.B_PRI}Meetup"

    private var remaining = 0

    init
    {
        Tasks.asyncTimer({
            remaining = Bukkit.getOnlinePlayers()
                .filter { !it.hasMetadata("spectator") }
                .size
        }, 0L, 10L)
    }

    override fun render(lines: LinkedList<String>, player: Player, state: CgsGameState)
    {
        lines += ""
        if (state == CgsGameState.WAITING || state == CgsGameState.STARTING)
        {
            lines.add("${CC.GRAY}Players: ${CC.PRI}${
                Bukkit.getOnlinePlayers().size
            }/${
                Bukkit.getMaxPlayers()
            }")
            lines.add("")

            if (state == CgsGameState.WAITING)
            {
                lines.add("${CC.WHITE}Waiting...")
            } else if (state == CgsGameState.STARTING)
            {
                lines.add("${CC.GRAY}Starting in")
                lines += "${CC.WHITE}${TimeUtil.formatIntoAbbreviatedString(StartingStateRunnable.PRE_START_TIME)}"
            }

            lines += ""
            lines.add("${CC.GRAY}Mode: ${CC.GOLD}${
                CgsGameEngine.INSTANCE.gameMode.getName()
            }")
            lines.add("${CC.GRAY}Version: ${CC.PRI}${
                CgsGameEngine.INSTANCE.gameInfo.gameVersion
            }")
        } else if (state.isAfter(CgsGameState.STARTED))
        {
            val cgsGamePlayer = CgsPlayerHandler.find(player)!!

            val statistics = UhcMeetupEngine.INSTANCE
                .getStatistics(cgsGamePlayer)

            lines.add("${CC.GRAY}Border: " + CC.GOLD + BorderHandler.currentBorder)
            BorderHandler.getFormattedBorderStatus().apply {
                if (isNotBlank())
                {
                    lines += "${CC.GRAY}Next: ${CC.YELLOW}${BorderHandler.getNextBorder()}${CC.PRI}$this"
                }
            }
            lines += ""
            lines.add("${CC.GRAY}Remaining: " + CC.PRI + remaining + "/" + CgsGameEngine.INSTANCE.originalRemaining.size)
            lines += ""
            lines.add("${CC.GRAY}Ping: " + CC.PRI + getFormattedPing(getPing(player)))
            lines.add("${CC.GRAY}Kills: " + CC.PRI + statistics.gameKills.value)

            if (statistics.noCleanTimer != null)
            {
                lines.add("")
                lines.add(CC.YELLOW + "Cooldowns:")
                lines.add("${CC.WHITE}No Clean " + CC.D_GRAY + "(" + TimeUtil.formatIntoMMSS(statistics.noCleanTimer!!.ticks) + ")")
            }
        }

        lines.add("")
        lines.add("${CC.YELLOW}solara.gg      ${CC.GRAY}")
    }

    private fun getFormattedPing(ping: Int): String
    {
        return if (ping > 300)
        {
            CC.D_RED + ping + "ms"
        } else if (ping > 150)
        {
            CC.PRI + ping + "ms"
        } else if (ping > 80)
        {
            CC.YELLOW + ping + "ms"
        } else
        {
            CC.GREEN + ping + "ms"
        }
    }
}
