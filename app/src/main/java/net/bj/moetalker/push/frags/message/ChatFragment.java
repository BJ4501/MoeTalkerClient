package net.bj.moetalker.push.frags.message;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.bj.moetalker.common.app.PresenterFragment;
import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.common.widget.adapter.TextWatcherAdapter;
import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.face.Face;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.MessageActivity;
import net.bj.moetalker.push.frags.panel.PanelFragment;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.persistence.Account;
import net.bj.talker.factory.presenter.message.ChatContract;
import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Neko-T4 on 2018/1/8.
 */

public abstract class ChatFragment<InitModel> extends PresenterFragment<ChatContract.Presenter>
        implements AppBarLayout.OnOffsetChangedListener,ChatContract.View<InitModel>, PanelFragment.PanelCallback{

    protected String mReceiverId;
    protected Adapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.edit_content)
    EditText mContent;

    @BindView(R.id.btn_submit)
    View mSubmit;

    //控制底部面板与软键盘过度的Boss控件
    private AirPanel.Boss mPanelBoss;

    private PanelFragment mPanelFragment;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    //NOTE：final 防止子类复写
    @Override
    protected final int getContentLayoutId() {
        return R.layout.fragment_chat_common;
    }

    //得到顶部布局的Id资源
    @LayoutRes
    protected abstract int getHeaderLayoutId();

    @Override
    protected void initWidget(View root) {
        //拿到占位布局,替换顶部布局一定需要发生在super之前
        //防止后面的控件绑定异常
        ViewStub stub = root.findViewById(R.id.view_stub_header);
        //设置替换资源
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();

        //在这里进行了控件绑定
        super.initWidget(root);

        //初始化面板操作
        mPanelBoss = root.findViewById(R.id.lay_content);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                //请求隐藏软键盘
                Util.hideKeyboard(mContent);
            }
        });
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);
        mPanelFragment.setup(this);

        initToolbar();
        initAppbar();
        initEditContent();

        //RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        //开始进行初始化操作
        mPresenter.start();
    }

    //初始化Toolbar
    protected void initToolbar(){
        Toolbar toolbar = mToolbar;
        //设置返回箭头
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    //给界面的Appbar设置一个监听，得到关闭与打开时候的进度
    private void initAppbar(){
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    //初始化输入框监听
    private void initEditContent(){
        mContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString().trim();
                //如果不为空就可以发送了
                boolean needSendMsg = !TextUtils.isEmpty(content);
                //设置状态，改变对应的Icon
                mSubmit.setActivated(needSendMsg);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    }

    //点击笑脸按钮
    @OnClick(R.id.btn_face)
    void onFaceClick(){
        //只需要请求打开即可
        mPanelBoss.openPanel();
        mPanelFragment.showFace();
    }

    //点击录音按钮
    @OnClick(R.id.btn_record)
    void onRecordClick(){
        mPanelBoss.openPanel();
        mPanelFragment.showRecord();
    }


    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        if (mSubmit.isActivated()){
            //发送
            String content = mContent.getText().toString();
            mContent.setText("");
            mPresenter.pushText(content);
        }else {
            onMoreClick();

        }
    }

    //点击更多。。。按钮
    private void onMoreClick(){
        mPanelBoss.openPanel();
        mPanelFragment.showGallery();
    }

    @Override
    public RecyclerAdapter<Message> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        //界面没有占位布局，Recycler是一直显示的，所以不需要做任何事情
        //do nothing
    }

    @Override
    public EditText getInputEditText() {
        //返回输入框
        return mContent;
    }

    @Override
    public void onSendGallery(String[] paths) {
        //图片回调回来
        mPresenter.pushImages(paths);

    }

    @Override
    public void onRecordDone(File file, long time) {
        //语音回调回来


    }

    //内容适配器
    private class Adapter extends RecyclerAdapter<Message>{

        @Override
        protected int getItemViewType(int position, Message message) {
            //显示左右的判断，看发送者
            //文字内容:我发送的在右边，收到的在左边
            boolean isRight = Objects.equals(message.getSender().getId(), Account.getUserId());

            switch (message.getType()){
                //文字内容
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
                //语音内容
                case Message.TYPE_AUDIO:
                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;
                //图片内容
                case Message.TYPE_PIC:
                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;
                //TODO 文件内容
                case Message.TYPE_FILE:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
                //其他内容
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {

            switch (viewType){
                //左右为同一个
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);
                case R.layout.cell_chat_audio_right:
                case R.layout.cell_chat_audio_left:
                    return new AudioHolder(root);
                case R.layout.cell_chat_pic_right:
                case R.layout.cell_chat_pic_left:
                    return new PicHolder(root);
                //默认情况下，返回的就是Text类型的Holder进行处理
                //TODO 文件的一些实现
                default:
                    return new TextHolder(root);
            }
        }
    }

    //Holder基类
    //不用private的原因是 后面需要ButterKnife
    class BaseHolder extends RecyclerAdapter.ViewHolder<Message>{
        //头像
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;

        //允许为空，左面没有，右面有
        @Nullable
        @BindView(R.id.loading)
        Loading mLoading;

        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            User sender = message.getSender();
            //进行数据加载
            sender.load();
            //进行头像加载
            mPortrait.setup(Glide.with(ChatFragment.this),sender);

            //当前布局应该是在右边，因为左边接收部分不显示加载动画
            if (mLoading != null){
                int status = message.getStatus();
                if (status == Message.STATUS_DONE){
                    //正常状态，隐藏Loading
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                }else if (status == Message.STATUS_CREATED){
                    //正在发送中的状态
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.setProgress(0);
                    //设置颜色
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(),R.color.colorAccent));
                    mLoading.start();
                }else if (status == Message.STATUS_FAILED){
                    //发送失败
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                    mLoading.setForegroundColor(UiCompat.getColor(getResources(),R.color.alertImportant));
                }

                //当状态时错误状态时才允许点击
                mPortrait.setEnabled(status == Message.STATUS_FAILED);
            }
        }

        //如果发送失败，点击头像可以再次发送
        @OnClick(R.id.im_portrait)
        void onRePushClick(){
            //重新发送
            if (mLoading != null && mPresenter.rePush(mData)){
                //必须是右边的才有可能进行重新发送
                //状态改变需要重新刷新界面当前信息
                updateData(mData);

            }
        }


    }

    //字体渲染，文字Holder
    class TextHolder extends BaseHolder{
        @BindView(R.id.txt_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);

            Spannable spannable = new SpannableString(message.getContent());

            //解析表情
            Face.decode(mContent,spannable, (int) Ui.dipToPx(getResources(),20));

            //把内容设置到布局上
            mContent.setText(spannable);
        }
    }

    //语音Holder
    class AudioHolder extends BaseHolder{

        public AudioHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);

        }
    }

    //图片Holder
    class PicHolder extends BaseHolder{

        @BindView(R.id.im_image)
        ImageView mContent;

        public PicHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            //当是图片类型的时候，Content中就是具体的地址
            String content = message.getContent();
            Glide.with(ChatFragment.this)
                    .load(content)
                    .fitCenter()
                    .into(mContent);
        }
    }


}
