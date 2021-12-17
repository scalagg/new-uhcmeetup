package gg.scala.meetup.game

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.CgsGameState
import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.meetup.shared.UhcMeetupCgsStatistics
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

    override fun onTick(state: CgsGameState, tickOfState: Int): Boolean
    {
        return true
    }

    override fun getScoreboardRenderer() = KillThePlayerScoreboard
    override fun getVisibilityAdapter() = KillThePlayerVisibilityAdapter
    override fun getNametagAdapter() = KillThePlayerNametagAdapter
    override fun getSnapshotCreator() = KillThePlayerCgsSnapshot

    override fun getExtraWinInformation() = listOf(" Something something something")
}
