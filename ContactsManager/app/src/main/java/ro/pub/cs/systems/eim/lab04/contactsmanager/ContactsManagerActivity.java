package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ro.pub.cs.systems.eim.lab04.contactsmanager.general.Constants;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button showButton;
    private Button saveButton;
    private Button cancelButton;
    private LinearLayout hideContainer;
    private EditText nameBox;
    private EditText phoneBox;
    private EditText emailBox;
    private EditText addressBox;
    private EditText jobTitleBox;
    private EditText companyBox;
    private EditText websiteBox;
    private EditText imBox;


    private ShowButtonListener showButtonListener = new ShowButtonListener();
    private class ShowButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (showButton.getText().equals("SHOW ADDITIONAL FIELDS")) {
                showButton.setText("HIDE ADDITIONAL FIELDS");
                hideContainer.setVisibility(View.VISIBLE);
            } else {
                showButton.setText("SHOW ADDITIONAL FIELDS");
                hideContainer.setVisibility(View.GONE);
            }
        }
    }

    private SaveButtonListener saveButtonListener = new SaveButtonListener();
    private class SaveButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String name = nameBox.getText().toString();
            String phone = phoneBox.getText().toString();
            String email = emailBox.getText().toString();
            String address = addressBox.getText().toString();
            String company = companyBox.getText().toString();
            String jobTitle = jobTitleBox.getText().toString();
            String website = websiteBox.getText().toString();
            String im = imBox.getText().toString();


            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            if (name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (phone != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            }
            if (email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (jobTitle != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }
            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
            //startActivity(intent);
            startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
        }
    }

    private CancelButtonListener cancelButtonListener = new CancelButtonListener();
    private class CancelButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //finish();
            setResult(Activity.RESULT_CANCELED, new Intent());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showButton = (Button)findViewById(R.id.show);
        showButton.setOnClickListener(showButtonListener);
        saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(saveButtonListener);
        cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(cancelButtonListener);
        hideContainer = (LinearLayout)findViewById(R.id.hideContainer);
        phoneBox = (EditText)findViewById(R.id.phoneBox);
        nameBox = (EditText)findViewById(R.id.nameBox);
        emailBox = (EditText)findViewById(R.id.emailBox);
        addressBox = (EditText)findViewById(R.id.addressBox);
        companyBox = (EditText)findViewById(R.id.companyBox);
        jobTitleBox = (EditText)findViewById(R.id.jobTitleBox);
        websiteBox = (EditText)findViewById(R.id.websiteBox);
        imBox = (EditText)findViewById(R.id.imBox);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneBox.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
