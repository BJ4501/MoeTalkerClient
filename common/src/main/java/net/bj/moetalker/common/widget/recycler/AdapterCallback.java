package net.bj.moetalker.common.widget.recycler;

/**
 * Created by Neko-T4 on 2017/11/28.
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);

}
