package cn.original.ide.launch.code;

import android.content.res.Resources;
import android.util.ArrayMap;

import java.util.Objects;

import dalvik.system.DexClassLoader;

public class ClassLoaderRegistry {
    private DexClassLoader dexClassLoader;
    private Resources resources;
    private ArrayMap<String, Integer> sparseArray = new ArrayMap<>();

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public void setDexClassLoader(DexClassLoader dexClassLoader) {
        this.dexClassLoader = dexClassLoader;
    }

    @Override
    public boolean equals(Object object) {
        return sparseArray.containsKey(object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dexClassLoader, sparseArray);
    }

}
