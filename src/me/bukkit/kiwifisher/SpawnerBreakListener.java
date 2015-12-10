package me.bukkit.kiwifisher;

import org.bukkit.*;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * @author KiwiFisher
 */
public class SpawnerBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.MOB_SPAWNER) &&
                event.getPlayer().getItemInHand().getType().equals(Material.getMaterial(Spawners.config.getString("tool"))) &&
                event.getPlayer().hasPermission("spawners.break") &&
                event.getPlayer().getGameMode() != GameMode.CREATIVE &&
                ((Spawners.config.getBoolean("tool-requires-sliktouch")) && event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH) ||
                        (!Spawners.config.getBoolean("tool-requires-sliktouch")) || event.getPlayer().hasPermission("spawners.bypasssilktouch"))) {

            Location location = event.getBlock().getLocation();
            CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();

            event.setExpToDrop(0);

            ItemStack spawnerItem =  new ItemStack(event.getBlock().getType(), 1);
            ItemMeta spawnerMeta = spawnerItem.getItemMeta();
            spawnerMeta.setDisplayName(ChatColor.RESET + creatureSpawner.getCreatureTypeName() + " Spawner");

            ArrayList lore = new ArrayList();
            lore.add(creatureSpawner.getCreatureTypeName());
            spawnerMeta.setLore(lore);
            spawnerItem.setItemMeta(spawnerMeta);

            if (Spawners.hasEconomy()) {
                int cost = Spawners.config.getInt("prices." + creatureSpawner.getCreatureTypeName().toLowerCase() + ".break");

                if (Spawners.economy.getBalance(event.getPlayer()) >= cost) {

                    location.getWorld().dropItemNaturally(location, spawnerItem);
                    Spawners.economy.withdrawPlayer(event.getPlayer(), cost);
                    event.getPlayer().sendMessage(ChatColor.YELLOW + "Removed $" + cost + " from you balance");

                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "You don't have enough money to break that spawner!");
                    event.setCancelled(true);
                }
            } else {
                location.getWorld().dropItemNaturally(location, spawnerItem);
            }



        }
    }

}
