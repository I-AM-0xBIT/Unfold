package com.example.myapplication.Databases

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.Daos.StoryDao
import com.example.myapplication.Daos.StoryElementDao
import com.example.myapplication.Daos.TemplateDao
import com.example.myapplication.Daos.UserDao
import com.example.myapplication.Models.Story
import com.example.myapplication.Models.StoryElement
import com.example.myapplication.Models.Template
import com.example.myapplication.Models.User

@Database(entities = [Story::class, StoryElement::class, Template::class, User::class], version = 1)
abstract class CollageDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao?
    abstract fun storyElementDao(): StoryElementDao?
    abstract fun templateDao(): TemplateDao
    abstract fun userDao(): UserDao?
    private class PopulateDbAsyncTask private constructor(collageDatabase: CollageDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        private val templateDao: TemplateDao
        protected override fun doInBackground(vararg voids: Void): Void? {
            templateDao.insert(Template(1, 1, ""))
            templateDao.insert(Template(2, 2, ""))
            templateDao.insert(Template(3, 3, ""))
            return null
        }

        init {
            templateDao = collageDatabase!!.templateDao()
        }
    }

    companion object {
        private var instance: CollageDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CollageDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        CollageDatabase::class.java, "collage")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
}