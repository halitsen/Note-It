package halit.sen.noteit.main

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import halit.sen.noteit.R
import halit.sen.noteit.addNote.AddNoteActivity
import halit.sen.noteit.database.Note
import halit.sen.noteit.utils.SharedPreference
import halit.sen.noteit.utils.displayNoteInList
import halit.sen.noteit.utils.getCurentTime
import halit.sen.noteit.utils.openInfoDialog


class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.note_list_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            if(item.noteIsLocked){
                getPasswordDialog(holder,item)
            }else{
                val editNoteIntent = Intent(holder.itemView.context,AddNoteActivity::class.java)
                editNoteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                editNoteIntent.putExtra("note",item)
                holder.itemView.context.startActivity(editNoteIntent)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val res = itemView.context.resources
        val description: TextView = itemView.findViewById(R.id.description_text)
        val lastEdit: TextView = itemView.findViewById(R.id.last_edit_text)
        val isNoteLockedImage: ImageView = itemView.findViewById(R.id.lock)

        fun bind(item: Note) {
            description.text = displayNoteInList(item.noteDescription)
            lastEdit.text = getCurentTime(item.noteLastEdit)
            if (item.noteIsLocked) {
                isNoteLockedImage.setImageResource(R.drawable.ic_lock_close)
            } else {
                isNoteLockedImage.setImageResource(R.drawable.ic_lock_open)
            }
        }
    }

    fun getPasswordDialog(holder: ViewHolder, item: Note){

        val dialog = Dialog(holder.itemView.context)
        dialog.setContentView(R.layout.password_dialog)
        val notePreference = SharedPreference(holder.itemView.context)
        val pass: EditText = dialog.findViewById(R.id.password_edit_text)
        val cancel : TextView = dialog.findViewById(R.id.cancel_text)
        val okey : TextView = dialog.findViewById(R.id.okey__text)

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        okey.setOnClickListener {
            val password = pass.text.trim().toString()
            if(password.equals(notePreference.getPassword())){
                val editNoteIntent = Intent(holder.itemView.context,AddNoteActivity::class.java)
                editNoteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                editNoteIntent.putExtra("note",item)
                holder.itemView.context.startActivity(editNoteIntent)
                dialog.dismiss()
            }else{
                openInfoDialog(holder.itemView.context,holder.itemView.context.getString(R.string.wron_pass_warning),holder.itemView.context.getString(R.string.password))
                return@setOnClickListener
            }
        }
        dialog.show()
    }
}