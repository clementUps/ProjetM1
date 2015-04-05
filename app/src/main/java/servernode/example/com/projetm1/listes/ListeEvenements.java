package servernode.example.com.projetm1.listes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ListeEvenements {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Evenement> ITEMS = new ArrayList<Evenement>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Evenement> ITEM_MAP = new HashMap<String, Evenement>();

    static {
        // Add 3 sample items.
        addItem(new Evenement("1", "Lumière"));
        addItem(new Evenement("2", "Item 2"));
        addItem(new Evenement("3", "Item 3"));
    }

    private static void addItem(Evenement item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Evenement {
        public String id;
        public String content;

        public Evenement(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
