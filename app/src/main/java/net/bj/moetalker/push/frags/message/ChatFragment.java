package net.bj.moetalker.push.frags.message;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import net.bj.moetalker.common.app.Fragment;
import net.bj.moetalker.common.widget.adapter.TextWatcherAdapter;
import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.MessageActivity;

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


}
