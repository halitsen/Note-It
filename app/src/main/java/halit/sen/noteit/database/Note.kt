package halit.sen.noteit.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "note_table")
 class Note :Serializable{

    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L

    @ColumnInfo(name = "note_title")
    var noteTitle: String = ""

    @ColumnInfo(name = "note_description")
    var noteDescription: String = ""

    @ColumnInfo(name = "note_last_edit")
    var noteLastEdit: Long = System.currentTimeMillis()

    @ColumnInfo(name = "note_is_locked")
    var noteIsLocked: Boolean = false

}
