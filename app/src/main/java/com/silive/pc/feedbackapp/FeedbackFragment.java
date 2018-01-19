package com.silive.pc.feedbackapp;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silive.pc.feedbackapp.Models.InternationalDelegates;
import com.silive.pc.feedbackapp.Models.Students;
import com.silive.pc.feedbackapp.Models.Visitors;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {

    private EditText nameEditText, designationEditText, organisationEditText, organisationAddressEditText,
            emailIdEditText, mobileEditText, feedbackEditText;
    private TextView timeTextView, dateTextView;
    private ImageView signatureImageView, photoImageView;
    private Button sendButton, photoButton, signatureButton;
    private static final int CAMERA_REQUEST = 1888;
    private static int flag = 0;
    private static int TAKE_OR_PICK = 1;

    private Bitmap imageBitmap;
    private String picturePath;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference studentsDatabaseReference, delegatesDatabaseReference, visitorsDatabaseRefernce;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        final String type = this.getArguments().getString("type");

        //Log.i("output", type);

        firebaseDatabase = FirebaseDatabase.getInstance();

        if (type == "students") {
            studentsDatabaseReference = firebaseDatabase.getReference().child("Students");
        }else
            if (type == "delegates")
            {
                delegatesDatabaseReference = firebaseDatabase.getReference().child("International Delegates");
            }else
                if (type == "visitors"){
                    visitorsDatabaseRefernce = firebaseDatabase.getReference().child("visitors");
                }

        nameEditText = (EditText) rootView.findViewById(R.id.name);
        designationEditText = (EditText) rootView.findViewById(R.id.designation);
        organisationEditText = (EditText) rootView.findViewById(R.id.organisation);
        organisationAddressEditText = (EditText) rootView.findViewById(R.id.organisation_address);
        emailIdEditText = (EditText) rootView.findViewById(R.id.email);
        mobileEditText = (EditText) rootView.findViewById(R.id.mobile);
        feedbackEditText = (EditText) rootView.findViewById(R.id.feedback);

        dateTextView = (TextView) rootView.findViewById(R.id.date);
        timeTextView = (TextView) rootView.findViewById(R.id.time);

        signatureImageView = (ImageView) rootView.findViewById(R.id.signature);
        photoImageView = (ImageView) rootView.findViewById(R.id.photo);

        sendButton = (Button) rootView.findViewById(R.id.send_button);
        photoButton = (Button) rootView.findViewById(R.id.photo_button);
        signatureButton = (Button) rootView.findViewById(R.id.signature_button);

        final String name = nameEditText.getText().toString();
        final String designation = designationEditText.getText().toString();
        final String organisation = organisationEditText.getText().toString();
        final String organisationAddress = organisationAddressEditText.getText().toString();
        final String emailId = emailIdEditText.getText().toString();
        final String mobile = mobileEditText.getText().toString();
        final String feedback = feedbackEditText.getText().toString();



        java.util.Date noteTS = Calendar.getInstance().getTime();

        String time = "hh:mm"; // 12:00
        timeTextView.setText(DateFormat.format(time, noteTS));
        time = timeTextView.getText().toString();

        String date = "dd MM yyyy"; // 01 January 2013
        dateTextView.setText(DateFormat.format(date, noteTS));
        date = dateTextView.getText().toString();

        signatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 TAKE_OR_PICK = 1;
                Intent takePictureIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
                //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 20){
                    Toast.makeText(getContext(), "Character limit is 20", Toast.LENGTH_SHORT).show();
                }
                Validation.hasText(nameEditText);
            }
        });

        designationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                Validation.hasText(designationEditText);
            }
        });

        organisationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Validation.hasText(organisationEditText);
            }
        });

        emailIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Validation.isEmailAddress(emailIdEditText, true);
            }
        });

        mobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Validation.isPhoneNumber(mobileEditText, true);
            }
        });

        feedbackEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Validation.hasText(feedbackEditText);
            }
        });

        final String finalDate = date;
        final String finalTime = time;
        sendButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                if (!checkValidation ()){
                    Toast.makeText(getContext(), "Form contains error", Toast.LENGTH_LONG).show();
                    flag = 1;

                }


                if (type == "students" && flag == 0) {
                    Students students = new Students(nameEditText.getText().toString(), designationEditText.getText().toString(),
                            organisationEditText.getText().toString(), organisationAddressEditText.getText().toString(),
                            emailIdEditText.getText().toString(), mobileEditText.getText().toString(), feedbackEditText.getText().toString(),
                            null, null, finalDate, finalTime);
                    studentsDatabaseReference.push().setValue(students);
                    Toast.makeText(getContext(), "Uploading Complete..", Toast.LENGTH_SHORT).show();
                }else if (type == "delegates" && flag == 0){
                    InternationalDelegates delegates = new InternationalDelegates(nameEditText.getText().toString(), designationEditText.getText().toString(),
                            organisationEditText.getText().toString(), organisationAddressEditText.getText().toString(),
                            emailIdEditText.getText().toString(), mobileEditText.getText().toString(), feedbackEditText.getText().toString(),
                            null, null, finalDate, finalTime);
                    delegatesDatabaseReference.push().setValue(delegates);
                    Toast.makeText(getContext(), "Uploading Complete..", Toast.LENGTH_SHORT).show();
                }else if (type == "visitors" && flag == 0){
                    Visitors visitors = new Visitors(nameEditText.getText().toString(), designationEditText.getText().toString(),
                            organisationEditText.getText().toString(), organisationAddressEditText.getText().toString(),
                            emailIdEditText.getText().toString(), mobileEditText.getText().toString(), feedbackEditText.getText().toString(),
                            null, null, finalDate, finalTime);
                    visitorsDatabaseRefernce.push().setValue(visitors);
                    Toast.makeText(getContext(), "Uploading Complete..", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getContext(), "Uploading Complete..", Toast.LENGTH_SHORT).show();
                //Log.i("output", "hello");
                nameEditText.setText("");
                designationEditText.setText("");
                organisationEditText.setText("");
                organisationAddressEditText.setText("");
                emailIdEditText.setText("");
                mobileEditText.setText("");
                feedbackEditText.setText("");

            }
        });


        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
           // Bundle extras = data.getExtras();
            //Uri photoUrl = data.getData();
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            photoImageView.setImageBitmap(photo);

        }*/
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK && null != data  && TAKE_OR_PICK == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoImageView.setImageBitmap(photo);
            if (imageBitmap != null) {
                try {
                    long fileName = System.currentTimeMillis();
                    File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + fileName + ".jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 300, 300, true);
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    File imageFile = new File(Environment.getExternalStorageDirectory() +File.separator + "photo_" + fileName + ".jpg");
                    picturePath = imageFile.getPath();
                    Toast.makeText(getContext(), picturePath, Toast.LENGTH_SHORT).show();
                    //System.out.println("FILE PATH -> "+picturePath);
                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private boolean checkValidation() {
        boolean validation = true;

        if (!Validation.hasText(nameEditText)){
            validation = false;
        }
        if (!Validation.hasText(designationEditText)){
            validation = false;
        }
        if (!Validation.hasText(organisationEditText)){
            validation = false;
        }
        if (!Validation.hasText(feedbackEditText)){
            validation = false;
        }
        if (!Validation.isEmailAddress(emailIdEditText, true)) {
            validation = false;
        }
        if (!Validation.isPhoneNumber(mobileEditText, false)) {
            validation = false;
        }

        return validation;
    }
}
