package mcjty.lib.typed;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A Type object represents a given type.
 */
public final class Type<V> {

    // Root
    public static final Type<Object> OBJECT = new Type<>(Object.class);

    // Basic
    public static final Type<Integer> INTEGER = create(Integer.class);
    public static final Type<Double> DOUBLE = create(Double.class);
    public static final Type<Long> LONG = create(Long.class);
    public static final Type<String> STRING = create(String.class);
    public static final Type<Boolean> BOOLEAN = create(Boolean.class);
    public static final Type<BlockPos> BLOCKPOS = create(BlockPos.class);

    @Nonnull private final Class<V> type;

    private Type(@Nonnull final Class<V> type) {
        this.type = type;
    }

    @Nonnull
    public static <V> Type<V> create(@Nonnull final Class<? super V> type) {
        return new Type<>((Class<V>) type);
    }

    @Nonnull
    public Class<V> getType() {
        return type;
    }

    public boolean isA(Object b) {
        return type.isInstance(b);
    }

    @Nonnull
    public List<V> convert(@Nonnull List<?> list) {
        for(Object o : list) {
            if(o != null && !type.isInstance(o)) {
                throw new ClassCastException("Cannot cast List<? super " + o.getClass().getName() + "> to List<" + type.getName() + ">");
            }
        }
        return (List<V>) list;
    }

    public V convert(Object o) {
        return type.cast(o);
    }

    @Override
    public String toString() {
        return "Type(" + getType().getSimpleName() + ')';
    }
}
