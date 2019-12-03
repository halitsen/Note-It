package halit.sen.noteit.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import halit.sen.noteit.R
import halit.sen.noteit.database.Note
import halit.sen.noteit.displayNoteInList
import halit.sen.noteit.getCurentTime


class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    var data = listOf<Note>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.note_list_item_row, parent, false)

        return  ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data.get(position)
        holder.bind(item)
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val res = itemView.context.resources

        val description : TextView = itemView.findViewById(R.id.description_text)
        val lastEdit : TextView = itemView.findViewById(R.id.last_edit_text)
        val isNoteLockedImage: ImageView = itemView.findViewById(R.id.lock)

       fun bind(item: Note) {
            description.text = displayNoteInList(item.noteDescription)
            lastEdit.text = getCurentTime(item.noteLastEdit)
           if(item.noteIsLocked){
               isNoteLockedImage.setImageResource(R.drawable.ic_lock_close)
           }else{
               isNoteLockedImage.setImageResource(R.drawable.ic_lock_open)
           }
        }
    }
}