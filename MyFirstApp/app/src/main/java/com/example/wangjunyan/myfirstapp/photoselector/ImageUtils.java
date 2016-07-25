package com.example.wangjunyan.myfirstapp.photoselector;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.wangjunyan.myfirstapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjunyan on 15/12/22.
 */
public class ImageUtils {

    public static List<FolderItem> getFolderItems(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        List<FolderItem> folderItemList = new ArrayList<FolderItem>();
        Map<String, FolderItem> map = new HashMap<String, FolderItem>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.SIZE}, null, null, null);
        if(cursor == null || !cursor.moveToNext()){
            return folderItemList;
        }
        cursor.moveToLast();
        FolderItem current = new FolderItem(context.getResources().getString(R.string.all_photos), 0,
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)), true);
        folderItemList.add(current);
        do{
            if(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) < 1024*10){
                continue;
            }
            current.increaseSize();
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            if(map.keySet().contains(name)){
                map.get(name).increaseSize();
            }else{
                FolderItem album = new FolderItem(name, 1, cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)), false);
                map.put(name, album);
                folderItemList.add(album);
            }
        }while(cursor.moveToPrevious());
        return folderItemList;
    }

    public static List<PhotoItem> getAllPhotos(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        List<PhotoItem> photoItemList = new ArrayList<PhotoItem>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE}, null, null, MediaStore.Images.ImageColumns.DATE_ADDED);
        if(cursor == null || !cursor.moveToNext()){
            return photoItemList;
        }
        cursor.moveToLast();
        PhotoItem photoItem;
        do{
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024*10) {
                photoItem = new PhotoItem();
                photoItem.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photoItemList.add(photoItem);
            }
        }while(cursor.moveToPrevious());
        return photoItemList;
    }

    public static List<PhotoItem> getPhotosInFolder(Context context, String folderName){
        ContentResolver contentResolver = context.getContentResolver();
        List<PhotoItem> photoItemList = new ArrayList<PhotoItem>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DATE_ADDED, MediaStore.Images.ImageColumns.SIZE},
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + " = ?", new String[]{folderName}, MediaStore.Images.ImageColumns.DATE_ADDED);
        if (cursor == null || !cursor.moveToNext()) {
            return photoItemList;
        }
        cursor.moveToLast();
        PhotoItem photoItem;
        do {
            if (cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE)) > 1024*10) {
                photoItem = new PhotoItem();
                photoItem.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                photoItemList.add(photoItem);
            }
        } while (cursor.moveToPrevious());
        return photoItemList;
    }
}
