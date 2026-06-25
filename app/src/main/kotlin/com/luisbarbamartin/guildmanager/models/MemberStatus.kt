package com.luisbarbamartin.guildmanager.models

enum class MemberStatus {
    Available, // TODO implement as the default state for idle guild members
    OnQuest, // TODO implement to lock members while they are on an active mission
    Resting, // TODO implement into the Guild Roster to heal from an injury at a later date
    Injured; // TODO implement into a quest failed result at a later date

    val isBusy: Boolean
        get() = this == OnQuest || this == Resting || this == Injured

    val isAvailable: Boolean
        get() = this == Available

    val needsRecovery: Boolean
        get() = this == Resting || this == Injured

    val isOnQuest: Boolean
        get() = this == OnQuest

    val isResting: Boolean
        get() = this == Resting

    val isInjured: Boolean
        get() = this == Injured
}
