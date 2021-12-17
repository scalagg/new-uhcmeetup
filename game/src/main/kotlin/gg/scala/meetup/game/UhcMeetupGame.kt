package gg.scala.meetup.game

import gg.scala.cgs.common.information.arena.CgsGameArenaHandler
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.ktp.game.gamemode.KillThePlayerSoloGameMode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class UhcMeetupGame : ExtendedScalaPlugin()
{
    override fun enable()
    {
        CgsGameArenaHandler.initialLoad(
            KillThePlayerSoloGameMode
        )

        val engine = KillThePlayerCgsEngine(
            this, KillThePlayerCgsInfo,
            KillThePlayerSoloGameMode
        )
        engine.statisticType = KillThePlayerCgsStatistics::class
        engine.initialLoad()
    }
}
