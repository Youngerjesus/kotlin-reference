package kotlins.app.generics;

import java.util.Collection;

public interface TestGeneric {
    default void copyAll(Collection<Object> to, Collection<String> from) {
        to.addAll(from);
    }
}
