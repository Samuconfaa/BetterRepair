package it.samuconfaa.betterrepair;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class FIXListener implements Listener{
    private final BetterRepair plugin;

    public FIXListener(BetterRepair plugin) {
        this.plugin = plugin;
    }

    public void openGUI(Player player){
        Inventory gui = Bukkit.createInventory(null, 27, "Fabbro");

        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemMeta Manvil = anvil.getItemMeta();
        Manvil.setDisplayName("FABBRO");
        Manvil.setLore(Collections.singletonList("clicca qua per riparare"));
        anvil.setItemMeta(Manvil);
        gui.setItem(15, anvil);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory inv = e.getClickedInventory();
        if(inv.getHolder()==null && "FABBRO".equals(inv.getName())){
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            int slot = e.getRawSlot();
            if(slot == 15){
                repairAllItems(player);
            }


        }


    }
    private void repairAllItems(Player player) {
        for (ItemStack item : player.getInventory()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) 0);
            }
        }
        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        if (offHandItem != null && offHandItem.getType() != Material.AIR) {
            offHandItem.setDurability((short) 0);
        }
        plugin.removeMoney(player, soldi); //soldi da mettere nel config
    }
}