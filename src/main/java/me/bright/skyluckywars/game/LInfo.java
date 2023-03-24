package me.bright.skyluckywars.game;

public enum LInfo  {
    KILLS("kills"),
    DEFAULTSET("defaultSet"),
    LUCKY_BLOCKS_BROKEN("luckyblocks"),
    MOB_OWNER("mob_owner"),
    HAS_PEARL("has_pearl");

    private String key;
    LInfo(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
