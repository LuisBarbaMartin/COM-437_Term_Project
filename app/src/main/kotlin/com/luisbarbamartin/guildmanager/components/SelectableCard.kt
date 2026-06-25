package com.luisbarbamartin.guildmanager.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectableCard(
    isSelected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    padding: PaddingValues = PaddingValues(0.dp),
    selectedBorderWidth: Dp = 2.dp,
    unselectedBorderWidth: Dp = 0.dp,
    selectedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unselectedBorderColor: Color = MaterialTheme.colorScheme.outline,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) {
    val borderWidth = if (isSelected) selectedBorderWidth else unselectedBorderWidth
    val borderColor = if (isSelected) selectedBorderColor else unselectedBorderColor

    Card(
        modifier = modifier
            .padding(padding)
            .then(
                if (borderWidth > 0.dp) {
                    Modifier.border(
                        width = borderWidth,
                        color = borderColor,
                        shape = shape
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        content()
    }
}

