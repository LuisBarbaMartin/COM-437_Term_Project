package com.luisbarbamartin.guildmanager.utils

fun <T> toggledSelection(currentSelection: T?, selectedValue: T): T? {
    return if (currentSelection == selectedValue) null else selectedValue
}

