package cn.original.ide.index;

import oms.ability.ModAbility;
import oms.io.File;

public class Path extends ModAbility {
    private String appName = null;
    private String appPackage = null;


    public static final String APPLICATION_PATH = "%原子灵动/应用/";

    private Path(String string) {

    }

    public Path with(String string) {
        return new Path(string);
    }

    public Path setName(String string) {
        this.appName = string;
        return this;
    }

    public Path setPackage(String string) {
        this.appPackage = string;
        return this;
    }

    public Path initResources() {
        File layout = new File(APPLICATION_PATH + appName + "/src/res/layout/");
        layout.mkdirs();
        File drawable = new File(APPLICATION_PATH + appName + "/src/res/drawable/");
        drawable.mkdirs();
        File values = new File(APPLICATION_PATH + appName + "/src/res/values/");
        values.mkdirs();
        File values_night = new File(APPLICATION_PATH + appName + "/src/res/values_night/");
        return this;
    }

    public String getLayoutPath() {
        return null;
    }


}
