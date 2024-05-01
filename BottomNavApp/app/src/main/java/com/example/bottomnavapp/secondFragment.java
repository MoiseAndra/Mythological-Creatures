package com.example.bottomnavapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bottomnavapp.ml.Model2x1282x64;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tensorflow.lite.support.image.TensorImage;


public class secondFragment extends Fragment{


    ImageView imageView;
    Button btOpen, btGal, btPred;
    Bitmap copyBitmap;
    ActivityResultLauncher<Intent> activityResultLauncher;


    List<String> classes = new ArrayList<String>();
    String resultString;
    float firstBest, secondBest;

    public secondFragment() {
    }

    public static secondFragment newInstance(String param1, String param2) {
        secondFragment fragment = new secondFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_second, container, false);
       return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image_view);
        btOpen = view.findViewById(R.id.bt_open);
        btGal = view.findViewById(R.id.bt_gallery);
        btPred = view.findViewById(R.id.bt_pred);


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, 100);
        }

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bitmap bitmap  = (Bitmap) result.getData().getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                    byte[] byteArr = stream.toByteArray();
                    Bitmap captureImage = BitmapFactory.decodeByteArray(byteArr,0, byteArr.length);
                    imageView.setImageBitmap(captureImage);
                    if (imageView.getDrawable() != null) {
                        copyBitmap = captureImage;
                        btPred.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null)
                {
                    activityResultLauncher.launch(intent);
                }
            }
        });



        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        ActivityResultLauncher<Intent> galleryResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                    try{
                        final Uri imageUri = result.getData().getData();
                        final InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        if (imageView.getDrawable() != null) {
                            copyBitmap = selectedImage;
                            btPred.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        btGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null)
                {
                    galleryResultLaucher.launch(intent);
                }
            }
        });



        btPred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFileLabels();

                try {
                    Model2x1282x64 model =  Model2x1282x64.newInstance(requireContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 180, 180, 3}, DataType.FLOAT32);

                    copyBitmap = Bitmap.createScaledBitmap(copyBitmap, 180, 180, true);

                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(copyBitmap);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    Model2x1282x64.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    resultString = classes.get(takeMaxprocent(outputFeature0.getFloatArray()));
                    System.out.println(resultString);

                    secondBest = secondMax(outputFeature0.getFloatArray());

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                if (firstBest - secondBest <0.15) {
                    Context context = requireContext();
                    CharSequence text = "I think it's " + resultString + " but I'm not sure." ;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                Fragment fragment = null;
                System.out.println(resultString);
                fragment = new LibFragment(resultString);
                replaceFragment(fragment);

            }
        });
    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.buttons);
        layout.setVisibility(View.GONE);
        LinearLayout layout2 = (LinearLayout) getView().findViewById(R.id.findandanswear);
        layout2.setVisibility(View.GONE);
        transaction.replace(R.id.secondFragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public int takeMaxprocent(float[] floatArray)
    {
        int index = 0;
        float max=-1;
        for (int i=0;i<floatArray.length;i++)
        {
            System.out.print(floatArray[i]+" ");
            if (floatArray[i]>max)
            {
                index =i;
                max=floatArray[i];
            }
        }
        System.out.println();
        System.out.print("MAXIMUL ESTE" + max+"\n");
        firstBest = max;
        return index;
    }

     public void readFileLabels()
     {
         InputStream is = requireContext().getResources().openRawResource(R.raw.labels);
         InputStreamReader inputStreamReader = new InputStreamReader(is);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         try {
         String line;

         while ((line = bufferedReader.readLine()) != null) {
             classes.add(line);
         }
         bufferedReader.close();
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }

     public float secondMax(float[] floatArray)
     {
         Arrays.sort(floatArray);
         return floatArray[floatArray.length - 2];
     }
}