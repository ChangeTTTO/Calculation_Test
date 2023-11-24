package com.example.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ipsec.ike.exceptions.IkeNetworkLostException;

import java.util.Random;

import androidx.annotation.HalfFloat;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class MyViewModel extends AndroidViewModel {
    SavedStateHandle handle;
    private static final String KEY_HIGH_SCORE = "key_high_score";
    private static final String KEY_LEFT_NUMBER = "key_left_number";
    private static final String KEY_RIGHT_NUMBER = "key_right_number";
    private static final String KEY_OPERATOR = "key_operator";
    private static final String KEY_ANSWER = "key_answer";
    private static final String KEY_CURRENT_SCORE = "key_current_score";
    private static final String SAVE_SHP_DATA_NAME = "save_shp_data_name";
    boolean win_flag = false;
    public MyViewModel(@NonNull Application application ,SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences shp =getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            //将一些值进行初始化
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0));//第二个参数是缺省值
            handle.set(KEY_LEFT_NUMBER,0);
            handle.set(KEY_RIGHT_NUMBER,0);
            handle.set(KEY_OPERATOR,"+");
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        this.handle=handle;
    }
    //声明为public，xml文件中才可引用
   public MutableLiveData<Integer> getLeftNumber(){
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }
   public MutableLiveData<Integer> getRightNumber(){
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }
    public MutableLiveData<String> getOperator(){
        return handle.getLiveData(KEY_OPERATOR);
    }
   public MutableLiveData<Integer> getHighScore(){
        return handle.getLiveData(KEY_HIGH_SCORE);
    }
   public MutableLiveData<Integer> getCurrentScore(){
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }
   public   MutableLiveData<Integer> getAnswer(){
        return handle.getLiveData(KEY_ANSWER);
    }

    //生成左右两边的数和运算符
    void generator(){
        int LEVEL = 50;
        Random random = new Random();
        int l,r;
        //范围是1-50的数。
        l=random.nextInt(LEVEL)+1;
        r=random.nextInt(LEVEL)+1;
        //加减随机
        if (l%2==0){
            getOperator().setValue("+");
        }else {
            getOperator().setValue("-");
        }
        getLeftNumber().setValue(l);
        getRightNumber().setValue(r);
    }
    //保存最高分
    void save (){
        SharedPreferences  shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();//为了能够写，拿到能写的对象
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue());//写入最高分
        editor.apply(); //提交
    }
    //回答正确的逻辑，如果当前的分数大于最高分，更新最高分。
    void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue());
        if(getCurrentScore().getValue()>getHighScore().getValue()){
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag=true;
        }
        //生成一道新的题
        generator();
    }
}
