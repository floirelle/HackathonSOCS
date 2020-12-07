package com.sas.hackathonsocs.fragment

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
        var contacts = ArrayList<User>()

        contacts.add(User("Shania", "082299880011","shania@gmail.com","Female"))
        contacts.add(User("Renjun", "0822990033","renjun@gmail.com","Male"))
        contacts.add(User("Jaemin","0811223344","jaemin@gmail.com","Male"))
        contacts.add(User("Adi","0811223344","adi@gmail.com","Male"))
        contacts.add(User("Stefie","0811223344","stefie@gmail.com","Female"))
        contacts.add(User("Jeno","0811223344","jeno@gmail.com","Male"))
        contacts.add(User("Chenle","0811223344","lele@gmail.com","Male"))
        contacts.add(User("Jisung","0811223344","jisung@gmail.com","Male"))
        contacts.add(User("Taeyong","0811223344","tiwai@gmail.com","Male"))
        contacts.add(User("Budi","0811223344","bufi@gmail.com","Male"))

        contacts.sortBy { it.name }

        contactAdapter = ContactAdapter(contacts)
        rv_contact.adapter = contactAdapter
    }
}