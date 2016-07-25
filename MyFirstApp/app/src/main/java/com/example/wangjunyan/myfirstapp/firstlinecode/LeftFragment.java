package com.example.wangjunyan.myfirstapp.firstlinecode;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjunyan.myfirstapp.MainActivity;
import com.example.wangjunyan.myfirstapp.R;


public class LeftFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstance){
        Log.d(MainActivity.LOG_TAG, "onCreateView");
        View view = inflater.inflate(R.layout.left_fragment, container, false);
        return view;
    }

}
