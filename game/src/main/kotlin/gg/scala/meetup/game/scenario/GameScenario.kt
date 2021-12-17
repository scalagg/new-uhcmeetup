package gg.scala.meetup.game.scenario

import org.bukkit.event.Listener

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
interface GameScenario
{
    fun getListeners(): List<Listener>
    {
        return emptyList()
    }
}
