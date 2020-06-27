package me.dylan.siphon.listeners;

import me.dylan.siphon.Siphon;
import me.dylan.siphon.locale.Locale;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.WorldServer;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DeathListener implements Listener {

    private final Siphon siphon;
    private final Random random;

    public DeathListener(Siphon siphon) {
        this.siphon = siphon;

        this.random = new Random();
    }

    private static final List<Enchantment> ENCHANTMENT_LIST = new ArrayList<>(
            Arrays.asList(Enchantment.ARROW_DAMAGE,
                    Enchantment.DAMAGE_ALL,
                    Enchantment.PROTECTION_ENVIRONMENTAL,
                    Enchantment.PROTECTION_PROJECTILE,
                    Enchantment.DURABILITY,
                    Enchantment.KNOCKBACK));

    @EventHandler
    public void on(PlayerDeathEvent event) {
        if (! siphon.isSiphon()) return;

        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (killer == null) return;

        killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 1));
        killer.giveExpLevels(2);

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) book.getItemMeta();
        if (enchantmentStorageMeta == null) return;

        enchantmentStorageMeta.addStoredEnchant(ENCHANTMENT_LIST.get(random.nextInt(ENCHANTMENT_LIST.size() - 1)), 1, false);
        book.setItemMeta(enchantmentStorageMeta);

        giveItem(player, book);
    }


    /* LeonTGs Utils */
    private void giveItem(Player player, ItemStack... stacks) {
        Validate.notNull(player, "Player cannot be null.");
        Validate.notNull(stacks, "ItemStack array cannot be null.");

        Map<Integer, ItemStack> leftOvers = player.getInventory().addItem(stacks);

        if (leftOvers.isEmpty()) return;

        player.sendMessage(Locale.PREFIX + "Inventory full! Item(s) were dropped on the ground.");
        Location loc = player.getLocation();

        for (ItemStack leftOver : leftOvers.values()) {
            dropItem(loc, leftOver);
        }
    }

    private void dropItem(Location dropLoc, ItemStack toDrop) {
        Validate.notNull(dropLoc, "Location cannot be null.");
        Validate.notNull(toDrop, "ItemStack cannot be null.");

        BlockPosition blockPosition = new BlockPosition(dropLoc.getX(), dropLoc.getY(), dropLoc.getZ());
        WorldServer worldServer = ((CraftWorld) dropLoc.getWorld()).getHandle();

        // 2 ticks later, otherwise the block pushes them to the side?
        Bukkit.getScheduler().runTaskLater(siphon, () -> net.minecraft.server.v1_14_R1.Block.a(worldServer, blockPosition, CraftItemStack.asNMSCopy(toDrop)), 2L);
    }
}
