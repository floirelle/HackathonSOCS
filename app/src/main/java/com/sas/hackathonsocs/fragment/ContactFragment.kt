package com.sas.hackathonsocs.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.sas.Firestore
import com.sas.hackathonsocs.AddContactActivity
import com.sas.hackathonsocs.LoginActivity
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.adapter.ContactAdapter
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_contact.view.*
import java.util.*

class ContactFragment : Fragment() {
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        var item: MenuItem = menu.findItem(R.id.seach_btn)
        var searchView = item.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                contactAdapter.filter.filter(p0)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        view.rv_contact.layoutManager =  LinearLayoutManager(this.activity)
        view.rv_contact.setHasFixedSize(true)
        return view
    }


    private fun setupRecycleView(){

        val email = this.activity!!
            .getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            .getString("email", "").toString()
//        Toast.makeText(this.context, email, Toast.LENGTH_SHORT).show()

        btn_add_contact.setOnClickListener({
            val intent = Intent(this.context, AddContactActivity::class.java)
            startActivity(intent)
        })

        Firestore.instance.collection("users")
            .document(email)
            .collection("contact")
            .addSnapshotListener{ value, e ->
                if(e != null){
                    no_contact.visibility = View.VISIBLE
                    return@addSnapshotListener}
                var contacts = ArrayList<User>()
                for (doc in value!!) {
                    contacts.add(doc.toObject(User::class.java)!!)
                }

                contacts.sortBy { it.name }

                contactAdapter = ContactAdapter(contacts)
                rv_contact.adapter = contactAdapter
                contactAdapter.notifyDataSetChanged()
            }
    }
}