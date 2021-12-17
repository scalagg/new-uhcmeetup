package gg.scala.meetup.shared.tickable

import org.bukkit.scheduler.BukkitRunnable

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
abstract class TickableBukkitRunnable : BukkitRunnable()
{
    var ticks = 0
}
