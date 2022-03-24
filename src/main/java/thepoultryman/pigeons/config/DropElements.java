package thepoultryman.pigeons.config;

import thepoultryman.pigeons.Pigeons;

public class DropElements {
    private final DropData city;
    private final DropData antwerpSmerleBrown;

    public DropElements(DropData city, DropData antwerpSmerleBrown) {
        this.city = city;
        this.antwerpSmerleBrown = antwerpSmerleBrown;
    }

    public DropData getCity() {
        return this.city;
    }

    public DropData getAntwerpSmerle(String type) {
        switch (type.toLowerCase()) {
            case "brown" -> {return this.antwerpSmerleBrown;}
            default -> {Pigeons.LOGGER.warn("There was a mistake when trying to get the 'Antwerp Smerle' type for config loading. " +
                    "Please check your config file for errors. The config for the 'City' pigeon was returned instead.");
                return this.antwerpSmerleBrown;
            }
        }
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
