package net.bj.talker.factory.presenter.search;

import net.bj.moetalker.factory.presenter.BasePresenter;

/**
 * 搜索群的逻辑实现
 * Created by Neko-T4 on 2017/12/21.
 */

public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
        implements SearchContract.Presenter {
    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
