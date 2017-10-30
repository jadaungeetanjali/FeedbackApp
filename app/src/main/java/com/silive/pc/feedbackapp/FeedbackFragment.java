package com.silive.pc.feedbackapp;


import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    private Button sendButton, photoButton;
    private static final int CAMERA_REQUEST = 1888;

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

        Log.i("output", type);

        firebaseDatabase = FirebaseDatabase.getInstance();

        if (type == "students") {
            studentsDatabaseReference = firebaseDatabase.getReference().child("Students");
        }else
            if (type == "delegates"){
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


        final String name = nameEditText.getText().toString();
        final String designation = designationEditText.getText().toString();
        final String organisation = organisationEditText.getText().toString();
        final String organisationAddress = organisationAddressEditText.getText().toString();
        final String emailId = emailIdEditText.getText().toString();
        final String mobile = mobileEditText.getText().toString();
        final String feedback = feedbackEditText.getText().toString();

        /*
        if(name.length() == 0){
            nameEditText.setError("enter name");
        }else{
            nameEditText.setError(null);
        }
            if (designation.length() == 0){
                designationEditText.setError("enter designation");
            }else{
                designationEditText.setError(null);
            }
                if (organisation.length() == 0){
                    organisationEditText.setError("enter organisation");
                }else{
                    organisationEditText.setError(null);
                }
                    if (emailId.length() == 0){
                        emailIdEditText.setError("enter email");
                    }else{
                        emailIdEditText.setError(null);
                    }
                        if (mobile.length() == 0){
                            mobileEditText.setError("enter mobile");
                        }else{
                            mobileEditText.setError(null);
                        }
                            if (feedback.length() == 0) {
                                feedbackEditText.setError("enter feedback");
                            }else {
                                feedbackEditText.setError(null);
                            } */
        java.util.Date noteTS = Calendar.getInstance().getTime();

        String time = "hh:mm"; // 12:00
        timeTextView.setText(DateFormat.format(time, noteTS));
        time = timeTextView.getText().toString();

        String date = "dd MM yyyy"; // 01 January 2013
        dateTextView.setText(DateFormat.format(date, noteTS));
        date = dateTextView.getText().toString();

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

            }
        });

        final String finalDate = date;
        final String finalTime = time;
        sendButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                if (type == "students") {
                    Students students = new Students(nameEditText.getText().toString(), designationEditText.getText().toString(),
                            organisationEditText.getText().toString(), organisationAddressEditText.getText().toString(),
                            emailIdEditText.getText().toString(), mobileEditText.getText().toString(), feedbackEditText.getText().toString(),
                            null, null, finalDate, finalTime);
                    studentsDatabaseReference.push().setValue(students);
                }else if (type == "delegates"){
                    InternationalDelegates delegates = new InternationalDelegates(nameEditText.getText().toString(), designationEditText.getText().toString(),
                            organisationEditText.getText().toString(), organisationAddressEditText.getText().toString(),
                            emailIdEditText.getText().toString(), mobileEditText.getText().toString(), feedbackEditText.getText().toString(),
                            null, null, finalDate, finalTime);
                    delegatesDatabaseReference.push().setValue(delegates);
                }else if (type == "visitors"){
                    Visitors visitors = new Visitors(nameEditText.getText().toString(), designationEditText.getText().toString(),
                            organisationEditText.getText().toString(), organisationAddressEditText.getText().toString(),
                            emailIdEditText.getText().toString(), mobileEditText.getText().toString(), feedbackEditText.getText().toString(),
                            null, null, finalDate, finalTime);
                    visitorsDatabaseRefernce.push().setValue(visitors);
                }

                Toast.makeText(getContext(), "Uploading Complete..", Toast.LENGTH_SHORT).show();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri photoUrl = data.getData();
            //Log.i("photouri", String.valueOf(photoUrl));
            //Log.v("output", String.valueOf(String.valueOf(photoUrl)));
            Picasso.with(photoImageView.getContext()).load(Uri.parse(String.valueOf(String.valueOf(photoUrl)))).into(photoImageView);
        }
    }

}
