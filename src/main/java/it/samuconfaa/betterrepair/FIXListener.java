package it.samuconfaa.betterrepair;

import org.bukkit.*;
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
        Inventory gui = Bukkit.createInventory(null, ConfigurationManager.GUIsize(), ConfigurationManager.GUIname());

        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemMeta Manvil = anvil.getItemMeta();
        Manvil.setDisplayName(ConfigurationManager.anvilName());
        List<String> lore = new ArrayList<>();
        lore.add(ConfigurationManager.lore1());
        lore.add(ConfigurationManager.lore2());
        lore.add(ConfigurationManager.lore3() + ConfigurationManager.price());
        Manvil.setLore(lore);
        anvil.setItemMeta(Manvil);
        gui.setItem(ConfigurationManager.anvilPos(), anvil);

        ItemStack vetro = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) ConfigurationManager.glassColor());
        ItemMeta meta = vetro.getItemMeta();
        meta.setDisplayName(ConfigurationManager.glassName());
        vetro.setItemMeta(meta);

        for(int i = 0; i < ConfigurationManager.GUIsize(); i++ ){
            if(i != ConfigurationManager.anvilPos()){
                gui.setItem(i, vetro);
            }
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem().getItemStack();

        if (item.getType() == Material.ANVIL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory inv = e.getClickedInventory();
        if(inv.getHolder()==null && ConfigurationManager.GUIname().equals(inv.getName())){
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            Location location = player.getLocation();
            int slot = e.getRawSlot();
            if(slot == ConfigurationManager.anvilPos()) {
                if (plugin.checkMoney(player) > ConfigurationManager.price()) {
                    repairAllItems(player);
                    player.playSound(location, Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
                }else {
                    player.sendMessage(ConfigurationManager.nomoney());
                    player.playSound(location, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
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
        plugin.removeMoney(player, ConfigurationManager.price());
    }
}