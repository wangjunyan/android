package com.example.wangjunyan.myfirstapp.firstlinecode;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjunyan.myfirstapp.MainActivity;
import com.example.wangjunyan.myfirstapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstance){
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.d(MainActivity.LOG_TAG, "onAttach " + activity.getClass().getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.LOG_TAG, "onCreate " + RightFragment.class.getSimpleName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.LOG_TAG, "onActivityCreated " + RightFragment.class.getSimpleName());
    }

    @Override
    public void onStart(){
        Log.d(MainActivity.LOG_TAG, "onStart " + RightFragment.class.getSimpleName());
        super.onStart();
    }

    @Override
    public void onResume(){
        Log.d(MainActivity.LOG_TAG, "onResume " + RightFragment.class.getSimpleName());
        super.onResume();
    }

    @Override
    public void onPause(){
        Log.d(MainActivity.LOG_TAG, "onPause "  + RightFragment.class.getSimpleName());
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.d(MainActivity.LOG_TAG, "onStop " + RightFragment.class.getSimpleName());
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        Log.d(MainActivity.LOG_TAG, "onDestroyView " + RightFragment.class.getSimpleName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Log.d(MainActivity.LOG_TAG, "onDestroy " + RightFragment.class.getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onDetach(){
        Log.d(MainActivity.LOG_TAG, "onDetach " + RightFragment.class.getSimpleName());
        super.onDetach();
    }

}
