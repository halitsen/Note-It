package halit.sen.noteit.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * from note_table WHERE noteId = :key")
    fun get(key: Long): LiveData<Note>

    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Delete
    fun deleteNote(note:Note)

    @Query("SELECT * FROM note_table ORDER BY noteId DESC LIMIT 1")
    fun getLatestNote(): Note?

    @Query("SELECT * from note_table WHERE noteId = :key")
    fun getNightWithId(key: Long): LiveData<Note>

}