package cn.original.ide.built.d8;

import com.android.tools.r8.R8;

import cn.original.ide.module.ability.CMDAbility;
import cn.original.ide.module.content.Application;

public class R8CMD extends CMDAbility {

    public R8CMD(Application application) {
        super(application);
    }

    @Override
    public void main(String[] strings) {
        R8.main(strings);
    }

    @Override
    public String getName() {
        return "R8";
    }
}
