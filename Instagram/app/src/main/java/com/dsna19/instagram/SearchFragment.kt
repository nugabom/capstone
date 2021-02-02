package com.dsna19.instagram

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    lateinit var search_bar : EditText
    lateinit var recyclerView: RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var mUsers : ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        search_bar = view.findViewById(R.id.search_bar)

        mUsers = ArrayList<User>()
        userAdapter = UserAdapter(context, mUsers, true)
        recyclerView.adapter = userAdapter

        readUsers()

        var textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchUsers(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        search_bar.addTextChangedListener(textWatcher)

        return view
    }

    fun searchUsers(s : String) {
        val query = FirebaseDatabase.getInstance()
            .getReference("Users")
            .orderByChild("username")
            .startAt(s)
            .endAt(s+"\uf8ff")

        val queryEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mUsers.clear()
                for (postSnapshot in snapshot.children) {
                    val user : User? =  postSnapshot.getValue(User::class.java)
                    mUsers.add(user!!)
                }

                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        query.addValueEventListener(queryEventListener)
    }

    fun readUsers() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Users")

        val readEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (search_bar.text.toString().equals("")) {
                    mUsers.clear()
                    for (postSnapshot in snapshot.children) {
                        var user : User? = postSnapshot.getValue(User::class.java)
                        mUsers.add(user!!)
                    }

                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        reference.addValueEventListener(readEventListener)
    }
}