package com.example.myapplication.database.car

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entite User
 * On peut customiser le nom de la table avec @Entity(tableName = "user_table") et
 * également le nom des colonnes avec @ColumnInfo(name = "first_name")
 */
@Entity
data class Notes(val titre: String, val contenu: String){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}