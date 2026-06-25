package com.luisbarbamartin.guildmanager.managers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luisbarbamartin.guildmanager.models.ActiveQuest
import com.luisbarbamartin.guildmanager.models.GameState
import com.luisbarbamartin.guildmanager.models.GuildMember
import com.luisbarbamartin.guildmanager.models.InventoryItem
import com.luisbarbamartin.guildmanager.models.ItemType
import com.luisbarbamartin.guildmanager.models.JobClass
import com.luisbarbamartin.guildmanager.models.MainHandGrip
import com.luisbarbamartin.guildmanager.models.MemberModifier
import com.luisbarbamartin.guildmanager.models.MemberStats
import com.luisbarbamartin.guildmanager.models.MemberStatus
import com.luisbarbamartin.guildmanager.models.MemberTitle
import com.luisbarbamartin.guildmanager.models.Race
import com.luisbarbamartin.guildmanager.models.WeaponHand
import com.luisbarbamartin.guildmanager.models.ZodiacSign
import java.io.File

class SaveManager(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    private val gson = Gson()
    private val legacySaveFile = File(context.filesDir, "save_game.json")

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE game_state (
                id INTEGER PRIMARY KEY CHECK (id = 1),
                guild_name TEXT NOT NULL,
                gold INTEGER NOT NULL,
                fame INTEGER NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE guild_members (
                id INTEGER NOT NULL,
                list_type TEXT NOT NULL,
                sort_order INTEGER NOT NULL,
                name TEXT NOT NULL,
                job_class TEXT NOT NULL,
                race TEXT NOT NULL,
                level INTEGER NOT NULL,
                experience INTEGER NOT NULL,
                zodiac_sign TEXT NOT NULL,
                status TEXT NOT NULL,
                title TEXT NOT NULL,
                image_res_id INTEGER NOT NULL,
                strength INTEGER NOT NULL,
                armor INTEGER NOT NULL,
                intellect INTEGER NOT NULL,
                agility INTEGER NOT NULL,
                wisdom INTEGER NOT NULL,
                stamina INTEGER NOT NULL,
                modifiers_json TEXT NOT NULL,
                PRIMARY KEY (id, list_type)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE saved_items (
                row_id INTEGER PRIMARY KEY AUTOINCREMENT,
                location TEXT NOT NULL,
                owner_member_id INTEGER,
                sort_order INTEGER NOT NULL,
                item_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                item_type TEXT NOT NULL,
                description TEXT NOT NULL,
                class_restrictions TEXT NOT NULL,
                value INTEGER NOT NULL,
                rarity TEXT NOT NULL,
                image_res_id INTEGER NOT NULL,
                strength INTEGER,
                armor INTEGER,
                intellect INTEGER,
                agility INTEGER,
                wisdom INTEGER,
                stamina INTEGER,
                weapon_hand TEXT,
                main_hand_grip TEXT
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE active_quests (
                id INTEGER PRIMARY KEY,
                quest_id INTEGER NOT NULL,
                started_at_millis INTEGER NOT NULL,
                ends_at_millis INTEGER NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE active_quest_members (
                active_quest_id INTEGER NOT NULL,
                member_id INTEGER NOT NULL,
                sort_order INTEGER NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE activity_log (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                sort_order INTEGER NOT NULL,
                message TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS activity_log")
        db.execSQL("DROP TABLE IF EXISTS active_quest_members")
        db.execSQL("DROP TABLE IF EXISTS active_quests")
        db.execSQL("DROP TABLE IF EXISTS saved_items")
        db.execSQL("DROP TABLE IF EXISTS guild_members")
        db.execSQL("DROP TABLE IF EXISTS game_state")
        onCreate(db)
    }

    fun saveGame(state: GameState) {
        val db = writableDatabase
        var isTransactionStarted = false

        try {
            db.beginTransaction()
            isTransactionStarted = true
            clearSaveTables(db)

            db.insertOrThrow(
                "game_state",
                null,
                ContentValues().apply {
                    put("id", 1)
                    put("guild_name", state.guildName)
                    put("gold", state.gold)
                    put("fame", state.fame)
                }
            )

            state.guildMembers.forEachIndexed { index, member ->
                db.insertOrThrow("guild_members", null, memberValues(member, MEMBER_LIST_GUILD, index))
                member.equippedItems.forEachIndexed { itemIndex, item ->
                    db.insertOrThrow(
                        "saved_items",
                        null,
                        itemValues(item, ITEM_LOCATION_EQUIPPED, member.id, itemIndex)
                    )
                }
            }

            state.recruits.forEachIndexed { index, member ->
                db.insertOrThrow("guild_members", null, memberValues(member, MEMBER_LIST_RECRUIT, index))
                member.equippedItems.forEachIndexed { itemIndex, item ->
                    db.insertOrThrow(
                        "saved_items",
                        null,
                        itemValues(item, ITEM_LOCATION_EQUIPPED, member.id, itemIndex)
                    )
                }
            }

            state.inventory.forEachIndexed { index, item ->
                db.insertOrThrow(
                    "saved_items",
                    null,
                    itemValues(item, ITEM_LOCATION_INVENTORY, null, index)
                )
            }

            state.activeQuests.forEach { activeQuest ->
                db.insertOrThrow(
                    "active_quests",
                    null,
                    ContentValues().apply {
                        put("id", activeQuest.id)
                        put("quest_id", activeQuest.questId)
                        put("started_at_millis", activeQuest.startedAtMillis)
                        put("ends_at_millis", activeQuest.endsAtMillis)
                    }
                )

                activeQuest.memberIds.forEachIndexed { index, memberId ->
                    db.insertOrThrow(
                        "active_quest_members",
                        null,
                        ContentValues().apply {
                            put("active_quest_id", activeQuest.id)
                            put("member_id", memberId)
                            put("sort_order", index)
                        }
                    )
                }
            }

            state.activityLog.forEachIndexed { index, message ->
                db.insertOrThrow(
                    "activity_log",
                    null,
                    ContentValues().apply {
                        put("sort_order", index)
                        put("message", message)
                    }
                )
            }

            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (isTransactionStarted) {
                db.endTransaction()
            }
        }
    }

    fun loadGame(): GameState? {
        val db = readableDatabase

        return try {
            val stateCursor = db.query(
                "game_state",
                null,
                "id = ?",
                arrayOf("1"),
                null,
                null,
                null
            )

            stateCursor.use { cursor ->
                if (!cursor.moveToFirst()) {
                    return loadLegacyJsonSave()
                }

                val equippedItemsByMemberId = loadEquippedItemsByMemberId(db)

                GameState(
                    guildName = cursor.string("guild_name"),
                    guildMembers = loadMembers(db, MEMBER_LIST_GUILD, equippedItemsByMemberId),
                    recruits = loadMembers(db, MEMBER_LIST_RECRUIT, equippedItemsByMemberId),
                    inventory = loadInventory(db),
                    activeQuests = loadActiveQuests(db),
                    gold = cursor.int("gold"),
                    fame = cursor.int("fame"),
                    activityLog = loadActivityLog(db)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun loadLegacyJsonSave(): GameState? {
        return try {
            if (!legacySaveFile.exists()) {
                return null
            }

            val state = gson.fromJson(legacySaveFile.readText(), GameState::class.java)
            saveGame(state)
            state
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun clearSaveTables(db: SQLiteDatabase) {
        db.delete("activity_log", null, null)
        db.delete("active_quest_members", null, null)
        db.delete("active_quests", null, null)
        db.delete("saved_items", null, null)
        db.delete("guild_members", null, null)
        db.delete("game_state", null, null)
    }

    private fun memberValues(member: GuildMember, listType: String, sortOrder: Int): ContentValues {
        return ContentValues().apply {
            put("id", member.id)
            put("list_type", listType)
            put("sort_order", sortOrder)
            put("name", member.name)
            put("job_class", member.jobClass.name)
            put("race", member.race.name)
            put("level", member.level)
            put("experience", member.experience)
            put("zodiac_sign", member.zodiacSign.name)
            put("status", member.status.name)
            put("title", member.title.name)
            put("image_res_id", member.imageResId)
            putStats(member.baseStats)
            put("modifiers_json", gson.toJson(member.modifiers))
        }
    }

    private fun itemValues(
        item: InventoryItem,
        location: String,
        ownerMemberId: Int?,
        sortOrder: Int
    ): ContentValues {
        return ContentValues().apply {
            put("location", location)
            if (ownerMemberId != null) {
                put("owner_member_id", ownerMemberId)
            } else {
                putNull("owner_member_id")
            }
            put("sort_order", sortOrder)
            put("item_id", item.id)
            put("name", item.name)
            put("item_type", item.itemType.name)
            put("description", item.description)
            put("class_restrictions", item.classRestrictions.joinToString(",") { jobClass -> jobClass.name })
            put("value", item.value)
            put("rarity", item.rarity)
            put("image_res_id", item.imageResId)
            putStats(item.stats)
            putNullableEnum("weapon_hand", item.weaponHand)
            putNullableEnum("main_hand_grip", item.mainHandGrip)
        }
    }

    private fun ContentValues.putStats(stats: MemberStats?) {
        put("strength", stats?.strength)
        put("armor", stats?.armor)
        put("intellect", stats?.intellect)
        put("agility", stats?.agility)
        put("wisdom", stats?.wisdom)
        put("stamina", stats?.stamina)
    }

    private fun ContentValues.putNullableEnum(key: String, value: Enum<*>?) {
        if (value == null) {
            putNull(key)
        } else {
            put(key, value.name)
        }
    }

    private fun loadMembers(
        db: SQLiteDatabase,
        listType: String,
        equippedItemsByMemberId: Map<Int, List<InventoryItem>>
    ): List<GuildMember> {
        val cursor = db.query(
            "guild_members",
            null,
            "list_type = ?",
            arrayOf(listType),
            null,
            null,
            "sort_order ASC"
        )

        return cursor.use {
            buildList {
                while (it.moveToNext()) {
                    val memberId = it.int("id")
                    add(
                        GuildMember(
                            id = memberId,
                            name = it.string("name"),
                            jobClass = enumValue(it.string("job_class"), JobClass.None),
                            race = enumValue(it.string("race"), Race.Human),
                            level = it.int("level"),
                            experience = it.int("experience"),
                            zodiacSign = enumValue(it.string("zodiac_sign"), ZodiacSign.Aries),
                            status = enumValue(it.string("status"), MemberStatus.Available),
                            title = enumValue(it.string("title"), MemberTitle.None),
                            imageResId = it.int("image_res_id"),
                            baseStats = it.memberStats(),
                            equippedItems = equippedItemsByMemberId[memberId].orEmpty(),
                            modifiers = modifiersFromJson(it.string("modifiers_json"))
                        )
                    )
                }
            }
        }
    }

    private fun loadInventory(db: SQLiteDatabase): List<InventoryItem> {
        val cursor = db.query(
            "saved_items",
            null,
            "location = ?",
            arrayOf(ITEM_LOCATION_INVENTORY),
            null,
            null,
            "sort_order ASC"
        )

        return cursor.use {
            buildList {
                while (it.moveToNext()) {
                    add(it.inventoryItem())
                }
            }
        }
    }

    private fun loadEquippedItemsByMemberId(db: SQLiteDatabase): Map<Int, List<InventoryItem>> {
        val cursor = db.query(
            "saved_items",
            null,
            "location = ?",
            arrayOf(ITEM_LOCATION_EQUIPPED),
            null,
            null,
            "owner_member_id ASC, sort_order ASC"
        )

        return cursor.use {
            val itemsByMemberId = mutableMapOf<Int, MutableList<InventoryItem>>()

            while (it.moveToNext()) {
                val ownerMemberId = it.int("owner_member_id")
                itemsByMemberId.getOrPut(ownerMemberId) { mutableListOf() }.add(it.inventoryItem())
            }

            itemsByMemberId
        }
    }

    private fun loadActiveQuests(db: SQLiteDatabase): List<ActiveQuest> {
        val memberIdsByQuestId = loadActiveQuestMemberIds(db)
        val cursor = db.query(
            "active_quests",
            null,
            null,
            null,
            null,
            null,
            "started_at_millis ASC"
        )

        return cursor.use {
            buildList {
                while (it.moveToNext()) {
                    val activeQuestId = it.long("id")
                    add(
                        ActiveQuest(
                            id = activeQuestId,
                            questId = it.int("quest_id"),
                            memberIds = memberIdsByQuestId[activeQuestId].orEmpty(),
                            startedAtMillis = it.long("started_at_millis"),
                            endsAtMillis = it.long("ends_at_millis")
                        )
                    )
                }
            }
        }
    }

    private fun loadActiveQuestMemberIds(db: SQLiteDatabase): Map<Long, List<Int>> {
        val cursor = db.query(
            "active_quest_members",
            null,
            null,
            null,
            null,
            null,
            "active_quest_id ASC, sort_order ASC"
        )

        return cursor.use {
            val memberIdsByQuestId = mutableMapOf<Long, MutableList<Int>>()

            while (it.moveToNext()) {
                val activeQuestId = it.long("active_quest_id")
                memberIdsByQuestId.getOrPut(activeQuestId) { mutableListOf() }.add(it.int("member_id"))
            }

            memberIdsByQuestId
        }
    }

    private fun loadActivityLog(db: SQLiteDatabase): List<String> {
        val cursor = db.query(
            "activity_log",
            null,
            null,
            null,
            null,
            null,
            "sort_order ASC"
        )

        return cursor.use {
            buildList {
                while (it.moveToNext()) {
                    add(it.string("message"))
                }
            }
        }
    }

    private fun Cursor.inventoryItem(): InventoryItem {
        return InventoryItem(
            id = int("item_id"),
            name = string("name"),
            itemType = enumValue(string("item_type"), ItemType.Special),
            description = string("description"),
            classRestrictions = jobClassesFromString(string("class_restrictions")),
            value = int("value"),
            rarity = string("rarity"),
            imageResId = int("image_res_id"),
            stats = nullableMemberStats(),
            weaponHand = nullableEnum(stringOrNull("weapon_hand"), WeaponHand.MainHand),
            mainHandGrip = nullableEnum(stringOrNull("main_hand_grip"), MainHandGrip.OneHanded)
        )
    }

    private fun Cursor.memberStats(): MemberStats {
        return MemberStats(
            strength = int("strength"),
            armor = int("armor"),
            intellect = int("intellect"),
            agility = int("agility"),
            wisdom = int("wisdom"),
            stamina = int("stamina")
        )
    }

    private fun Cursor.nullableMemberStats(): MemberStats? {
        val columnIndex = getColumnIndexOrThrow("strength")

        if (isNull(columnIndex)) {
            return null
        }

        return memberStats()
    }

    private fun modifiersFromJson(json: String): List<MemberModifier> {
        val type = object : TypeToken<List<MemberModifier>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    private fun jobClassesFromString(value: String): List<JobClass> {
        return value.split(",")
            .filter { name -> name.isNotBlank() }
            .map { name -> enumValue(name, JobClass.None) }
    }

    private inline fun <reified T : Enum<T>> enumValue(value: String, default: T): T {
        return enumValues<T>().firstOrNull { enum -> enum.name == value } ?: default
    }

    private inline fun <reified T : Enum<T>> nullableEnum(value: String?, default: T): T? {
        if (value == null) {
            return null
        }

        return enumValue(value, default)
    }

    private fun Cursor.string(columnName: String): String {
        return getString(getColumnIndexOrThrow(columnName))
    }

    private fun Cursor.stringOrNull(columnName: String): String? {
        val columnIndex = getColumnIndexOrThrow(columnName)
        return if (isNull(columnIndex)) null else getString(columnIndex)
    }

    private fun Cursor.int(columnName: String): Int {
        return getInt(getColumnIndexOrThrow(columnName))
    }

    private fun Cursor.long(columnName: String): Long {
        return getLong(getColumnIndexOrThrow(columnName))
    }

    companion object {
        private const val DATABASE_NAME = "guild_manager.db"
        private const val DATABASE_VERSION = 1

        private const val MEMBER_LIST_GUILD = "guild"
        private const val MEMBER_LIST_RECRUIT = "recruit"

        private const val ITEM_LOCATION_INVENTORY = "inventory"
        private const val ITEM_LOCATION_EQUIPPED = "equipped"
    }
}
