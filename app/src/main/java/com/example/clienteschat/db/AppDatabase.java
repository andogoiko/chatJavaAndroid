package com.example.clienteschat.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Mensaje.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MensajeDAO mensajeDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mensajitosandrois").allowMainThreadQueries().build();
        }

        return INSTANCE;

    }

}
