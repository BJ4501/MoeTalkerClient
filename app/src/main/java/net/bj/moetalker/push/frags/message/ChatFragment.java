package net.bj.moetalker.push.frags.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.widget.PortraitView;
import net.bj.moetalker.common.widget.adapter.TextWatcherAdapter;
import net.bj.moetalker.common.widget.recycler.RecyclerAdapter;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.MessageActivity;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.model.db.User;
import net.bj.talker.factory.persistence.Account;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Neko-T4 on 2018/1/8.
 */

public abstract class ChatFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener{

    protected String mReceiverId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.edit_content)
    EditText mContent;

    @BindView(R.id.btn_submit)
    View mSubmit;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        initToolbar();
        initAppbar();
        initEditContent();

        //RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    @OnClick(R.id.btn_face)
    void onFaceClick(){


    }

    @OnClick(R.id.btn_record)
    void onRecordClick(){


    }


    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        if (mSubmit.isActivated()){
            //发送

        }else {
            onMoreClick();

        }
    }

    private void onMoreClick(){
        //TODO
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
            }

            return 0;
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {

            switch (viewType){
                //左右为同一个
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);
                //默认情况下，返回的就是Text类型的Holder进行处理
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
            if (mLoading != null){
                //必须是右边的才有可能进行重新发送
                //TODO 重新发送

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
            //把内容设置到布局上
            mContent.setText(message.getContent());
        }
    }


}
