package me.bukkit.kiwifisher;

import org.bukkit.entity.EntityType;

/**
 * @author KiwiFisher
 */
public enum SpawnerType {

    CHICKEN (("prices.chicken.change"), "spawners.chicken", 93, EntityType.CHICKEN),
    COW (("prices.cow.change"), "spawners.cow", 92, EntityType.COW),
    MUSHROOM_COW (("prices.mushroomcow.change"), "spawners.mushroomcow", 96, EntityType.MUSHROOM_COW),
    PIG (("prices.pig.change"), "spawners.pig", 90, EntityType.PIG),
    RABBIT (("prices.rabbit.change"), "spawners.rabbit", 101, EntityType.RABBIT),
    SHEEP (("prices.sheep.change"), "spawners.sheep", 91, EntityType.SHEEP),
    CAVE_SPIDER (("prices.cavespider.change"), "spawners.cave_]spider", 59, EntityType.CAVE_SPIDER),
    ENDERMAN (("prices.enderman.change"), "spawners.enderman", 58, EntityType.ENDERMAN),
    SPIDER (("prices.spider.change"), "spawners.spider", 52, EntityType.SPIDER),
    PIG_ZOMBIE (("prices.pigzombie.change"), "spawners.pigzombie", 57, EntityType.PIG_ZOMBIE),
    BLAZE (("prices.blaze.change"), "spawners.blaze", 61, EntityType.BLAZE),
    GHAST (("prices.ghast.change"), "spawners.ghast", 56, EntityType.GHAST),
    MAGMA_CUBE (("prices.lavaslime.change"), "spawners.lavaslime", 62, EntityType.MAGMA_CUBE),
    SILVERFISH (("prices.silverfish.change"), "spawners.silverfish", 60, EntityType.SILVERFISH),
    SKELETON (("prices.skeleton.change"), "spawners.skeleton", 51, EntityType.SKELETON),
    SLIME (("prices.slime.change"), "spawners.slime", 55, EntityType.SLIME),
    WITCH (("prices.witch.change"), "spawners.witch", 66, EntityType.WITCH),
    ZOMBIE (("prices.zombie.change"), "spawners.zombie", 54, EntityType.ZOMBIE);

    private String price;
    private String permission;
    private int byteData;
    private EntityType mobType;

    SpawnerType(String price, String permission, int byteData, EntityType mobType) {

        this.price = price;
        this.permission = permission;
        this.byteData = byteData;
        this.mobType = mobType;

    }

    public String getPrice() {
        return this.price;
    }

    public String getPermission() { return this.permission; }

    public int getByteData() { return this.byteData; }

    public EntityType getMobType() { return this.mobType; }
}
