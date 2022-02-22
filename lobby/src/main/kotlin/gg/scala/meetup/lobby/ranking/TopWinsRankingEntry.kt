package gg.scala.meetup.lobby.ranking

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.lobby.leaderboard.CgsLobbyRankingEntry
import gg.scala.meetup.lobby.UhcMeetupLobbyEngine

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object TopWinsRankingEntry : CgsLobbyRankingEntry
{
    override fun getDisplay() = "Wins"
    override fun getId() = "wins"

    override fun getValue(cgsGamePlayer: CgsGamePlayer): Int
    {
        return UhcMeetupLobbyEngine
            .getStatistics(cgsGamePlayer)
            .wins.value
    }
}
