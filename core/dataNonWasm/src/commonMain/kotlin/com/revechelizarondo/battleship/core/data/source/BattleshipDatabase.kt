package com.revechelizarondo.battleship.core.data.source

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.revechelizarondo.battleship.core.data.source.dao.PreferencesDao
import com.revechelizarondo.battleship.core.data.util.Converters
import com.revechelizarondo.battleship.core.domain.models.Preference
import com.revechelizarondo.battleship.core.domain.platform.Platforms
import com.revechelizarondo.battleship.core.domain.platform.getPlatform
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
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
            when (getPlatform().name) {
                Platforms.Windows ->
                    Path(System.getenv("APPDATA") ?: ".") / APP_NAME / DATABASE_NAME

                Platforms.macOS ->
                    Path(
                        System.getenv("HOME") ?: "."
                    ) / "Library/Application Support" / APP_NAME / DATABASE_NAME

                Platforms.Linux ->
                    Path(System.getenv("HOME") ?: ".") / ".local/share" / APP_NAME / DATABASE_NAME

                else ->
                    Path(System.getenv("HOME") ?: ".") / ".${APP_NAME.lowercase()}" / DATABASE_NAME
            }
    }
}