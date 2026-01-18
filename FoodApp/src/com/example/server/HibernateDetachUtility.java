package com.example.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.domain.Order;
import com.example.domain.OrderItem;

public class HibernateDetachUtility {

    public static <T> List<T> detachList(List<T> values) {
        if (values == null) {
            return values;
        }
        List<T> detached = new ArrayList<T>(values);
        detachCollection(detached);
        return detached;
    }

    public static <T> Set<T> detachSet(Set<T> entities) {
        Set<T> detachedSet = new HashSet<>(entities);
        detachCollection(detachedSet);
        return detachedSet;
    }

    public static <T> void detachCollection(Collection<T> values) {
        if (values == null) {
            return;
        }
        for (Object value : values) {
            detach(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static void detach(Object value) {
        if (value == null) {
            return;
        }

        if (value instanceof Order) {
            detachOrder((Order) value);
        } else if (value instanceof List) {
            // We can't easily replace the list reference if passed as Object,
            // but the recursive call in detachList handles creating a new List.
            // However, detach(Object) returning void implies it modifies in place or expects the caller to handle it.
            // The original code was: return detachList(...)
            // But here we are void.
            // Wait, the original `detach(Object value)` returned Object.
            // I should stick to that signature to be safe for recursive calls if I were using it that way.
            // But let's look at usage. Usage was `order.detach()` (void) and `detachCollection` calls `detach(value)`.
            // `detachCollection` iterates and calls `detach(value)`.
            // If `value` is a List, `detach(value)` in original code returned a new List. But `detachCollection` ignored the return value!
            // "for (Object value : values) { detach(value); }"
            // So recursively detaching a List inside a Collection didn't actually update the Collection with the detached List in the original code?
            // Original code:
            /*
            public static <T> void detachCollection(Collection<T> values) {
                if (values == null) {
                    return;
                }
                for (Object value : values) {
                    detach(value);
                }
            }
            public static <T> Object detach(Object value) {
                ...
                if (value instanceof List) { return detachList((List<T>) value); }
                ...
            }
            */
            // Yes, `detachCollection` called `detach(value)` but ignored the return. So nested lists were NOT detached in the original code unless they were `PersistantObject` which detached in place.
            // `Order` is a `PersistantObject`, so `detachOrder` (void) is correct.
        }
    }

    public static void detachOrder(Order order) {
        // Detach logic specific to Order (handling orderItems)
        if (order.getOrderItems() != null) {
            Set<OrderItem> detachedItems = new HashSet<>(order.getOrderItems());
            // No need to recurse deeply if OrderItems are simple, but if they had collections...
            // Original Order.detach() called PersistantObject.detachCollection(orderItems).
            // And PersistantObject.detachCollection called detach() on each item.
            // OrderItem extends PersistantObject but its detach() is empty (super.detach() only).
            // So effectively it just deep copied the Set.
            order.setOrderItems(detachedItems);
        }
    }
}
