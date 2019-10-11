package com.example.instalike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PublishPicsFragment extends Fragment {
    public static final int PHOTO_GALERY_REQUEST = 20;
    private View view;
    private ImageView mPhoto;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publish_pics,container,false);

        Intent photoIntent=new Intent(Intent.ACTION_PICK);

        File photoDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path= photoDirectory.getPath();

        Uri data=Uri.parse(path);
        photoIntent.setDataAndType(data,"image/*");
        startActivityForResult(photoIntent, PHOTO_GALERY_REQUEST);

        mPhoto=view.findViewById(R.id.fragment_publish_post_img);
        return view;

    }
    @Override
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
                    Bitmap image = BitmapFactory.decodeStream(inputStream);


                    // show the image to the user
                    mPhoto.setImageBitmap(image);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(getActivity(), "Unable to open image", Toast.LENGTH_LONG).show();
                }


        }
    }
}
