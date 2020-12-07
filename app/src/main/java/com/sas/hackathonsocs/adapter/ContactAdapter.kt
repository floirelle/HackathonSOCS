package com.sas.hackathonsocs.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sas.hackathonsocs.ContactActivity
import com.sas.hackathonsocs.R
import com.sas.hackathonsocs.model.User
import kotlinx.android.synthetic.main.recyclerview_contact.view.*

class ContactAdapter (private val contactList: ArrayList<User>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(), Filterable {


    private var contactListFull: List<User> = ArrayList(contactList);

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var filteredUsers = ArrayList<User>();

                if(p0 == null || p0!!.length == 0) {
                    filteredUsers.addAll(contactListFull)
                } else{
                    var filterPattern = p0.toString().toLowerCase().trim()
                    for(user: User in contactListFull){
                        if( user.name.toLowerCase().contains(filterPattern)){
                            filteredUsers.add(user)
                        }
                    }
                }

                var results = FilterResults()
                results.values = filteredUsers
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                contactList.clear()
                contactList.addAll(p1!!.values as List<User>)
                notifyDataSetChanged()
            }
        }
    }
    
    class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var text_contact: TextView

        init{
            text_contact = itemView.tv_contact_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_contact, parent, false)

        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        var curr = contactList[position]
        holder.text_contact.text = curr.name
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, ContactActivity::class.java)
            intent.putExtra("user", curr)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = contactList.size
}