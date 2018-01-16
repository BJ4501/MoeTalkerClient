package net.bj.moetalker.push.frags.panel;

import android.view.View;

import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.face.Face;
import net.bj.moetalker.push.R;

import java.util.List;

/**
 * Created by Neko-T4 on 2018/1/16.
 */

public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
