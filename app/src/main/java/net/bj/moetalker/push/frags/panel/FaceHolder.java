package net.bj.moetalker.push.frags.panel;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;

import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.face.Face;
import net.bj.moetalker.push.R;

import butterknife.BindView;

/**
 * Created by Neko-T4 on 2018/1/16.
 */

public class FaceHolder extends RecyclerAdapter.ViewHolder<Face.Bean> {
    @BindView(R.id.im_face)
    ImageView mFace;

    public FaceHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Face.Bean bean) {
        if (bean != null && (
                //drawable 资源 id
                (bean.preview instanceof Integer)
                        //face zip包资源路径
                        ||bean.preview instanceof String)){

            Glide.with(mFace.getContext())
                    .load(bean.preview)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888) //设置解码格式，保证清晰度
                    .into(mFace);
        }
    }
}
