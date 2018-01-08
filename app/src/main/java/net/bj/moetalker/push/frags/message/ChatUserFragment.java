package net.bj.moetalker.push.frags.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.bj.moetalker.push.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment {


    public ChatUserFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

}
