package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insertbook(bookEntity: BookEntity)
    @Delete
    fun deletebook(bookEntity: BookEntity)
    @Query("SELECT * FROM books")
    fun getallbooks():List<BookEntity>

    @Query("SELECT * FROM books where book_id=:bookid")
    fun getbookid(bookid:String):BookEntity
}