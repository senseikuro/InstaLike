package com.example.instalike;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.instalike.db.Post;
import com.example.instalike.db.PostActions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;


public class PublishPicsFragment extends Fragment {
    public static final int PHOTO_GALERY_REQUEST = 20;
    public static final int REQUEST_IMAGE = 100;
    private View view;
    private ImageView mPhoto;
    private EditText mDescription;
    private Button mPublish;
    private int user_ID;
    private byte[] pathImage;
    private Bitmap mImage;



    public static final int CAMERA_ACTION_PICK_REQUEST_CODE = 0;
    public static final int REQUEST_GALLERY_IMAGE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish_pics,container,false);
        ImagePickerActivity.clearCache(getActivity());
        onPublishClick();

        mPhoto=view.findViewById(R.id.fragment_publish_post_img);
        mDescription=view.findViewById(R.id.fragment_publish_post_description);
        mPublish=view.findViewById(R.id.fragment_publish_post_btn);

        user_ID=getArguments().getInt("CURRENT_USER");
        /*Intent photoIntent=new Intent(Intent.ACTION_PICK);

        File photoDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path= photoDirectory.getPath();

        Uri data=Uri.parse(path);
        photoIntent.setDataAndType(data,"image/*");
        startActivityForResult(photoIntent, PHOTO_GALERY_REQUEST);*/


        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublish.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                Date now = new Date(Calendar.getInstance().getTime().getTime());
                PostActions postAction=new PostActions(getContext());
                postAction.open();
                Post post= new Post(user_ID,pathImage,mDescription.getText().toString(),now);
                postAction.insertPost(post);
            }
            });

        return view;

    }
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            // if we are here, everything processed successfully.
            if (requestCode == PHOTO_GALERY_REQUEST) {
                // if we are here, we are hearing back from the image gallery.

                // the address of the image on the SD Card.
                Uri imageUri = data.getData();

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    // get a bitmap from the stream.
                    mImage = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mImage.compress(Bitmap.CompressFormat.JPEG,100,stream);
                    pathImage=stream.toByteArray();

                    // show the image to the user
                    mPhoto.setImageBitmap(mImage);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(getActivity(), "Unable to open image", Toast.LENGTH_LONG).show();
                }


        }
    }*/
   /*private void loadProfile(String url) {
       Log.d(TAG, "Image cache path: " + url);

       Glide.with(this).load(url)
               .into(mPhoto);
       mPhoto.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.transparent));
   }

    private void loadProfileDefault() {
        Glide.with(this).load(R.drawable.baseline_account_circle_black_48)
                .into(mPhoto);
        mPhoto.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }*/
    public void onPublishClick(){
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }
    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

   // @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                /*Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Uri imageUri=data.getParcelableExtra("path");
                InputStream inputStream;
                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    // get a bitmap from the stream.
                    mImage = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    pathImage = stream.toByteArray();

                    // show the image to the user
                    pathImage=stream.toByteArray();

                    mPhoto.setImageBitmap(mImage);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(getActivity(), "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Grant Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}

