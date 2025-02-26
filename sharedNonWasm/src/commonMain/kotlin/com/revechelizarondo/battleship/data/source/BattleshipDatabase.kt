package com.revechelizarondo.battleship.data.source

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.revechelizarondo.battleship.data.source.dao.PreferencesDao
import com.revechelizarondo.battleship.data.util.Converters
import com.revechelizarondo.battleship.domain.models.Preference
import com.revechelizarondo.battleship.getPlatform
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div


expect object BattleshipDatabaseCtor : RoomDatabaseConstructor<BattleshipDatabase>

@Database(
    entities = [Preference::class],
    version = 1
)
@ConstructedBy(BattleshipDatabaseCtor::class)
@TypeConverters(Converters::class)
abstract class BattleshipDatabase : RoomDatabase() {
    abstract fun preferencesDao(): PreferencesDao

    companion object {
        private const val APP_NAME = "Battleship"
        const val DATABASE_NAME = "battleship_database.db"

        fun getDatabaseLocation(): String = getPath().toString()

        private fun getPath(): Path =
            when {
                getPlatform().name.startsWith("Windows") ->
                    Path(System.getenv("APPDATA") ?: ".") / APP_NAME / DATABASE_NAME

                getPlatform().name.startsWith("Mac") ->
                    Path(
                        System.getenv("HOME") ?: "."
                    ) / "Library/Application Support" / APP_NAME / DATABASE_NAME

                getPlatform().name.startsWith("Linux") ->
                    Path(System.getenv("HOME") ?: ".") / ".local/share" / APP_NAME / DATABASE_NAME

                else ->
                    Path(System.getenv("HOME") ?: ".") / ".$APP_NAME" / DATABASE_NAME
            }
    }
}