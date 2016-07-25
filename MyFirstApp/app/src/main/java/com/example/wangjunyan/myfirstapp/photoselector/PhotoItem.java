package com.example.wangjunyan.myfirstapp.photoselector;

/**
 * Created by wangjunyan on 15/12/21.
 */
public class PhotoItem {
    public String name;
    public String path;
    public boolean checked;

    public PhotoItem(){

    }

    public PhotoItem(String name, String path){
        this.name = name;
        this.path = path;
    }
}
