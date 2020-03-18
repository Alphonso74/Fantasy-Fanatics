package psu.ajm6684.myapplication;

import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

public interface NBADAO {


    @Insert
    void insert(EntityNBA...user);

    @Update
    void update(EntityNBA...user);

    @Delete
    void delete(EntityNBA...user);

    @Query("")
    void delete(String id);

}

@Database(entities = {EntityNBA.class}, version = 1, exportSchema = false)
abstract class NBADatabase extends RoomDatabase {

    public abstract NBADAO daoAccess();
}