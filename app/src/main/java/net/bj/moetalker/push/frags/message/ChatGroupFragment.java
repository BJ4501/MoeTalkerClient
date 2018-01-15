package net.bj.moetalker.push.frags.message;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.bj.moetalker.push.R;
import net.bj.moetalker.push.activities.GroupMemberActivity;
import net.bj.moetalker.push.activities.PersonalActivity;
import net.bj.talker.factory.model.db.Group;
import net.bj.talker.factory.model.db.view.MemberUserModel;
import net.bj.talker.factory.presenter.message.ChatContract;
import net.bj.talker.factory.presenter.message.ChatGroupPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * 群聊天界面
 */
public class ChatGroupFragment extends ChatFragment<Group> implements ChatContract.GroupView {

    @BindView(R.id.im_header)
    ImageView mHeader;
    @BindView(R.id.lay_members)
    LinearLayout mLayMembers;
    @BindView(R.id.txt_member_more)
    TextView mMemberMore;

    public ChatGroupFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getHeaderLayoutId() {
        return R.layout.lay_chat_header_group;
    }

    @Override
    protected ChatContract.Presenter initPresenter() {
        return new ChatGroupPresenter(this,mReceiverId);
    }

    //加载背景
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Glide.with(this)
                .load(R.drawable.default_banner_group)
                .centerCrop()
                .into(new ViewTarget<CollapsingToolbarLayout,GlideDrawable>(mCollapsingLayout) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setContentScrim(resource.getCurrent());
                    }
                });
    }

    //头像随状态栏高度缩放方法(高度的综合运算，透明头像与icon)
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        super.onOffsetChanged(appBarLayout, verticalOffset);
        View view = mLayMembers;
        if(view == null)
            return;

        if (verticalOffset == 0){
            //完全展开--头像可见
            view.setVisibility(View.VISIBLE);
            //缩放效果
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);
        }else {
            //abs 运算
            verticalOffset = Math.abs(verticalOffset);
            //最大滚动高度
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            if (verticalOffset >= totalScrollRange){
                //头像不可见
                view.setVisibility(View.INVISIBLE);
                //缩放效果
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);
            }else {
                //处于中间状态时
                //计算进度值(1->0)
                float progress = 1 - verticalOffset/(float)totalScrollRange;
                view.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);
            }
        }
    }

    @Override
    public void onInit(Group group) {
        mCollapsingLayout.setTitle(group.getName());
        Glide.with(this)
                .load(group.getPicture())
                .centerCrop()
                .placeholder(R.drawable.default_banner_group)
                .into(mHeader);
    }

    @Override
    public void onInitGroupMembers(List<MemberUserModel> members, long moreCount) {
        if (members == null || members.size() == 0)
            return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (final MemberUserModel member : members) {
            //添加成员头像
            ImageView p = (ImageView) inflater.inflate(R.layout.lay_chat_group_portrait,mLayMembers,false);
            mLayMembers.addView(p,0);
            Glide.with(this)
                    .load(member.portrait)
                    .placeholder(R.drawable.default_portrait)
                    .centerCrop()
                    .dontAnimate()
                    .into(p);

            //当头像被点击，显示这个人的个人信息
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //个人界面的跳转
                    PersonalActivity.show(getContext(),member.userId);
                }
            });
        }

        //更多(+1)的按钮
        if (moreCount > 0){
            mMemberMore.setText(String.format("+%s",moreCount));
            mMemberMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //显示成员列表
                    //mReceiverId 就是群的Id
                    GroupMemberActivity.show(getContext(),mReceiverId);
                }
            });
        }else {
            mMemberMore.setVisibility(View.GONE);
        }

    }

    @Override
    public void showAdminOption(boolean isAdmin) {
        if (isAdmin){
            mToolbar.inflateMenu(R.menu.chat_group);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_add){
                        //群成员添加操作
                        //mReceiverId 就是群的Id
                        GroupMemberActivity.showAdmin(getContext(),mReceiverId);
                        return true;
                    }

                    return false;
                }
            });
        }
    }


}
