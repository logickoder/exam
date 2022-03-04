package dev.logickoder.exams.ui.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.logickoder.exams.ui.theme.CardCaption

@Composable
fun Table(modifier: Modifier = Modifier, data: Map<String, Any>) {
    val data = data.toList()
    Column(
        modifier = modifier
    ) {
        data.forEachIndexed { index, pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    pair.first,
                    style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    color = CardCaption,
                )
                Text(
                    pair.second.toString(),
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 11.sp, fontWeight = FontWeight.Bold
                    ),
                    color = Color.DarkGray,
                )
            }
            if (index != data.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}