package psu.ajm6684.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class NBARepository {

    private String DB_NAME = "nba_task";

    
    //private NBADAO nbadao;
    private NBADatabase nbaDatabase;


    public NBARepository(Context context) {
        nbaDatabase = Room.databaseBuilder(context, NBADatabase.class, DB_NAME).build();
    }

//    public void insertPlayer(String name, String email, String password, Integer id) {
//
//        insertTask(name, email, password, id);
//    }

    public void insertPlayer(String firstName, String lastName, String email, String password, Integer id) {

        EntityNBA nba = new EntityNBA(id);

        nba.setFirstName(firstName);
        nba.setLastName(email);
        nba.setEmail(email);
        nba.setPassword(password);


    }

    public void insertTask(final EntityNBA EntityNBA) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                nbaDatabase.daoAccess().insert(EntityNBA);
                return null;
            }
        }.execute();
    }

    public void updateTask(final EntityNBA EntityNBA) {
//        EntityNBA.setModifiedAt(AppUtils.getCurrentDateTime());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                nbaDatabase.daoAccess().update(EntityNBA);
                return null;
            }
        }.execute();
    }


    public void deleteTask(final EntityNBA EntityNBA) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                nbaDatabase.daoAccess().delete(EntityNBA);
                return null;
            }
        }.execute();
    }


}
