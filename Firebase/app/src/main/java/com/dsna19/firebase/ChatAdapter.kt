package com.dsna19.firebase

import android.content.Context
import android.provider.ContactsContract
import android.text.method.HideReturnsTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(val context : Context, val chatList : ArrayList<ChatData>, val my_nickname: String)
    : RecyclerView.Adapter<ChatAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickname = itemView.findViewById<TextView>(R.id.TextView_nickname)
        val msg = itemView.findViewById<TextView>(R.id.TextView_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder  {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var currentChat = chatList[position]
        holder.nickname.text = currentChat.nickname
        holder.msg.text = currentChat.msg

        if (currentChat.nickname.equals(this.my_nickname)) {
            holder.msg.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            holder.nickname.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun addChat(chat : ChatData) {
        chatList.add(chat)
        notifyItemChanged(chatList.size - 1)
    }
}
