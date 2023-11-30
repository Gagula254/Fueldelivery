package my.fueldelivery.app.attendantProductPanel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import my.fueldelivery.app.R;

public class attendant_postProduct extends AppCompatActivity {

    //    ImageButton imageButton;
    Button post_product;
    Spinner Products;
    TextInputLayout desc,qty,pri;
    String descrption,quantity,price,products;
    //    Uri imageuri;
//    private Uri mcropimageuri;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,dataa;
    FirebaseAuth Fauth;
    StorageReference ref;
    String AttendantId , RandomUID , State, City , Area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendant_post_product);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Products = (Spinner)findViewById(R.id.products);
        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.Quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        post_product = (Button) findViewById(R.id.post);
        Fauth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("ProductDetails");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataa = firebaseDatabase.getInstance().getReference("Attendant").child(userid);
            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Attendant attendantt = snapshot.getValue(Attendant.class);
                    State = attendantt.getState();
                    City = attendantt.getCity();
                    Area = attendantt.getArea();
//                    imageButton = (ImageButton) findViewById(R.id.image_upload);

//                    imageButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onSelectImageclick(v);
//                        }
//                    });
                    post_product.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            products = Products.getSelectedItem().toString().trim();
                            descrption = desc.getEditText().getText().toString().trim();
                            quantity = qty.getEditText().getText().toString().trim();
                            price = pri.getEditText().getText().toString().trim();

                            if(isValid()){
                                uploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            Log.e("Error: ",e.getMessage());
        }

    }

    private void uploadImage() {

//        if(imageuri == null){
        final ProgressDialog progressDialog = new ProgressDialog(attendant_postProduct.this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();
        RandomUID = UUID.randomUUID().toString();
        ref = storageReference.child(RandomUID);
        AttendantId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            FoodDetails info = new FoodDetails(dishes,quantity,price,descrption,String.valueOf(uri),RandomUID,ChefId);
//                            firebaseDatabase.getInstance().getReference("FoodDetails").child(State).child(City).child(Area).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID)
//                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                            progressDialog.dismiss();
//                                            Toast.makeText(chef_postDish.this,"Dish Posted Successfully!",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.dismiss();
//                    Toast.makeText(chef_postDish.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                    progressDialog.setMessage("Uploaded "+(int) progress+"%");
//                    progressDialog.setCanceledOnTouchOutside(false);
//                }
//            });
////        }
//
        ProductDetails info = new ProductDetails(products, quantity, price, descrption, RandomUID, AttendantId);

// Assuming 'info' contains all the necessary information including the image URL
        firebaseDatabase.getInstance().getReference("ProductDetails")
                .child(State).child(City).child(Area)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(RandomUID)
                .setValue(info)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(attendant_postProduct.this, "Product Posted Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isValid() {

        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");

        boolean isValidDescription = false,isValidPrice=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(descrption)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        }else{
            desc.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter number of Plates or Items");
        }else{
            isValidQuantity=true;
        }
        if(TextUtils.isEmpty(price)){
            pri.setErrorEnabled(true);
            pri.setError("Please Mention Price");
        }else{
            isValidPrice=true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidPrice)?true:false;
        return isValid;
    }
//    private void startCropImageActivity(Uri imageuri){
//        CropImage.activity(imageuri)
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setMultiTouchEnabled(true)
//                .start(this);
//    }
//    private void onSelectImageclick(View v){
//        CropImage.startPickImageActivity(this);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (mcropimageuri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startCropImageActivity(mcropimageuri);
//        } else {
//            Toast.makeText(this, "Cancelling! Permission Not Granted", Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    @SuppressLint("NewApi")
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
//            imageuri = CropImage.getPickImageResultUri(this,data);
//            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
//                mcropimageuri = imageuri;
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
//            }else{
//                startCropImageActivity(imageuri);
//            }
//        }
//        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if(resultCode==RESULT_OK){
//                ((ImageButton) findViewById(R.id.image_upload)).setImageURI(result.getUri());
//                Toast.makeText(this,"Cropped Successfully!",Toast.LENGTH_SHORT).show();
//            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
//                Toast.makeText(this,"Failed To Crop"+result.getError(),Toast.LENGTH_SHORT).show();
//
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}