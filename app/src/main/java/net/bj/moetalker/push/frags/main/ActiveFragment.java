package net.bj.moetalker.push.frags.main;


import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.widget.GalleryView;
import net.bj.moetalker.push.R;

import butterknife.BindView;


public class ActiveFragment extends Fragment {
    @BindView(R.id.galleryView)
    GalleryView mGallery;


    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

        mGallery.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });

    }
}
