package thepoultryman.pigeons.config;

import thepoultryman.pigeons.Pigeons;

import java.util.HashMap;

public class DropConfigFormatOld {
    private final HashMap<String, DropData> specialDrops = new HashMap<>();
    private final int dropChanceDay;
    private final int dropChanceNight;
    private final int specialDropChance;

    public DropConfigFormatOld(DropData city, DropData antwerpSmerleBrown, DropData antwerpSmerleGray, DropData egyptianSwift, int dropChanceDay, int dropChanceNight, int specialDropChance) {
        specialDrops.put("city", city);
        specialDrops.put("antwerp_smerle_brown", antwerpSmerleBrown);
        specialDrops.put("antwerp_smerle_gray", antwerpSmerleGray);
        specialDrops.put("egyptian_swift", egyptianSwift);

        this.dropChanceDay = dropChanceDay;
        this.dropChanceNight = dropChanceNight;
        this.specialDropChance = specialDropChance;
    }

    public DropData getCity() {
        return this.specialDrops.get("city");
    }

    public DropData getAntwerpSmerle(String type) {
        switch (type.toLowerCase()) {
            case "brown" -> {return this.specialDrops.get("antwerp_smerle_brown");}
            case "gray" -> {return this.specialDrops.get("antwerp_smerle_gray");}
            default -> {Pigeons.LOGGER.warn("There was a mistake when trying to get the 'Antwerp Smerle' type for config loading. " +
                    "Please check your config file for errors. The config for the 'City' pigeon was returned instead.");
                return this.specialDrops.get("antwerp_smerle_brown");
            }
        }
    }

    public DropData getEgyptianSwift() {
        return this.specialDrops.get("egyptian_swift");
    }

    public int getDropChanceDay() {
        return this.dropChanceDay;
    }

    public int getDropChanceNight() {
        return this.dropChanceNight;
    }

    public int getSpecialDropChance() {
        return this.specialDropChance;
    }

    public static class DropData {
        private final String item;
        private final int count;

        public DropData(String item, int count) {
            this.item = item;
            this.count = count;
        }

        public String getItem() {
            return this.item;
        }

        public int getCount() {
            return this.count;
        }
    }
}
