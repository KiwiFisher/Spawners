package me.bukkit.kiwifisher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author KiwiFisher
 */
public class SpawnerClickListener implements Listener {

    private CreatureSpawner creatureSpawner;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                event.getPlayer().getItemInHand().getType() == Material.getMaterial(Spawners.config.getString("tool")) &&
                event.getClickedBlock().getType().equals(Material.MOB_SPAWNER) &&
                event.getPlayer().hasPermission("spawners.gui")) {

            this.setCreatureSpawner((CreatureSpawner) event.getClickedBlock().getState());
            SpawnersGUI GUI = new SpawnersGUI(event.getPlayer(), creatureSpawner);
            GUI.makeGUI();

        }

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) &&
                event.getItem().getType().equals(Material.getMaterial(Spawners.config.getString("tool"))) &&
                event.getClickedBlock().getType().equals(Material.MOB_SPAWNER) && Spawners.hasEconomy() &&
                ((Spawners.config.getBoolean("tool-requires-sliktouch") && event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) ||
                        ((!Spawners.config.getBoolean("tool-requires-sliktouch")) || event.getPlayer().hasPermission("spawners.bypasssilktouch")))) {

            this.setCreatureSpawner((CreatureSpawner) event.getClickedBlock().getState());
            event.getPlayer().sendMessage(ChatColor.YELLOW + "Cost to break this spawner: " + ChatColor.GOLD + "$" + Spawners.config.getInt("prices." + creatureSpawner.getCreatureTypeName().toLowerCase() + ".break"));
        }
    }

    public CreatureSpawner getCreatureSpawner() {
        return this.creatureSpawner;
    }

    public void setCreatureSpawner(CreatureSpawner creatureSpawner) {
        this.creatureSpawner = creatureSpawner;
    }

    public void updateSpawnerType(SpawnerType type) {
        getCreatureSpawner().setSpawnedType(type.getMobType());
    }

}
