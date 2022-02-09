package net.riyaya.battleassist;

import net.riyaya.battleassist.Commands.CommandManager;
import net.riyaya.battleassist.Commands.TabCompleteListener;
import net.riyaya.battleassist.DataBase.Config;
import net.riyaya.battleassist.Listener.PlayerDeadListener;
import net.riyaya.battleassist.Listener.PlayerJoinListener;
import net.riyaya.battleassist.Utils.GameStatus;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public final class BattleAssist extends JavaPlugin {

    public static Config config;
    public static GameStatus gameStatus;
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        config = new Config(getConfig());
        gameStatus = new GameStatus();
        gameStatus.setStatus(false);
        gameStatus.setTime(0);

        startCounting(this);

        registerListener(this);

        getCommand("battle").setExecutor(new CommandManager());
        getCommand("battle").setTabCompleter(new TabCompleteListener());

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    private void startCounting(JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> players = new ArrayList<>();
                if(gameStatus.getStatus()) {
                    if(gameStatus.getTime() <= 0) {
                        startCounting(plugin);
                        return;
                    }

                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                            players.add(player);
                        }
                    }

                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(ChatColor.YELLOW + "§l残り人数" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "§l" + players.size() + ChatColor.GREEN + "§l 残り時間" + ChatColor.WHITE + ": " + ChatColor.GREEN + "§l" + gameStatus.getTime());
                    }

                    if(players.size() <= 1 || gameStatus.getTime() == 0) {
                        stop();
                    }

                    gameStatus.setTime(gameStatus.getTime() - 1);

                    if(config.getMaxTime() - config.getRemoveElytraSeconds() == gameStatus.getTime()) {
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            if(player.getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
                                player.getInventory().getChestplate().setAmount(0);
                            }
                        }
                    }

                    //first border
                    if(config.getMaxTime() - (config.getRemoveElytraSeconds() + 20) == gameStatus.getTime()) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "§l[注意!] ボーダーの第一収縮が始まりました");
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
                        }
                        config.getStartLocation().getWorld().getWorldBorder().setSize(config.getFirstBorder().getSize(), config.getFirstBorder().getShrinkageStartSeconds());
                    }

                    //second border
                    if(config.getMaxTime() - (config.getRemoveElytraSeconds()
                            + 20
                            + config.getFirstBorder().getShrinkageStartSeconds()
                            + config.getFirstBorder().getWaitSeconds()) == gameStatus.getTime()) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "§l[注意!] ボーダーの第一収縮が始まりました");
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
                        }
                        config.getStartLocation().getWorld().getWorldBorder().setSize(config.getSecondBorder().getSize(), config.getSecondBorder().getShrinkageStartSeconds());
                    }

                    //third border
                    if(config.getMaxTime() - (config.getRemoveElytraSeconds()
                            + 20
                            + config.getFirstBorder().getShrinkageStartSeconds()
                            + config.getFirstBorder().getWaitSeconds()
                            + config.getSecondBorder().getShrinkageStartSeconds()
                            + config.getSecondBorder().getWaitSeconds()) == gameStatus.getTime()) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "§l[注意!] ボーダーの最終収縮が始まりました");
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 10, 1);
                        }
                        config.getStartLocation().getWorld().getWorldBorder().setSize(config.getThirdBorder().getSize(), config.getThirdBorder().getShrinkageStartSeconds());
                    }
                }
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    public static void start() {
        config.getStartLocation().getWorld().getWorldBorder().setSize(config.getMaxBorderSize());
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "lc respawnall");

        Bukkit.getScheduler().runTaskLater(BattleAssist.getInstance(), () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                    player.sendTitle(ChatColor.YELLOW + "ゲーム開始まで 3...", "");
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 1);
                }
            }

            Bukkit.getScheduler().runTaskLater(BattleAssist.getInstance(), () -> {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.sendTitle(ChatColor.YELLOW + "ゲーム開始まで 2...", "");
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 1);
                    }
                }

                Bukkit.getScheduler().runTaskLater(BattleAssist.getInstance(), () -> {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                            player.sendTitle(ChatColor.GOLD + "ゲーム開始まで 1...", "");
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 10, 1);
                        }
                    }
                    Bukkit.getScheduler().runTaskLater(BattleAssist.getInstance(), () -> {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (!player.getGameMode().equals(GameMode.CREATIVE) || !player.getGameMode().equals(GameMode.SPECTATOR)) {
                                gameStatus.setStatus(true);
                                gameStatus.resetTime();

                                player.sendTitle(ChatColor.YELLOW + "スタート!!", "");
                                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 0);
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0);
                                player.setGameMode(GameMode.SURVIVAL);
                                player.teleport(config.getStartLocation());
                                player.getInventory().clear();
                                player.getInventory().setChestplate(new ItemStack(Material.ELYTRA));

                            }
                        }
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "§l[注意!] のこり" + config.getRemoveElytraSeconds() + "でエリトラが削除されます。");
                    }, 20L);
                }, 20L);
            }, 20L);
        }, 20L);
    }

    public static void stop() {
        gameStatus.setStatus(false);
        gameStatus.setTime(0);
        config.getStartLocation().getWorld().getWorldBorder().setSize(config.getMaxBorderSize());
        ArrayList<Player> PLAYING = new ArrayList<>();

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getGameMode().equals(GameMode.SURVIVAL)) {
                PLAYING.add(player1);
            }
        }

        Bukkit.broadcastMessage(PLAYING.get(0).getDisplayName());

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                player.sendTitle(ChatColor.GOLD + "§l試合終了～", ChatColor.GREEN + "§lビクトリ : " + PLAYING.get(0).getDisplayName());
            } catch (Exception ignored) {
                player.sendTitle(ChatColor.GOLD + "§l試合終了～", ChatColor.GREEN + "§lビクトリ : なし");
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (config.isAllowTeleportLobby()) {
                player.teleport(config.getSpawnLocation());
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 10, 2);
        }
    }

    private void registerListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeadListener(), plugin);
    }
}
