package gg.scala.meetup.lobby.ranking

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.lobby.leaderboard.CgsLobbyRankingEntry
import gg.scala.meetup.lobby.UhcMeetupLobbyEngine

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object TopGamesRankingEntry : CgsLobbyRankingEntry
{
    override fun getDisplay() = "Played"
    override fun getId() = "played"

    override fun getValue(cgsGamePlayer: CgsGamePlayer): Int
    {
        return UhcMeetupLobbyEngine
            .getStatistics(cgsGamePlayer)
            .played.value
    }
}
