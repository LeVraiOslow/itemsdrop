package com.itemsdrop.plugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsDropTask extends BukkitRunnable {

    private final ItemsDropPlugin plugin;
    private final Random random = new Random();

    public ItemsDropTask(ItemsDropPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        if (players.isEmpty()) {
            return;
        }

        int amount = Math.max(1, plugin.getConfig().getInt("amount-per-drop", 1));
        String broadcastTemplate = plugin.getConfig().getString("broadcast-message", "");

        for (Player player : players) {
            Material material = pickRandomMaterial();
            if (material == null) {
                continue;
            }

            ItemStack item = new ItemStack(material, amount);
            player.getInventory().addItem(item);

            if (broadcastTemplate != null && !broadcastTemplate.isEmpty()) {
                String message = broadcastTemplate
                        .replace("%player%", player.getName())
                        .replace("%item%", formatMaterialName(material));
                plugin.getServer().broadcastMessage(message);
            }
        }
    }

    private Material pickRandomMaterial() {
        List<String> configured = plugin.getConfig().getStringList("items");

        if (configured != null && !configured.isEmpty()) {
            List<Material> valid = new ArrayList<>();
            for (String name : configured) {
                Material m = Material.matchMaterial(name);
                if (m != null && m.isItem()) {
                    valid.add(m);
                }
            }
            if (!valid.isEmpty()) {
                return valid.get(random.nextInt(valid.size()));
            }
            plugin.getLogger().warning("Aucun item valide trouve dans la liste 'items' du config.yml, utilisation d'un item aleatoire global.");
        }

        // Fallback : un item completement aleatoire parmi tous les Material valides
        Material[] all = Material.values();
        Material candidate;
        int attempts = 0;
        do {
            candidate = all[random.nextInt(all.length)];
            attempts++;
        } while ((!candidate.isItem() || candidate.isAir() || candidate.isLegacy()) && attempts < 200);

        return candidate.isItem() ? candidate : Material.APPLE;
    }

    private String formatMaterialName(Material material) {
        String raw = material.name().replace('_', ' ').toLowerCase();
        return raw.substring(0, 1).toUpperCase() + raw.substring(1);
    }
}
