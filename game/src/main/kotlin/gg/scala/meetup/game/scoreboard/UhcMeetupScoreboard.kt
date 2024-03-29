package gg.scala.meetup.game.scoreboard

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.cgs.common.player.scoreboard.CgsGameScoreboardRenderer
import gg.scala.cgs.common.runnable.state.StartingStateRunnable
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.cgs.game.CgsEnginePlugin
import gg.scala.lemon.LemonConstants
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
    override fun getTitle() = "${CC.B_PRI}UHC Meetup"

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
        lines.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------${
            if (state.isAfter(CgsGameState.STARTED))
                "--" else ""
        }")

        if (state == CgsGameState.WAITING || state == CgsGameState.STARTING)
        {
            if (state == CgsGameState.WAITING)
            {
                lines.add("Waiting for players...")
            } else if (state == CgsGameState.STARTING)
            {
                lines.add("Starting in ${CC.PRI}${TimeUtil.formatIntoAbbreviatedString(StartingStateRunnable.PRE_START_TIME)}")
            }

            lines.add("")
            lines.add("Players: ${CC.PRI}${
                Bukkit.getOnlinePlayers().size
            }/${
                Bukkit.getMaxPlayers()
            }")
            lines.add("")
            lines.add("Mode: ${CC.PRI}${
                CgsGameEngine.INSTANCE.gameMode.getName()
            }")
            lines.add("Version: ${CC.PRI}${
                CgsGameEngine.INSTANCE.gameInfo.gameVersion
            }")
        } else if (state.isAfter(CgsGameState.STARTED))
        {
            val cgsGamePlayer = CgsPlayerHandler.find(player)!!

            val statistics = UhcMeetupEngine.INSTANCE
                .getStatistics(cgsGamePlayer)

            lines.add("Border: " + CC.PRI + BorderHandler.currentBorder + CC.GRAY + BorderHandler.getFormattedBorderStatus())
            lines.add(
                "Remaining: " + CC.PRI + remaining + "/" + CgsGameEngine.INSTANCE.originalRemaining.size
            )
            lines.add("Ping: " + CC.PRI + getFormattedPing(getPing(player)))
            lines.add("Kills: " + CC.PRI + statistics.gameKills.value)

            if (statistics.noCleanTimer != null)
            {
                lines.add("")
                lines.add(CC.D_AQUA + "Cooldowns:")
                lines.add(" No Clean " + CC.PRI + "(" + statistics.noCleanTimer!!.ticks + ")")
            }
        }

        lines.add("")
        lines.add("${CC.PRI}${LemonConstants.WEB_LINK}")
        lines.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------${
            if (state.isAfter(CgsGameState.STARTED))
                "--" else ""
        }")
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
