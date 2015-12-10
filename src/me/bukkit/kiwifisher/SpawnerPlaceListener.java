package me.bukkit.kiwifisher;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author KiwiFisher
 */
public class SpawnerPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        if (event.getBlockPlaced().getType().equals(Material.MOB_SPAWNER)) {

            CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlockPlaced().getState();
            try {
                creatureSpawner.setCreatureTypeByName(event.getItemInHand().getItemMeta().getLore().get(0));
            } catch (Exception e) {} //Does nothing. Will error if vanilla spawner is placed since there is no lore.
            creatureSpawner.update();

        }

    }

}
