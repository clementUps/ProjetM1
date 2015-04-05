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
public class ListeActions {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Action> ITEMS = new ArrayList<Action>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Action> ITEM_MAP = new HashMap<String, Action>();

    static {
        // Add 3 sample items.
        addItem(new Action("1", "Envoie d'un mail"));
        addItem(new Action("2", "Item 2"));
        addItem(new Action("3", "Item 3"));
    }

    private static void addItem(Action item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Action {
        public String id;
        public String content;

        public Action(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
