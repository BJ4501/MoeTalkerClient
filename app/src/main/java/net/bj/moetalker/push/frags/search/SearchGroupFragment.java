package net.bj.moetalker.push.frags.search;


import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.SearchActivity;

/**
 * 搜索群的界面实现
 */
public class SearchGroupFragment extends Fragment implements SearchActivity.SearchFragment {


    public SearchGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_group;
    }

    @Override
    public void search(String content) {

    }
}
