package com.example.weatherapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherapp.data.local.converters.*
import com.example.weatherapp.data.local.dao.CitiesWeatherDao
import com.example.weatherapp.data.local.models.CityLocalProperty
import com.example.weatherapp.data.remote.models.*
import kotlinx.coroutines.*

// Annotates class to be a Room Database with a table (entity) of the CityLocalProperty class
@Database(entities = [CityLocalProperty::class], version = 1, exportSchema = false)
@TypeConverters(
    CloudsConverter::class,
    CoordConverter::class,
    MainConverter::class,
    SysConverter::class,
    ListWeatherConverter::class,
    WindConverter::class)
abstract class CitiesWeatherDatabase : RoomDatabase() {

    abstract fun cityWeatherDao(): CitiesWeatherDao

    //for check there is actually works
    private class CitiesDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    withContext(Dispatchers.IO) {


                        var wordDao = database.cityWeatherDao()

                        // Delete all content here.
                        wordDao.deleteAll()

                        // Add sample words.
                        wordDao.insertCity(
                            CityLocalProperty(
                                0, "1", Clouds(1), 1,
                                Coord(1.0, 1.0), 1,
                                Main(1.0, 1, 1, 1.0, 1.0, 1.0),
                                "1", Sys("1", 1, 1, 1, 1),
                                1, 1, listOf(
                                    Weather(
                                    "ddad", "dasda",0,"dwqdw"
                                )
                                ), Wind(1.0, 1.0)
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CitiesWeatherDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CitiesWeatherDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CitiesWeatherDatabase::class.java,
                        "cities_weather_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}