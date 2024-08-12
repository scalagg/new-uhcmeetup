package gg.scala.meetup.shared

import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.meetup.shared.gamemode.UhcMeetupSoloGameMode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupCgsInfo : CgsGameGeneralInfo(
    "Meetup", 1.0F, 5,
    61, UhcMeetupCgsAwards, false, true,
    true, false, true,
    true, listOf(
        UhcMeetupSoloGameMode
    )
)
