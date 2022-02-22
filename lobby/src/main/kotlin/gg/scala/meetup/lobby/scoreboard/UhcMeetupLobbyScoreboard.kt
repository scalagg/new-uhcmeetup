package gg.scala.meetup.lobby.scoreboard

import gg.scala.cgs.lobby.updater.CgsGameInfoUpdater
import gg.scala.meetup.shared.UhcMeetupCgsInfo
import net.evilblock.cubed.scoreboard.ScoreboardAdapter
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object UhcMeetupLobbyScoreboard : ScoreboardAdapter()
{
    override fun getLines(
        board: LinkedList<String>,
        player: Player
    )
    {
        board.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------")
        board.add("In Game: ${CC.PRI}${CgsGameInfoUpdater.playingTotalCount}")
        board.add("In Lobby: ${CC.PRI}${CgsGameInfoUpdater.lobbyTotalCount}")
        board.add("")

        for (gameMode in UhcMeetupCgsInfo.gameModes)
        {
            board.add("${CC.D_AQUA}${gameMode.getName()}:")
            board.add(" In Game: ${CC.PRI}${CgsGameInfoUpdater.gameModeCounts[gameMode.getId()]}")
            board.add("")
        }

        board.add("${CC.PRI}www.verio.cc")
        board.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------")
    }

    override fun getTitle(player: Player) =
        CC.B_PRI + "UHC Meetup"
}
