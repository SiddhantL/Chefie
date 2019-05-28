package com.snltech.siddhantlad.chefiecompile.DatabaseSource;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snltech.siddhantlad.chefiecompile.DisplayRecipeInfo;
import com.snltech.siddhantlad.chefiecompile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

    /**
     * Created by User on 2/13/2017.
     */

    public class ImageUploader extends AppCompatActivity {

        private static final String TAG = "ImageUploader";

        //declare variables
        private ImageView image;
       // private EditText imageName;
        private Button btnUpload/*, btnNext, btnBack*/,imageSelect;
        private ProgressDialog mProgressDialog;
String picturePath;
TextView nameRec;
        private final static int mWidth = 512;
        private final static int mLength = 512;
        public static final int PICK_IMAGE = 1;
        private ArrayList<String> pathArray;
        private int array_position;
        DatabaseReference creditDatabase;
        EditText YoutubeText;
        private StorageReference mStorageRef;
        private FirebaseAuth auth;

        public ImageUploader() {
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_image_uploader);
            try {
                image = (ImageView) findViewById(R.id.uploadImage);
                checkFilePermissions();
                imageSelect = (Button) findViewById(R.id.buttonSelect);
                nameRec = (TextView) findViewById(R.id.nameRec);
                YoutubeText = (EditText) findViewById(R.id.editText5);
            /*btnBack = (Button) findViewById(R.id.btnBackImage);
            btnNext = (Button) findViewById(R.id.btnNextImage);*/
                btnUpload = (Button) findViewById(R.id.btnUploadImage);
                // imageName = (EditText) findViewById(R.id.imageName);
                pathArray = new ArrayList<>();
                mProgressDialog = new ProgressDialog(ImageUploader.this);
                auth = FirebaseAuth.getInstance();
                mStorageRef = FirebaseStorage.getInstance().getReference();
                Intent intent = getIntent();
                final String nameOfRecipe = intent.getStringExtra("nameOfRecipe");
                creditDatabase = FirebaseDatabase.getInstance().getReference("credits/" + nameOfRecipe);
                nameRec.setText(nameOfRecipe);
                imageSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_IMAGE);

                    }
                });
         /*   btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (array_position > 0) {
                        Log.d(TAG, "onClick: Back an Image.");
                        array_position = array_position - 1;
                        loadImageFromStorage();
                    }
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (array_position < pathArray.size() - 1) {
                        Log.d(TAG, "onClick: Next Image.");
                        array_position = array_position + 1;
                        loadImageFromStorage();
                    }
                }
            });*/

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (YoutubeText.getText() != null||!YoutubeText.getText().toString().equals("")||!YoutubeText.getText().toString().equals(" ")||!TextUtils.isEmpty(YoutubeText.getText().toString())) {
                            creditDatabase.child("YouTube").setValue(YoutubeText.getText().toString());
                        }
                        Log.d(TAG, "onClick: Uploading Image.");
                        mProgressDialog.setMessage("Uploading Image...");
                        mProgressDialog.show();

                        //get the signed in user
                        FirebaseUser user = auth.getCurrentUser();
                        String userID = user.getUid();

                        String name = nameOfRecipe;
                        if (!TextUtils.isEmpty(name)) {
                            Uri uri = Uri.fromFile(new File(picturePath));
                            StorageReference storageReference = mStorageRef.child("images/" + name + "/" + name + ".jpg");
                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    //   Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    toastMessage("Recipe Uploaded");
                                    mProgressDialog.dismiss();
                                    Intent displayYourRecipe = new Intent(ImageUploader.this, DisplayRecipeInfo.class);
                                    displayYourRecipe.putExtra("RecipeName", nameOfRecipe);
                                    startActivity(displayYourRecipe);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toastMessage("Upload Failed");
                                    mProgressDialog.dismiss();
                                }
                            })
                            ;
                        } else {
                            //toastMessage("Add A Name");
                        }


                    }
                });

            }catch (Exception E){
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Add the file paths you want to use into the array
         */
        private void addFilePaths() {
            Log.d(TAG, "addFilePaths: Adding file paths.");
            String path = System.getenv("EXTERNAL_STORAGE");
            pathArray.add(path + "/Pictures/Portal/image1.jpg");
            pathArray.add(path + "/Pictures/Portal/image2.jpg");
            pathArray.add(path + "/Pictures/Portal/image3.jpg");
            loadImageFromStorage();
        }

        private void loadImageFromStorage() {
            try {
                String path = pathArray.get(array_position);
                File f = new File(path, "");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                image.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "loadImageFromStorage: FileNotFoundException: " + e.getMessage());
            }

        }

        private void checkFilePermissions() {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                int permissionCheck = ImageUploader.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
                permissionCheck += ImageUploader.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
                if (permissionCheck != 0) {
                    this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
                }
            } else {
                Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
            }
        }

        /**
         * customizable toast
         *
         * @param message
         */
        private void toastMessage(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
        }
    }

