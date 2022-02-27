package gg.scala.meetup.shared

import gg.scala.cgs.common.player.statistic.GameSpecificStatistics
import gg.scala.cgs.common.player.statistic.value.CgsGameStatistic
import gg.scala.meetup.shared.loadout.UhcMeetupLoadout
import gg.scala.meetup.shared.tickable.TickableBukkitRunnable
import java.lang.reflect.Type

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class UhcMeetupCgsStatistics
@JvmOverloads
constructor(
    override var gameKills: CgsGameStatistic = CgsGameStatistic(),
    override var kills: CgsGameStatistic = CgsGameStatistic(),
    override var deaths: CgsGameStatistic = CgsGameStatistic(),
    override var played: CgsGameStatistic = CgsGameStatistic(),
    override var wins: CgsGameStatistic = CgsGameStatistic(),
    override var losses: CgsGameStatistic = CgsGameStatistic()
) : GameSpecificStatistics()
{
    @Transient
    var noCleanTimer: TickableBukkitRunnable? = null

    val loadout = UhcMeetupLoadout()

    init
    {
        if (loadout.itemStacks.isEmpty())
        {
            loadout.resetAndApplyDefault()
        }
    }

    override fun getAbstractType(): Type
    {
        return UhcMeetupCgsStatistics::class.java
    }
}
