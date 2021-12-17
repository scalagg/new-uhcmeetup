package gg.scala.meetup.game.runnable

import gg.scala.meetup.game.handler.BorderHandler
import io.papermc.lib.PaperLib
import me.lucko.helper.scheduler.threadlock.ServerThreadLock
import net.minecraft.server.v1_8_R3.BiomeBase
import org.bukkit.*
import org.bukkit.block.Biome
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock
import org.bukkit.entity.Player
import java.io.File
import java.util.logging.Logger

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object UhcMeetupWorldGenerationRunnable
{
    fun initialLoad()
    {
        deleteDirectory(File("meetup"))
        Logger.getGlobal().info("[UHCMeetup] Generation of the UHC Meetup world has started.")

        swapBiomes()
    }

    private fun generateNewWorld()
    {
        val worldCreator = WorldCreator("meetup")
        worldCreator.generateStructures(false)

        try
        {
            Bukkit.createWorld(worldCreator)
        } catch (ignored: Exception)
        {
            Bukkit.shutdown()
            return
        }

        val lock = File("meetup", "gen.lock")
        lock.createNewFile()

        handleLoadChunks()

        for (world in Bukkit.getWorlds())
        {
            world.entities.forEach {
                if (it !is Player)
                {
                    it.remove()
                }
            }
        }

        val world = Bukkit.getWorld("meetup")

        world.setGameRuleValue("doMobSpawning", "false")
        world.setGameRuleValue("doDaylightCycle", "false")
        world.setGameRuleValue("naturalRegeneration", "false")
        world.setGameRuleValue("doFireTick", "false")
        world.setGameRuleValue("difficulty", "0")
        world.time = 0

        handleLoadChunks()

        for (chunk in world.loadedChunks)
        {
            val cx = chunk.x shl 4
            val cz = chunk.z shl 4
            for (x in cx until cx + 16)
            {
                for (z in cz until cz + 16)
                {
                    world.getBlockAt(x, 50, z).type = Material.BEDROCK
                }
            }
        }

        BorderHandler.setBorder(100)
    }

    private fun handleLoadChunks()
    {
        for (x in -110..109)
        {
            for (z in -110..109)
            {
                val location = Location(
                    Bukkit.getWorld("meetup"), x.toDouble(), 60.0,
                    z.toDouble()
                )
                if (!location.chunk.isLoaded)
                {
                    PaperLib.getChunkAtAsync(location)
                        .whenComplete { chunk: Chunk, throwable: Throwable? ->
                            if (throwable != null)
                            {
                                Logger.getGlobal()
                                    .info("Failed to load chunk async, fallbacking to sync loading. (" + throwable.message + ")")

                                try
                                {
                                    ServerThreadLock.obtain()
                                        .use { location.world.loadChunk(x, z) }
                                } catch (exception: Exception)
                                {
                                    Logger.getGlobal()
                                        .info("Failed to load chunk sync. (" + exception.message + ")")
                                }
                            } else
                            {
                                Logger.getGlobal()
                                    .info("Loaded chunk at " + chunk.x + ", " + chunk.z + ".")
                            }
                        }
                }
            }
        }
    }

    private fun deleteDirectory(path: File): Boolean
    {
        if (path.exists())
        {
            val files = path.listFiles()
            if (files != null)
            {
                for (file in files)
                {
                    if (file.isDirectory)
                    {
                        deleteDirectory(file)
                    } else
                    {
                        file.delete()
                    }
                }
            }
        }
        return path.delete()
    }

    private fun swapBiomes()
    {
        setBiomeBase(Biome.SMALL_MOUNTAINS, Biome.SAVANNA, 0)
        setBiomeBase(Biome.MUSHROOM_ISLAND, Biome.SAVANNA, 0)
        setBiomeBase(Biome.MUSHROOM_SHORE, Biome.SAVANNA, 0)
        setBiomeBase(Biome.DESERT_MOUNTAINS, Biome.DESERT, 0)
        setBiomeBase(Biome.DESERT_HILLS, Biome.DESERT, 0)
        setBiomeBase(Biome.FLOWER_FOREST, Biome.PLAINS, 0)
        setBiomeBase(Biome.SUNFLOWER_PLAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.OCEAN, Biome.PLAINS, 0)
        setBiomeBase(Biome.RIVER, Biome.PLAINS, 0)
        setBiomeBase(Biome.BEACH, Biome.TAIGA, 0)
        setBiomeBase(Biome.JUNGLE, Biome.PLAINS, 0)
        setBiomeBase(Biome.JUNGLE_HILLS, Biome.TAIGA, 0)
        setBiomeBase(Biome.JUNGLE_EDGE, Biome.DESERT, 0)
        setBiomeBase(Biome.JUNGLE_MOUNTAINS, Biome.DESERT, 0)
        setBiomeBase(Biome.JUNGLE_EDGE_MOUNTAINS, Biome.DESERT, 0)
        setBiomeBase(Biome.DEEP_OCEAN, Biome.PLAINS, 0)
        setBiomeBase(Biome.SAVANNA_PLATEAU, Biome.PLAINS, 0)
        setBiomeBase(Biome.ROOFED_FOREST, Biome.DESERT, 0)
        setBiomeBase(Biome.STONE_BEACH, Biome.PLAINS, 0)
        setBiomeBase(Biome.JUNGLE, Biome.PLAINS, 128)
        setBiomeBase(Biome.JUNGLE_EDGE, Biome.DESERT, 128)
        setBiomeBase(Biome.SAVANNA, Biome.SAVANNA, 128)
        setBiomeBase(Biome.SAVANNA_PLATEAU, Biome.DESERT, 128)
        setBiomeBase(Biome.FOREST_HILLS, Biome.PLAINS, 0)
        setBiomeBase(Biome.BIRCH_FOREST_HILLS, Biome.PLAINS, 0)
        setBiomeBase(Biome.BIRCH_FOREST_HILLS, Biome.PLAINS, 128)
        setBiomeBase(Biome.BIRCH_FOREST_HILLS_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.BIRCH_FOREST_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.TAIGA, Biome.SAVANNA, 0)
        setBiomeBase(Biome.TAIGA, Biome.SAVANNA, 128)
        setBiomeBase(Biome.TAIGA_HILLS, Biome.SAVANNA, 0)
        setBiomeBase(Biome.TAIGA_MOUNTAINS, Biome.SAVANNA, 0)
        setBiomeBase(Biome.ICE_PLAINS, Biome.SAVANNA, 0)
        setBiomeBase(Biome.ICE_PLAINS, Biome.SAVANNA, 128)
        setBiomeBase(Biome.ICE_PLAINS_SPIKES, Biome.SAVANNA, 0)
        setBiomeBase(Biome.MEGA_SPRUCE_TAIGA, Biome.PLAINS, 0)
        setBiomeBase(Biome.MEGA_SPRUCE_TAIGA_HILLS, Biome.PLAINS, 0)
        setBiomeBase(Biome.MEGA_TAIGA, Biome.PLAINS, 0)
        setBiomeBase(Biome.MEGA_TAIGA, Biome.PLAINS, 128)
        setBiomeBase(Biome.MEGA_TAIGA_HILLS, Biome.PLAINS, 0)
        setBiomeBase(Biome.COLD_BEACH, Biome.DESERT, 0)
        setBiomeBase(Biome.COLD_TAIGA, Biome.PLAINS, 0)
        setBiomeBase(Biome.COLD_TAIGA, Biome.PLAINS, 128)
        setBiomeBase(Biome.COLD_TAIGA_HILLS, Biome.DESERT, 0)
        setBiomeBase(Biome.COLD_TAIGA_MOUNTAINS, Biome.DESERT, 0)
        setBiomeBase(Biome.FOREST, Biome.PLAINS, 0)
        setBiomeBase(Biome.ROOFED_FOREST_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.MESA, Biome.PLAINS, 0)
        setBiomeBase(Biome.MESA, Biome.PLAINS, 128)
        setBiomeBase(Biome.MESA_PLATEAU, Biome.PLAINS, 0)
        setBiomeBase(Biome.MESA_PLATEAU, Biome.PLAINS, 128)
        setBiomeBase(Biome.MESA_BRYCE, Biome.PLAINS, 0)
        setBiomeBase(Biome.MESA_PLATEAU_FOREST, Biome.PLAINS, 0)
        setBiomeBase(Biome.MESA_PLATEAU_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.EXTREME_HILLS, Biome.PLAINS, 0)
        setBiomeBase(Biome.EXTREME_HILLS, Biome.DESERT, 128)
        setBiomeBase(Biome.EXTREME_HILLS_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.EXTREME_HILLS_PLUS, Biome.DESERT, 0)
        setBiomeBase(Biome.EXTREME_HILLS_PLUS, Biome.DESERT, 128)
        setBiomeBase(Biome.EXTREME_HILLS_PLUS_MOUNTAINS, Biome.DESERT, 0)
        setBiomeBase(Biome.FROZEN_OCEAN, Biome.PLAINS, 0)
        setBiomeBase(Biome.FROZEN_RIVER, Biome.PLAINS, 0)
        setBiomeBase(Biome.ICE_MOUNTAINS, Biome.PLAINS, 0)
        setBiomeBase(Biome.SWAMPLAND, Biome.PLAINS, 0)
        setBiomeBase(Biome.SWAMPLAND_MOUNTAINS, Biome.PLAINS, 0)

        Logger.getGlobal().info("[UHCMeetup] Finished biome swap for the UHC Meetup world.")
        Logger.getGlobal().info("[UHCMeetup] Starting world generation for the UHC Meetup world.")

        generateNewWorld()
    }

    private fun setBiomeBase(from: Biome, to: Biome, plus: Int)
    {
        BiomeBase.getBiomes()[CraftBlock.biomeToBiomeBase(from).id + plus] = CraftBlock.biomeToBiomeBase(to)
    }
}
