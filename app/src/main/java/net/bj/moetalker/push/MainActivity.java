package net.bj.moetalker.push;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.bj.moetalker.common.Common;
import net.bj.moetalker.common.app.Activity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements IView {
    @BindView(R.id.txt_result)
    TextView mResultText;

    @BindView(R.id.edit_query)
    EditText mInputTest;

    private IPresenter mPresenter;



    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_submit)
    void onSubmit(){
        mPresenter.Search();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new Presenter(this);
    }

    @Override
    public String getInputString() {
        return mInputTest.getText().toString();
    }

    @Override
    public void setResultString(String string) {
        mResultText.setText(string);
    }
}
