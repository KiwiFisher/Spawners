package me.bukkit.kiwifisher;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

/**
 * @author KiwiFisher
 */
public class SpawnersGUI implements Listener {

    public Inventory myInventory = null;
    public CreatureSpawner creatureSpawner;
    private Player player;

    public SpawnersGUI() {

    }

    public SpawnersGUI(Player player, CreatureSpawner spawner) {

        this.player = player;
        this.creatureSpawner = spawner;
        creatureSpawner = spawner;

    }

    public void makeGUI() {
        int size = -1;

        for (SpawnerType type : SpawnerType.values()) {

            if (player.hasPermission(type.getPermission())) {
                size++;
            }
        }

        int rows = (size / 9) + 1;

        myInventory = Bukkit.createInventory(null, rows * 9, "Change Spawners");

        int slot = 0;

        for (SpawnerType type : SpawnerType.values()) {

            if (((player.hasPermission(type.getPermission())) || ((player.hasPermission("spawners.basic_group")) && (
                    type == SpawnerType.ZOMBIE ||
                            type == SpawnerType.SKELETON ||
                            type == SpawnerType.BLAZE ||
                            type == SpawnerType.PIG ||
                            type == SpawnerType.COW ||
                            type == SpawnerType.MUSHROOM_COW ||
                            type == SpawnerType.ENDERMAN ||
                            type == SpawnerType.PIG_ZOMBIE
            ))) && creatureSpawner.getSpawnedType() != type.getMobType()) {

                ItemStack item = new ItemStack(Material.MONSTER_EGG, 1, (byte) type.getByteData());

                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + WordUtils.capitalizeFully(type.name().replaceAll("[_]", " ")));

                ArrayList lore = new ArrayList();

                if (Spawners.hasEconomy()) {

                    lore.add(ChatColor.YELLOW + "Price: " + ChatColor.GOLD + "$" + (int) Spawners.config.getInt(type.getPrice()));
                    itemMeta.setLore(lore);

                }

                item.setItemMeta(itemMeta);
                myInventory.setItem(slot, (item));

                slot++;
            }

            player.openInventory(myInventory);

        }
    }

    //=============================================================================================================================
    //============== BELOW IS THE LISTENER ========================================================================================
    //=============================================================================================================================

    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked in

        if (myInventory != null && inventory.getName().equals(myInventory.getName())) {

            event.setCancelled(true);

            if (Spawners.hasEconomy()) {

                int cost;

                try {
                    cost = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0)).replaceAll("[^0-9]", ""));

                    if (Spawners.economy.getBalance(player) >= cost) {
                        creatureSpawner.setSpawnedType(EntityType.fromId(clicked.getDurability()));
                        creatureSpawner.update();
                        Spawners.economy.withdrawPlayer(player, cost);
                        player.sendMessage(ChatColor.YELLOW + "Removed $" + cost + " from you balance");
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough money to change this spawner!");
                    }

                } catch (Exception e) {
                    event.setCancelled(true);
                }

            } else {

                creatureSpawner.setSpawnedType(EntityType.fromId(clicked.getDurability()));
                creatureSpawner.update();

            }

            player.closeInventory();

        }
    }
}
