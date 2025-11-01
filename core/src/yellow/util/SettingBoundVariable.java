package yellow.util;

import arc.*;
import arc.func.*;

/** A variable bound to a setting entry. */
public class SettingBoundVariable<T>{
    private final String name;
    private final T def;

    public SettingBoundVariable(String name, T def){
        this(name, def, false);
    }

    public SettingBoundVariable(String name, T def, boolean applyDefs){
        this.name = name;
        this.def = def;

        if(!(def instanceof Float || def instanceof Integer || def instanceof Boolean || def instanceof Long
                || def instanceof String || def instanceof byte[]))
            throw new IllegalArgumentException("Invalid object for setting bound variable '" + name + "'");

        if(applyDefs && !Core.settings.has(name)) Core.settings.put(name, def);
    }

    public static <T> SettingBoundVariable<T> with(String name, T def){
        return new SettingBoundVariable<>(name, def);
    }

    public T get(){
        return SafeSettings.get(name, def);
    }

    public T get(T altDef){
        return SafeSettings.get(name, altDef);
    }

    public SettingBoundVariable<T> set(T object){
        Core.settings.put(name, object);
        return this;
    }

    public SettingBoundVariable<T> set(Func<T, T> prev){
        return set(prev.get(get()));
    }


    public static class FloatSetting extends SettingBoundVariable<Float>{
        public FloatSetting(String name, Float def){
            super(name, def);
        }

        public FloatSetting(String name, Float def, boolean applyDefs){
            super(name, def, applyDefs);
        }

        @Override
        public FloatSetting set(Float object){
            return (FloatSetting) super.set(object);
        }

        @Override
        public FloatSetting set(Func<Float, Float> prev){
            return (FloatSetting) super.set(prev);
        }
    }

    public static class IntSetting extends SettingBoundVariable<Integer>{
        public IntSetting(String name, Integer def){
            super(name, def);
        }

        public IntSetting(String name, Integer def, boolean applyDefs){
            super(name, def, applyDefs);
        }

        @Override
        public IntSetting set(Integer object){
            return (IntSetting) super.set(object);
        }

        @Override
        public IntSetting set(Func<Integer, Integer> prev){
            return (IntSetting) super.set(prev);
        }
    }

    public static class BooleanSetting extends SettingBoundVariable<Boolean>{
        public BooleanSetting(String name, Boolean def){
            super(name, def);
        }

        public BooleanSetting(String name, Boolean def, boolean applyDefs){
            super(name, def, applyDefs);
        }

        @Override
        public BooleanSetting set(Boolean object){
            return (BooleanSetting) super.set(object);
        }

        @Override
        public BooleanSetting set(Func<Boolean, Boolean> prev){
            return (BooleanSetting) super.set(prev);
        }
    }

    public static class LongSetting extends SettingBoundVariable<Long>{
        public LongSetting(String name, Long def){
            super(name, def);
        }

        public LongSetting(String name, Long def, boolean applyDefs){
            super(name, def, applyDefs);
        }

        @Override
        public LongSetting set(Long object){
            return (LongSetting) super.set(object);
        }

        @Override
        public LongSetting set(Func<Long, Long> prev){
            return (LongSetting) super.set(prev);
        }
    }

    public static class StringSetting extends SettingBoundVariable<String>{
        public StringSetting(String name, String def){
            super(name, def);
        }

        public StringSetting(String name, String def, boolean applyDefs){
            super(name, def, applyDefs);
        }

        @Override
        public StringSetting set(String object){
            return (StringSetting) super.set(object);
        }

        @Override
        public StringSetting set(Func<String, String> prev){
            return (StringSetting) super.set(prev);
        }
    }

    public static class ByteArraySetting extends SettingBoundVariable<byte[]>{
        public ByteArraySetting(String name, byte[] def){
            super(name, def);
        }

        public ByteArraySetting(String name, byte[] def, boolean applyDefs){
            super(name, def, applyDefs);
        }

        @Override
        public ByteArraySetting set(byte[] object){
            return (ByteArraySetting) super.set(object);
        }

        @Override
        public ByteArraySetting set(Func<byte[], byte[]> prev){
            return (ByteArraySetting) super.set(prev);
        }
    }
}
