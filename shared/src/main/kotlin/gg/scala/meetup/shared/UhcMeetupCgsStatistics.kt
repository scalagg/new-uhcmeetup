package gg.scala.meetup.shared

import gg.scala.cgs.common.player.statistic.GameSpecificStatistics
import gg.scala.cgs.common.player.statistic.value.CgsGameStatistic
import gg.scala.meetup.shared.loadout.UhcMeetupLoadout
import gg.scala.meetup.shared.tickable.TickableBukkitRunnable
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import java.lang.reflect.Type
import java.util.UUID

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class UhcMeetupCgsStatistics(uniqueId: UUID): GameSpecificStatistics(uniqueId)
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

    override fun save() = DataStoreObjectControllerCache
        .findNotNull<UhcMeetupCgsStatistics>()
        .save(this, DataStoreStorageType.MONGO)
}
