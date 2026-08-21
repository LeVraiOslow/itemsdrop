package com.itemsdrop.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ItemsDropPlugin extends JavaPlugin {

    private ItemsDropTask task;
    private boolean running = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ItemsDropCommand executor = new ItemsDropCommand(this);
        getCommand("itemsdrop").setExecutor(executor);
        getCommand("itemsdrop").setTabCompleter(executor);

        getLogger().info("ItemsDrop active. Utilise /itemsdrop start pour lancer la distribution.");
    }

    @Override
    public void onDisable() {
        stopTask();
    }

    /**
     * Demarre la tache de distribution si elle n'est pas deja en cours.
     * @return true si demarree, false si deja active
     */
    public boolean startTask() {
        if (running) {
            return false;
        }
        long intervalTicks = Math.max(1, getConfig().getInt("interval-seconds", 10)) * 20L;
        task = new ItemsDropTask(this);
        task.runTaskTimer(this, intervalTicks, intervalTicks);
        running = true;
        return true;
    }

    /**
     * Arrete la tache de distribution si elle est en cours.
     * @return true si arretee, false si elle n'etait pas active
     */
    public boolean stopTask() {
        if (!running || task == null) {
            return false;
        }
        task.cancel();
        task = null;
        running = false;
        return true;
    }

    public boolean isRunning() {
        return running;
    }
}
