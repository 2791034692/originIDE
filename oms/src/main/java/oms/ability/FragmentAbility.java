package oms.ability;

import androidx.fragment.app.Fragment;

public class FragmentAbility extends Fragment {

    public UIAbility getAbility() {
        return (UIAbility)getActivity();
    }
}
