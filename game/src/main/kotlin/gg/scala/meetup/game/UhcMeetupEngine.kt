package gg.scala.meetup.game

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.lemon.util.CubedCacheUtil
import gg.scala.meetup.game.listener.UhcMeetupListener
import gg.scala.meetup.game.nametag.UhcMeetupNametagAdapter
import gg.scala.meetup.game.scoreboard.UhcMeetupScoreboard
import gg.scala.meetup.game.visibility.UhcMeetupVisibilityAdapter
import gg.scala.meetup.shared.UhcMeetupCgsSnapshot
import gg.scala.meetup.shared.UhcMeetupCgsStatistics
import net.evilblock.cubed.util.CC
import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
class UhcMeetupEngine(
    plugin: ExtendedScalaPlugin,
    gameInfo: CgsGameGeneralInfo,
    gameMode: CgsGameMode
) : CgsGameEngine<UhcMeetupCgsStatistics>(
    plugin, gameInfo, gameMode
)
{
    companion object
    {
        @JvmStatic
        var INSTANCE by Delegates.notNull<UhcMeetupEngine>()
    }

    override fun getScoreboardRenderer() = UhcMeetupScoreboard
    override fun getVisibilityAdapter() = UhcMeetupVisibilityAdapter
    override fun getNametagAdapter() = UhcMeetupNametagAdapter
    override fun getSnapshotCreator() = UhcMeetupCgsSnapshot

    override fun getExtraWinInformation(): List<String>
    {
        val topKills = mutableListOf<String>()

        val sortedKills = UhcMeetupListener.killsTracker
            .entries.sortedByDescending { it.value }

        for (i in 0 until 3.coerceAtMost(sortedKills.size))
        {
            val (key, value) = sortedKills[i]
            topKills.add("  #$i ${CC.GRAY}- ${CC.WHITE}${CubedCacheUtil.fetchName(key)}${CC.GRAY} - ${CC.PRI}$value")
        }

        return topKills
    }

    override fun onTick(state: CgsGameState, tickOfState: Int): Boolean
    {
        TODO("Not yet implemented")
    }
}
