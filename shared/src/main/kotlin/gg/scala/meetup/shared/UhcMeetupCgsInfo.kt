package gg.scala.meetup.shared

import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.meetup.shared.gamemode.UhcMeetupDuosGameMode
import gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode
import gg.scala.meetup.shared.gamemode.UhcMeetupTriosGameMode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupCgsInfo : CgsGameGeneralInfo(
    "UHC Meetup", 0.01F, 5,
    61, UhcMeetupCgsAwards, true, true,
    true, false, true,
    true, listOf(
        UhcMeetupSoloGameMode,
        UhcMeetupDuosGameMode,
        UhcMeetupTriosGameMode
    )
)
