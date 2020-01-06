package za.co.seamlesspay.v1.util.EmvUtil.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Combines a Map with List values to provide simple way to store multiple values for a key.
 */
// Top level class to get rid of 3rd generic collection parameter for more convenient usage.
class Multimap<K, V> extends AbstractMultimap<K, V, List<V>> {
    private final ListType listType;

    public enum ListType {
        /** Aka ArrayList */
        REGULAR,

        /** Aka CopyOnWriteArrayList */
        THREAD_SAFE,

        /** Aka LinkedList */
        LINKED
    }

    public static <K, V> Multimap<K, V> create() {
        return create(ListType.REGULAR);
    }

    public static <K, V> Multimap<K, V> create(ListType listType) {
        return new Multimap<>(new HashMap<K, List<V>>(), listType);
    }

    protected Multimap(Map<K, List<V>> map, ListType listType) {
        super(map);
        this.listType = listType;
        if (listType == null) {
            throw new IllegalArgumentException("List type may not be null");
        }
    }

    protected List<V> createNewCollection() {
        switch (listType) {
            case REGULAR:
                return new ArrayList<>();
            case THREAD_SAFE:
                return new CopyOnWriteArrayList<>();
            case LINKED:
                return new LinkedList<>();
            default:
                throw new IllegalStateException("Unknown list type: " + listType);
        }
    }

}
