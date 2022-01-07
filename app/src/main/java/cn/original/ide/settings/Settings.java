package cn.original.ide.settings;

import android.content.Context;

import oms.io.File;
import oms.io.JSON;

public class Settings {
    private static Settings settings = null;
    private final Context context;
    private final File file;
    private JSON set;

    Settings(Context context) {
        this.context = context;
        File file2 = new File("@app/settings/init.json");
        this.file = new File("$settings/init.json");
        if (!this.file.exists()) {
            file2.copy("$settings/init.json");
        }
        this.set = new JSON(file.read());
        //System.out.println(file.read());
    }

    public static Settings getInstance(Context context) {
        return new Settings(context);
    }

    public static void clear() {
        settings = null;
        System.gc();
    }

    public void set(String hand, String key, boolean t) {
        set.getJson(hand).put(key, t);
        update();
    }

    public void set(String hand, String key, int t) {
        set.getJson(hand).put(key, t);
        update();
    }

    public void set(String hand, String key, String t) {
        set.getJson(hand).put(key, t);
        update();
    }

    public boolean getBoolean(String hand, String key) {
        return set.getJson(hand).getBoolean(key);
    }

    public int getInt(String hand, String key) {
        return set.getJson(hand).getInt(key);
    }

    public String getString(String hand, String key) {
        return set.getJson(hand).getString(key);
    }

    void update() {
        this.file.write(set.toString());
        System.gc();
    }

}
