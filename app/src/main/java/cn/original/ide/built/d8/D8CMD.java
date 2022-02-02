package cn.original.ide.built.d8;

import com.android.tools.r8.D8;

import cn.original.ide.module.ability.CMDAbility;
import cn.original.ide.module.content.Application;

public class D8CMD extends CMDAbility {
    public D8CMD(Application application) {
        super(application);
    }

    @Override
    public void main(String[] strings) {
        D8.main(strings);
    }

    @Override
    public String getName() {
        return "D8";
    }
}
