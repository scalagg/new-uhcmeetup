package gg.scala.meetup.game.visibility

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.common.player.visibility.CgsGameVisibilityAdapter
import net.evilblock.cubed.visibility.VisibilityAction

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object UhcMeetupVisibilityAdapter : CgsGameVisibilityAdapter
{
    override fun computeVisibility(
        viewer: CgsGamePlayer, target: CgsGamePlayer
    ): VisibilityAction
    {
        return VisibilityAction.NEUTRAL
    }
}
