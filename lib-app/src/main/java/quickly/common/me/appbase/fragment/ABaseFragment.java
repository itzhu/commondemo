package quickly.common.me.appbase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Aauthor- itzhu
 * Date- 2017/3/7 9:31
 * Desc-
 */

public abstract class ABaseFragment extends Fragment {
    private View fragmentContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContentView = inflater.inflate(getViewID(), container, false);
        return fragmentContentView;
    }

    protected abstract int getViewID();

    /**
     * onCreateView之后才能使用
     */
    public <T extends View> T $(int id) {
        return (T) getView().findViewById(id);
    }
}
