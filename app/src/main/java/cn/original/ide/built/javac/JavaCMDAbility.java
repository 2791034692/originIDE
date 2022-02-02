package cn.original.ide.built.javac;

import java.util.Arrays;

import cn.original.ide.module.ability.CMDAbility;
import cn.original.ide.module.content.Application;

public class JavaCMDAbility extends CMDAbility {
    public JavaCMDAbility(Application application) {
        super(application);
    }

    @Override
    public void main(String[] strings) {
        System.out.print(Arrays.toString(strings));
    }

    @Override
    public String getName() {
        return "javac";
    }
}
