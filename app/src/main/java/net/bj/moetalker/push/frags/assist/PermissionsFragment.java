package net.bj.moetalker.push.frags.assist;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.bj.moetalker.push.R;
import net.bj.moetalker.push.frags.media.GalleryFragment;

/**
 * 权限申请弹出框
 */
public class PermissionsFragment extends BottomSheetDialogFragment {


    public PermissionsFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //复用即可
        return new GalleryFragment.TransStatusBottomSheetDialog(getContext());
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取我们的GalleryView
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGallery = root.findViewById(R.id.galleryView);
        return root;
    }
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取布局中的控件
        View root = inflater.inflate(R.layout.fragment_permissions, container, false);
        return root;
    }

}
