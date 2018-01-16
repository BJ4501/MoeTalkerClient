package net.bj.moetalker.push.frags.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.app.PresenterFragment;
import net.bj.moetalker.common.widget.EmptyView;
import net.bj.moetalker.common.widget.GalleryView;
import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.face.Face;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.MessageActivity;
import net.bj.moetalker.push.activities.PersonalActivity;
import net.bj.moetalker.utils.DateTimeUtil;
import net.bj.talker.factory.model.db.Session;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.presenter.message.SessionContract;
import net.bj.talker.factory.presenter.message.SessionPresenter;
import net.qiujuer.genius.ui.Ui;

import butterknife.BindView;
import butterknife.OnClick;


public class ActiveFragment extends PresenterFragment<SessionContract.Presenter>
        implements SessionContract.View{

    @BindView(R.id.empty)
    EmptyView mEmptyView;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    //适配器，User可以直接从数据库查询数据
    private RecyclerAdapter<Session> mAdapter;

    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        //初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<Session>() {
            @Override
            protected int getItemViewType(int position, Session session) {
                //返回cell的布局id
                return R.layout.cell_chat_list;
            }

            @Override
            protected ViewHolder<Session> onCreateViewHolder(View root, int viewType) {
                return new ActiveFragment.ViewHolder(root);
            }
        });

        //点击事件监听
        mAdapter.setmListener(new RecyclerAdapter.AdapterListenerImpl<Session>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Session session) {
                //跳转到聊天界面
                MessageActivity.show(getContext(),session);
            }
        });

        //初始化占位布局
        mEmptyView.bind(mRecycler);
        setPlaceHolderView(mEmptyView);
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        //进行一次数据加载
        mPresenter.start();
    }

    @Override
    protected SessionContract.Presenter initPresenter() {
        return new SessionPresenter(this);
    }

    @Override
    public RecyclerAdapter<Session> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mPlaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount() >= 0);
    }

    //界面数据渲染
    class ViewHolder extends RecyclerAdapter.ViewHolder<Session>{
        //头像
        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;
        //名字
        @BindView(R.id.txt_name)
        TextView mName;
        //内容
        @BindView(R.id.txt_content)
        TextView mContent;
        //时间
        @BindView(R.id.txt_time)
        TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Session session) {
            mPortraitView.setup(Glide.with(ActiveFragment.this),session.getPicture());
            mName.setText(session.getTitle());

            String str = TextUtils.isEmpty(session.getContent()) ? "" : session.getContent();
            Spannable spannable = new SpannableString(str);
            //解析表情
            Face.decode(mContent,spannable,(int) mContent.getTextSize());
            //把内容设置到布局上
            mContent.setText(spannable);
            mTime.setText(DateTimeUtil.getSampleDate(session.getModifyAt()));
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick(){
            //显示信息
            PersonalActivity.show(getContext(),mData.getId());
        }

    }
}
