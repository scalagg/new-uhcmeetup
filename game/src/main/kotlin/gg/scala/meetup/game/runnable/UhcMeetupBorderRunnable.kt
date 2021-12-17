package gg.scala.meetup.game.runnable

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.meetup.game.UhcMeetupEngine
import gg.scala.meetup.game.handler.BorderHandler
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.time.TimeUtil
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupBorderRunnable : BukkitRunnable()
{
    @JvmStatic
    val TICKS = listOf(
        18000, 14400, 10800, 7200, 3600, 2700,
        1800, 900, 600, 300, 240, 180, 120, 60,
        50, 40, 30, 15, 10, 5, 4, 3, 2, 1
    )

    fun initialLoad()
    {
        runTaskTimer(UhcMeetupEngine.INSTANCE.plugin, 0L, 20L)
    }

    override fun run()
    {
        if (BorderHandler.currentBorderTime <= 0)
        {
            val nextBorder = BorderHandler.getNextBorder()

            if (nextBorder == 10)
            {
                BorderHandler.setBorder(10)
                cancel()
                return
            }

            BorderHandler.setBorder(nextBorder)
            BorderHandler.currentBorderTime = 120
        } else if (TICKS.contains(BorderHandler.currentBorderTime))
        {
            CgsGameEngine.INSTANCE.broadcast(
                "${CC.SEC}The border will shrink to ${CC.WHITE}${BorderHandler.getNextBorder()}${CC.SEC} blocks in ${CC.PRI}${
                    TimeUtil.formatIntoDetailedString(BorderHandler.currentBorderTime)
                }${CC.SEC}!"
            )
        }
    }
}
