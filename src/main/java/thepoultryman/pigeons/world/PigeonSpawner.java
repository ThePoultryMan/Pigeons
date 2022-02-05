package thepoultryman.pigeons.world;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Spawner;
import thepoultryman.pigeons.Pigeons;
import thepoultryman.pigeons.entity.PigeonEntity;

import java.util.Random;

public class PigeonSpawner implements Spawner {
    private int ticksUntilSpawn;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (spawnAnimals && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)){
            --ticksUntilSpawn;
            Random random = world.getRandom();
            if (ticksUntilSpawn <= 0) {
                ticksUntilSpawn = 125;
                world.getPlayers().forEach(serverPlayerEntity -> {
                    int x = (8 + random.nextInt(32)) * (random.nextBoolean() ? -1 : 1);
                    int y = (random.nextInt(4)) * (random.nextBoolean() ? -1 : 1);
                    int z = (8 + random.nextInt(32)) * (random.nextBoolean() ? -1 : 1);
                    BlockPos pos = serverPlayerEntity.getBlockPos().add(x, y, z);

                    if (world.getBiome(pos).getCategory().equals(Biome.Category.SAVANNA) || world.getBiome(pos).getCategory().equals(Biome.Category.PLAINS) ||world.getBiome(pos).getCategory().equals(Biome.Category.FOREST) && random.nextInt(3) == 0) {
                        spawn(world, pos);
                    }
                });
            }
        }

        return 0;
    }

    private void spawn(ServerWorld world, BlockPos pos) {
        PigeonEntity pigeonEntity = Pigeons.PIGEON_ENTITY_TYPE.create(world);
        if (pigeonEntity != null) {
            pigeonEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, (EntityData) null, (NbtCompound) null);
            pigeonEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            world.spawnEntityAndPassengers(pigeonEntity);
        }
    }
}
