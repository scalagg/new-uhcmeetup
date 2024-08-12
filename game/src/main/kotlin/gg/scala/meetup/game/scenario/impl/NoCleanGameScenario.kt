package gg.scala.meetup.game.scenario.impl

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.commons.annotations.Listeners
import gg.scala.lemon.handler.PlayerHandler
import gg.scala.meetup.game.UhcMeetupEngine
import gg.scala.meetup.game.scenario.GameScenario
import gg.scala.meetup.shared.UhcMeetupCgsStatistics
import gg.scala.meetup.shared.tickable.TickableBukkitRunnable
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.time.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
@Listeners
object NoCleanGameScenario : GameScenario, Listener
{
    @JvmStatic
    private val TICKS = listOf(15, 10, 5, 4, 3, 2, 1)

    fun startNoCleanTimer(player: Player)
    {
        val statistics = UhcMeetupEngine.INSTANCE
            .getStatistics(
                CgsPlayerHandler.find(player)!!
            )

        NoCleanGameScenarioTimer(statistics, player)
    }

    fun cancelNoCleanTimer(player: Player)
    {
        val statistics = UhcMeetupEngine.INSTANCE
            .getStatistics(
                CgsPlayerHandler.find(player)!!
            )

        statistics.noCleanTimer ?: return

        statistics.noCleanTimer!!.cancel()
        statistics.noCleanTimer = null

        player.sendMessage("${CC.B_RED}Your no-clean timer has expired due to hostile action!")
    }

    override fun getListeners(): List<Listener>
    {
        return listOf(this)
    }

    class NoCleanGameScenarioTimer(
        private val statistics: UhcMeetupCgsStatistics,
        private val player: Player
    ) : TickableBukkitRunnable()
    {
        init
        {
            ticks = 16
            statistics.noCleanTimer = this

            runTaskTimer(CgsGameEngine.INSTANCE.plugin, 0L, 20L)
        }

        override fun run()
        {
            if (Bukkit.getPlayer(player.uniqueId) == null)
            {
                cancel()
                return
            }

            ticks--

            if (TICKS.contains(ticks))
            {
                player.sendMessage("${CC.RED}Your no-clean timer expires in ${CC.BOLD}${
                    TimeUtil.formatIntoDetailedString(ticks)
                }${CC.RED}!")
            } else if (ticks == 0)
            {
                if (Bukkit.getPlayer(player.uniqueId) != null)
                {
                    player.sendMessage(CC.B_RED + "Your no clean timer has expired.")
                    statistics.noCleanTimer = null
                }

                cancel()
                return
            }
        }
    }
}
