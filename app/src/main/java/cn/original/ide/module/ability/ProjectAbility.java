package cn.original.ide.module.ability;

import android.graphics.drawable.Drawable;

import cn.original.ide.module.content.Application;
import cn.original.ide.module.interfaces.ProjectAbilityParent;

public class ProjectAbility extends Ability implements ProjectAbilityParent {
    public static final int VIEW_EDIT_GROUP = 0;
    public static final int VIEW_IMAGE_GROUP = 1;
    private Application application;
    private ProjectAbilityParent projectAbilityParent = null;

    public ProjectAbility(Application application) {
        super(application);
    }

    public void addParent(ProjectAbilityParent ability) {
        this.projectAbilityParent = ability;
    }

    public final AddItem onCreateAddItemInterface() {
        if (projectAbilityParent != null && projectAbilityParent instanceof ProjectAbility) {
            return ((ProjectAbility) projectAbilityParent).onCreateAddItemInterface();
        }
        return onCreateAddItem();
    }


    protected AddItem onCreateAddItem() {
        return null;
    }

    @Override
    public ProjectAbilityParent getParent() {
        return projectAbilityParent;
    }

    public interface AddItem {
        Drawable getIcon();

        String getName();
    }

    public final void onCreateAddDialogInterface() {

    }

    protected void onCreateAddDialog() {

    }

    public final void addDialogData() {

    }

    public class VIEW {
        public int TYPE;
        public String title = null;
    }


}
