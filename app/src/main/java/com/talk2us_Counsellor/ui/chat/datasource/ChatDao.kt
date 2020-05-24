package com.talk2us_Counsellor.ui.chat.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.talk2us_Counsellor.models.Message

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun sendMessage(message: Message)

    @Query("DELETE FROM messages")
    fun deleteAll()

    @Query("SELECT * FROM messages")
    fun getAllMessages():LiveData<List<Message>>

    @Delete
    fun delete(message: Message)
    @Update
    fun updateLast(message: Message)
}