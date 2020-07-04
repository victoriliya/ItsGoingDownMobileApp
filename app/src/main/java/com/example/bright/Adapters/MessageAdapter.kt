package com.example.bright.Adapters

import android.R.string
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bright.Model.Message
import com.example.bright.R
import com.example.bright.Services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MessageAdapter(val context: Context, val messages: ArrayList<Message>): RecyclerView.Adapter<MessageAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bindMessage(context, messages[position])
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userImage = itemView?.findViewById<ImageView>(R.id.messageUserImage)
        val timeStamp = itemView?.findViewById<TextView>(R.id.timeStampLbl)
        val userName = itemView?.findViewById<TextView>(R.id.messageUserNameLbl)
        val messageBody = itemView?.findViewById<TextView>(R.id.messageBodyLbl)

        fun bindMessage(context: Context, message: Message) {
            val resourceId =
                context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage?.setImageResource(resourceId)
            userImage?.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName?.text = message.userName
            timeStamp?.text = returnDateString(message.timeStamp)
            messageBody?.text = message.message
        }

    }

    fun returnDateString(isoString: String): String{

        /*val source: string = "7/10/2019 2:52:52 PM"

        val result: DateTime = DateTime.ParseExact(
            source,
            "M/d/yyyy h:m:s tt",  // if "7/10/2019" means "10 July 2019"
            CultureInfo.InvariantCulture
        )*/

        /*yyyy-MM-dd'T'HH:mm:ss.SSS'z'*/

        val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'z'", Locale.getDefault())
        isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
        var convertedDate = Date()
        try{
            convertedDate = isoFormatter.parse(isoString)
        }catch (e: ParseException){
            Log.d("PARSE", "Cannot parse date $e")
        }

        val outDateString = SimpleDateFormat("E, h:mm a", Locale.getDefault())

        return outDateString.format(convertedDate)

    }
}