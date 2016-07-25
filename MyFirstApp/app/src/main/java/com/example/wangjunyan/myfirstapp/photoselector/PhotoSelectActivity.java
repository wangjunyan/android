package com.example.wangjunyan.myfirstapp.photoselector;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wangjunyan.myfirstapp.R;
import com.example.wangjunyan.myfirstapp.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoSelectActivity extends Activity {
    public static final String CAMERA_FLAG = "camera_flag@cmcm.com";

    private GridView gvPhotos;
    private ListView lvFolders;
    private Button btnFolder;
    private LinearLayout layoutFolder;

    private String folderName;
    private List<FolderItem> foldersList;
    private List<PhotoItem> photosList;
    private PhotoAdapter photoAdapter;
    private FolderAdater folderAdater;
    private PhotoLoader photoLoader;
    private FolderLoader folderLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);
        gvPhotos = (GridView) findViewById(R.id.grid_photo);
        photoAdapter = new PhotoAdapter(this);
        gvPhotos.setAdapter(photoAdapter);

        lvFolders = (ListView) findViewById(R.id.lst_folder);
        folderAdater = new FolderAdater(this);
        lvFolders.setAdapter(folderAdater);

        btnFolder = (Button) findViewById(R.id.btn_folder);
        layoutFolder = (LinearLayout) findViewById(R.id.layout_folder);
        btnFolder.setText(R.string.all_photos);
        btnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = layoutFolder.getVisibility();
                if(visibility == View.VISIBLE){
                    layoutFolder.setVisibility(View.GONE);
                }else if(visibility == View.GONE){
                    layoutFolder.setVisibility(View.VISIBLE);
                }
            }
        });

        lvFolders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FolderItem folderItem = foldersList.get(position);
                for(FolderItem folder : foldersList){
                    if(folder.equals(folderItem)){
                        folder.checked = true;
                    }else{
                        folder.checked = false;
                    }
                }
                folderAdater.notifyDataSetChanged();
                btnFolder.setText(folderItem.name);
                folderName = folderItem.name;
                layoutFolder.setVisibility(View.GONE);
                photoLoader.setFolderName(folderItem.name);
                photoLoader.forceLoad();
            }
        });

        gvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoItem photoItem = photosList.get(position);
                if(photoItem.path.equals(CAMERA_FLAG)){

                }else{

                }
            }
        });

        getLoaderManager().initLoader(0, null, new FolderLoaderCallbacks());
        getLoaderManager().initLoader(1, null, new PhotoLoaderCallbacks());
    }


    private boolean isInAllPhotosFolder() {
        if (!TextUtils.isEmpty(folderName) && !folderName.equals(getResources().getString(R.string.all_photos))) {
            return false;
        }
        return true;
    }

    private class FolderLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<FolderItem>> {

        @Override
        public Loader<List<FolderItem>> onCreateLoader(int id, Bundle args) {
            folderLoader = new FolderLoader(PhotoSelectActivity.this);
            return folderLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<FolderItem>> loader, List<FolderItem> folders) {
            folderAdater.setItems(folders);
            folderAdater.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<FolderItem>> loader) {

        }

    }

    private class PhotoLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<PhotoItem>> {

        @Override
        public Loader<List<PhotoItem>> onCreateLoader(int id, Bundle args) {
            photoLoader = new PhotoLoader(PhotoSelectActivity.this);
            return photoLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<PhotoItem>> loader, List<PhotoItem> photos) {
            photoAdapter.setItems(photos, isInAllPhotosFolder());
            photoAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<PhotoItem>> loader) {
        }

    }

    public static class PhotoLoader extends AsyncTaskLoader<List<PhotoItem>> {
        private List<PhotoItem> photoItemList;
        private String folderName;

        public PhotoLoader(Context context){
            super(context);
        }

        public void setFolderName(String folderName){
            this.folderName = folderName;
        }

        @Override
        public List<PhotoItem> loadInBackground() {
            if(!TextUtils.isEmpty(folderName) && !folderName.equals(getContext().getResources().getString(R.string.all_photos))){
                return ImageUtils.getPhotosInFolder(getContext(), folderName);
            }else{
                return ImageUtils.getAllPhotos(getContext());
            }

        }

        @Override
        public void deliverResult(List<PhotoItem> photoItems){
            if (isReset()){
                if(photoItems != null){
                    onReleaseResources(photoItems);
                }
            }
            List<PhotoItem> oldPhotoItems = photoItemList;
            photoItemList = photoItems;
            if(isStarted()){
                super.deliverResult(photoItems);
            }
            if(oldPhotoItems != null){
                onReleaseResources(oldPhotoItems);
            }
        }

        @Override
        protected void onStartLoading(){
            if(photoItemList != null){
                deliverResult(photoItemList);
            }else{
                forceLoad();
            }
        }

        @Override
        protected void onStopLoading(){
            cancelLoad();
        }

        @Override
        public void onCanceled(List<PhotoItem> photoItems){
            super.onCanceled(photoItems);
            onReleaseResources(photoItems);
        }

        @Override
        protected void onReset(){
            super.onReset();
            onStopLoading();
            if(photoItemList != null){
                onReleaseResources(photoItemList);
                photoItemList = null;
            }
        }

        protected void onReleaseResources(List<PhotoItem> photoItems){

        }

    }

    public static class FolderLoader extends AsyncTaskLoader<List<FolderItem>>{

        private List<FolderItem> folderItemList;

        public FolderLoader(Context context){
            super(context);
        }

        @Override
        public List<FolderItem> loadInBackground() {
            return ImageUtils.getFolderItems(getContext());
        }

        @Override
        public void deliverResult(List<FolderItem> folderItems){
            if (isReset()){
                if(folderItems != null){
                    onReleaseResources(folderItems);
                }
            }
            List<FolderItem> oldFolderItems = folderItemList;
            folderItemList = folderItems;
            if(isStarted()){
                super.deliverResult(folderItems);
            }
            if(oldFolderItems != null){
                onReleaseResources(oldFolderItems);
            }
        }

        @Override
        protected void onStartLoading(){
            if(folderItemList != null){
                deliverResult(folderItemList);
            }else{
                forceLoad();
            }
        }

        @Override
        protected void onStopLoading(){
            cancelLoad();
        }

        @Override
        public void onCanceled(List<FolderItem> folderItems){
            super.onCanceled(folderItems);
            onReleaseResources(folderItems);
        }

        @Override
        protected void onReset(){
            super.onReset();
            onStopLoading();
            if(folderItemList != null){
                onReleaseResources(folderItemList);
                folderItemList = null;
            }
        }

        protected void onReleaseResources(List<FolderItem> folderItems){

        }
    }

    public class PhotoAdapter extends BaseAdapter{
        public static final int NUM_COLUMNS = 3;
        private Context context;
        private AbsListView.LayoutParams itemLayoutParams;

        public PhotoAdapter(Context context){
            this.context = context;
            int screenWidth = DisplayUtils.getWidthPixels(context);
            int horizontalSpacing = context.getResources().getDimensionPixelSize(R.dimen.photo_item_gv_horizontal_spacing);
            int itemWidth = (screenWidth - (horizontalSpacing * (NUM_COLUMNS - 1))) / NUM_COLUMNS;
            this.itemLayoutParams = new AbsListView.LayoutParams(itemWidth, itemWidth);
        }

        @Override
        public int getCount() {
            return photosList==null ? 0 : photosList.size();
        }

        @Override
        public Object getItem(int position) {
            return (PhotoItem) photosList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PhotoItem photoItem = photosList.get(position);
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.photo_item, null);
            }
            ImageView imgPhoto = ViewHolder.get(convertView, R.id.img_photo);
            if(photoItem.path != null && photoItem.path.equals(CAMERA_FLAG)){
                imgPhoto.setImageResource(R.drawable.icon_photo_default);
            }else{
                Picasso.with(context).load("file://" + photoItem.path)
                        .placeholder(R.drawable.icon_photo_default).resize(160, 160).centerCrop().into(imgPhoto);
            }
            convertView.setLayoutParams(itemLayoutParams);
            return convertView;
        }

        public void setItems(List<PhotoItem> photoItems){
            photosList = photoItems;
        }

        public void setItems(List<PhotoItem> photoItems, boolean needCameraItem){
            if (needCameraItem) {
                PhotoItem photoItem = new PhotoItem();
                photoItem.path = PhotoSelectActivity.CAMERA_FLAG;
                photoItems.add(0, photoItem);
            }
            photosList = photoItems;
        }
    }

    public class FolderAdater extends BaseAdapter{

        private Context context;

        public FolderAdater(Context context){
            this.context = context;
        }

        public void setItems(List<FolderItem> folderItems){
            foldersList = folderItems;
        }

        @Override
        public int getCount() {
            return foldersList==null ? 0 : foldersList.size();
        }

        @Override
        public Object getItem(int position) {
            return (FolderItem) foldersList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderItem folderItem = foldersList.get(position);
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.folder_item, null);
            }
            ImageView imgFolder = ViewHolder.get(convertView, R.id.img_folder);
            TextView txtFolderName = ViewHolder.get(convertView, R.id.txt_folder_name);
            TextView txtFolderCount = ViewHolder.get(convertView, R.id.txt_folder_count);
            ImageView imgFolderCheck = ViewHolder.get(convertView, R.id.img_folder_check);
            Picasso.with(context).load("file://" + folderItem.path).placeholder(R.drawable.icon_photo_default)
                    .resizeDimen(R.dimen.folder_item_image_size, R.dimen.folder_item_image_size).centerCrop().into(imgFolder);
            txtFolderName.setText(folderItem.name);
            txtFolderCount.setText(String.format(context.getResources().getString(R.string.photos_size), folderItem.size));
            imgFolderCheck.setImageResource(folderItem.checked ? R.drawable.icon_checked : R.drawable.icon_unchecked);
            return convertView;
        }
    }
}
