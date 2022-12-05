package com.example.clienteschat.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MensajeDAO {

    @Query("SELECT * FROM mensaje")
    List<Mensaje> getAllMensajes();

    @Insert
    void insertMensaje(Mensaje... mensajes);

    @Delete
    void delete(Mensaje mensaje);
}
