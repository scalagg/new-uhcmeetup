package gg.scala.meetup.shared

import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.ktp.game.gamemode.KillThePlayerSoloGameMode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupCgsInfo : CgsGameGeneralInfo(
    "UHC Meetup", 0.01F, 5,
    61, UhcMeetupCgsAwards, true,
    true, false, true,
    true, listOf(KillThePlayerSoloGameMode)
)
