package com.ericchee.sendsms

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val REQUEST_SMS_SEND_PERMISSION = 1234
        val REQUEST_CALL_PHONE_PERMISSION = 5678
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnComposeSMS.setOnClickListener {


            val number = "1234567890"
            val uri = Uri.parse("smsto:$number")
            val smsIntent = Intent(Intent.ACTION_VIEW, uri)
            smsIntent.putExtra("sms_body", "Some SMS message content")
            startActivity(smsIntent)

        }

        btnComposeMMS.setOnClickListener {

            val number = "1234567890"
            val uri = Uri.parse("mmsto:$number")
            val fileUri = Uri.parse("some file path")
            val smsIntent = Intent(Intent.ACTION_SEND).apply {
                data = uri
                type = "image/jpg"
                putExtra("sms_body", "Some SMS message content")
                putExtra(Intent.EXTRA_STREAM, fileUri)
            }
            startActivity(smsIntent)

        }

        btnSendSMSDirectly.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

                // Need to request SEND_SMS permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    REQUEST_SMS_SEND_PERMISSION)

            } else {

                // Has Permissions, Send away!

                val smsManager = SmsManager.getDefault()
                val number = "1234567890"
                val message = "hey u up? wyd"
                smsManager.sendTextMessage(
                    number,
                    null,
                    message,
                    null,
                    null)
            }

        }

        btnCall.setOnClickListener {


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

                // Need to request CALL PHONE permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_PHONE_PERMISSION)

            } else {

                // Has Permissions, Call away!
                val callIntent = Intent().apply {
                    action = Intent.ACTION_CALL
                    data = Uri.parse("tel:01234567890")
                }
                startActivity(callIntent)

            }


        }





    }
}
