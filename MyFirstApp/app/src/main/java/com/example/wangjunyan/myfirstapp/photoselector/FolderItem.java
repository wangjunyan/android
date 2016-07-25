package com.example.wangjunyan.myfirstapp.photoselector;

/**
 * Created by wangjunyan on 15/12/21.
 */
public class FolderItem {
    public String name;
    public String path;
    public int size;
    public boolean checked;

    public FolderItem(String name, int size, String path, boolean checked){
        this.name = name;
        this.size = size;
        this.path = path;
        this.checked = checked;
    }

    public void increaseSize(){
        size++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof FolderItem)) {
            return false;
        }
        FolderItem folderItem = (FolderItem) o;
        if (name != null && name.equals(folderItem.name)) {
            return true;
        } else {
            return false;
        }
    }
}
