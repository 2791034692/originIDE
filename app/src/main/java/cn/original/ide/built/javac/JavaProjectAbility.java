package cn.original.ide.built.javac;

import android.graphics.drawable.Drawable;
import cn.original.ide.R;
import cn.original.ide.module.ability.ProjectAbility;
import cn.original.ide.module.content.Application;

public class JavaProjectAbility extends ProjectAbility {

    public JavaProjectAbility(Application application) {
        super(application);
    }

    @Override
    protected AddItem onCreateAddItem() {
        AddItem addItem = new AddItem() {
            @Override
            public Drawable getIcon() {
                return getApplication().getApplicationManage().getContext().getDrawable(R.drawable.ic_drawable_java_24dp);
            }

            @Override
            public String getName() {
                return "JAVA控制台";
            }
        };
        return super.onCreateAddItem();
    }

    @Override
    protected void onCreateProject() {
        super.onCreateProject();
    }


}
