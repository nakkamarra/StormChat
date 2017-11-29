package com.stjohns.stormchat.Activities;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.stjohns.stormchat.R;

public class ProfileActivity extends Activity
{
    private static final int REQUEST_CAMERA = 3;
    private static final int SELECT_FILE = 2;
    EditText userNameEditText, userStatusEditText, userCollegeEditText, userMajorEditText;
    ImageView userPic;
    Button userChangeImage;
    LinearLayout saveProfilePage;

    FirebaseAuth authUser;
    FirebaseAuth.AuthStateListener authUserListener;

    DatabaseReference userDB;
    StorageReference storageRef;
    Uri imageHoldUri = null;

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_profile_activity);
    }
    public void onStart()
    {
        super.onStart();
        userNameEditText =findViewById(R.id.userProfileName); // findViewById(int) retrieves the widgets in that UI that you need to interact with programmatically.
        userStatusEditText =findViewById(R.id.userProfileStatus);
        userCollegeEditText =findViewById(R.id.college_field);
        userMajorEditText =findViewById(R.id.major_field);
        userChangeImage=findViewById(R.id.userChangeImage);
        userPic = findViewById(R.id.userProfileImageView);
        saveProfilePage =findViewById(R.id.saveProfile);

        authUser = FirebaseAuth.getInstance();

        authUserListener = new FirebaseAuth.AuthStateListener() //Listener called when there is a change in the authentication state.
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                    ProfileActivity.this.finish();
                }
            }
        };

        mProgress = new ProgressDialog(this);

        userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(authUser.getCurrentUser().getUid());
        storageRef = FirebaseStorage.getInstance().getReference();

        saveProfilePage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (hasWindowFocus())
                {
                    saveUserProfile();
                }
            }
        });

        userChangeImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectPic();
            }
        });
    }

    private void saveUserProfile()
    {
        final String username = userNameEditText.getText().toString().trim();
        final String userStatus = userStatusEditText.getText().toString().trim();
        final String college = userCollegeEditText.getText().toString().trim();
        final String major= userMajorEditText.getText().toString().trim();

        if( !TextUtils.isEmpty(username) && !TextUtils.isEmpty(major) && !TextUtils.isEmpty(college))
        {
           if( imageHoldUri != null )
            {
                mProgress.setTitle("Saving Profile");
                mProgress.show();
                StorageReference mChildStorage = storageRef.child("User_Profile").child(imageHoldUri.getLastPathSegment());
                mChildStorage.putFile(imageHoldUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {

                        final Uri imageUrl = taskSnapshot.getDownloadUrl();

                        userDB.child("username").setValue(username);
                        userDB.child("status").setValue(userStatus);
                        userDB.child("college").setValue(college);
                        userDB.child("major").setValue(major);
                        userDB.child("userid").setValue(authUser.getCurrentUser().getUid());
                        userDB.child("imageurl").setValue(imageUrl.toString());

                        mProgress.dismiss();
                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                        ProfileActivity.this.finish();
                    }
                });
            }
            else
            {
                Toast.makeText(ProfileActivity.this, "Please select the profile pic", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(ProfileActivity.this, "Please enter username, college, and major", Toast.LENGTH_LONG).show();
        }

    }

    private void selectPic()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Device", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("Take Photo"))
                {
                    cameraIntent();
                }
                else if (items[item].equals("Choose from Library"))
                {
                    galleryIntent();
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Log.d("gola", "entered here");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Log.d("gola", "entered here");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {

            CropImage.activity(data.getData()).start(ProfileActivity.this);

        }
        else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).start(ProfileActivity.this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                imageHoldUri = result.getUri();
                userPic.setImageURI(imageHoldUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }
}