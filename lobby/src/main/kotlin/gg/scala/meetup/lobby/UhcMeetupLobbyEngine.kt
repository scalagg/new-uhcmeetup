package gg.scala.meetup.lobby

import com.cryptomorin.xseries.XMaterial
import gg.scala.cgs.common.instance.CgsServerInstance
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.cgs.lobby.gamemode.CgsGameLobby
import gg.scala.cgs.lobby.updater.CgsGameInfoUpdater
import gg.scala.meetup.lobby.ranking.TopGamesRankingEntry
import gg.scala.meetup.lobby.ranking.TopKillsRankingEntry
import gg.scala.meetup.lobby.ranking.TopWinsRankingEntry
import gg.scala.meetup.lobby.scoreboard.UhcMeetupLobbyScoreboard
import gg.scala.meetup.shared.UhcMeetupCgsInfo
import gg.scala.meetup.shared.UhcMeetupCgsStatistics
import gg.scala.meetup.shared.gamemode.UhcMeetupDuosGameMode
import gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode
import gg.scala.meetup.shared.gamemode.UhcMeetupTriosGameMode
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bungee.BungeeUtil
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object UhcMeetupLobbyEngine : CgsGameLobby<UhcMeetupCgsStatistics>(
    UhcMeetupCgsStatistics::class
)
{
    override fun getFormattedButton(
        info: CgsServerInstance, player: Player
    ): Button
    {
        return ItemBuilder.of(XMaterial.FIRE_CHARGE)
            .name("${CC.GREEN}${info.internalServerId}")
            .addToLore(
                "${CC.SEC}Participants: ${CC.PRI}${
                    info.gameServerInfo!!.participants.size
                }",
                "${CC.SEC}Status: ${CC.PRI}${
                    info.gameServerInfo!!.state.name
                        .lowercase().capitalize()
                }",
                "",
                "${CC.SEC}Map: ${CC.PRI}${
                    info.gameServerInfo!!.arenaId
                }",
                "${CC.SEC}Mode: ${CC.PRI}${
                    info.gameServerInfo!!.gameMode.capitalize()
                }",
                "",
                "${CC.GREEN}Click to spectate!"
            )
            .toButton { _, _ ->
                BungeeUtil.sendToServer(player, info.internalServerId)
            }
    }

    override fun getGameInfo() = UhcMeetupCgsInfo

    override fun getGameModeButtons() = mutableMapOf(
        11 to ItemBuilder(Material.FISHING_ROD)
            .name("${CC.D_AQUA}Solos")
            .addToLore(
                "${CC.SEC}Join a game of Solos",
                "${CC.SEC}UHC Meetup!",
                "",
                " ${CC.SEC}In game: ${CC.AQUA}${CgsGameInfoUpdater.gameModeCounts["solo"]}",
                "",
                "${CC.GREEN}Click to join game!"
            )
            .toButton { player, _ ->
                val server = CgsGameInfoUpdater
                    .findAvailableServer(
                        UhcMeetupSoloGameMode, "UHC Meetup"
                    )

                if (server != null)
                {
                    player!!.sendMessage("${CC.SEC}Connecting you to ${CC.PRI}${server.internalServerId}${CC.SEC}...")
                    BungeeUtil.sendToServer(player, server.internalServerId)
                } else
                {
                    player!!.sendMessage("${CC.RED}Sorry! We were unable to find a suitable server for you to join.")
                }
            },
        13 to ItemBuilder(Material.FISHING_ROD)
            .name("${CC.D_AQUA}Duos")
            .addToLore(
                "${CC.SEC}Join a game of Duos",
                "${CC.SEC}UHC Meetup!",
                "",
                " ${CC.SEC}In game: ${CC.AQUA}${CgsGameInfoUpdater.gameModeCounts["duos"]}",
                "",
                "${CC.GREEN}Click to join game!"
            )
            .toButton { player, _ ->
                val server = CgsGameInfoUpdater
                    .findAvailableServer(
                        UhcMeetupDuosGameMode, "UHC Meetup"
                    )

                if (server != null)
                {
                    player!!.sendMessage("${CC.SEC}Connecting you to ${CC.PRI}${server.internalServerId}${CC.SEC}...")
                    BungeeUtil.sendToServer(player, server.internalServerId)
                } else
                {
                    player!!.sendMessage("${CC.RED}Sorry! We were unable to find a suitable server for you to join.")
                }
            },
        15 to ItemBuilder(Material.FISHING_ROD)
            .name("${CC.D_AQUA}Trios")
            .addToLore(
                "${CC.SEC}Join a game of Trios",
                "${CC.SEC}UHC Meetup!",
                "",
                " ${CC.SEC}In game: ${CC.AQUA}${CgsGameInfoUpdater.gameModeCounts["trios"]}",
                "",
                "${CC.GREEN}Click to join game!"
            )
            .toButton { player, _ ->
                val server = CgsGameInfoUpdater
                    .findAvailableServer(
                        UhcMeetupTriosGameMode, "UHC Meetup"
                    )

                if (server != null)
                {
                    player!!.sendMessage("${CC.SEC}Connecting you to ${CC.PRI}${server.internalServerId}${CC.SEC}...")
                    BungeeUtil.sendToServer(player, server.internalServerId)
                } else
                {
                    player!!.sendMessage("${CC.RED}Sorry! We were unable to find a suitable server for you to join.")
                }
            }
    )

    override fun getRankingEntries() = mutableListOf(
        TopGamesRankingEntry,
        TopKillsRankingEntry,
        TopWinsRankingEntry
    )

    override fun getScoreboardAdapter() =
        UhcMeetupLobbyScoreboard
}
