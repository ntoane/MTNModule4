package com.example.hardwareapis

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_email.*


class EmailFragment : Fragment(R.layout.fragment_email) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendBtn.setOnClickListener {
            val recipientEmail = toEmail.text.toString()
            val subject = subject.text.toString()
            val body = body.text.toString()

            // call to send email Intent
            if(recipientEmail.trim().isNotEmpty()) {
                sendEmail(recipientEmail, subject, body)
            } else {
                Toast.makeText(activity, "Please type recipient email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            //Toast.makeText(this@EmailFragment, e.message, Toast.LENGTH_LONG).show()
        }

    }
}