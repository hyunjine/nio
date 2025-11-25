package com.hyunjine.nio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunjine.common.ui.theme.Black
import com.hyunjine.common.ui.theme.Grey

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: (NioScreen) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(26.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(NioScreen.entries.filter { it != NioScreen.Home }) {
            HomeComponent(
                type = it,
                onClick = onClick
            )
        }
    }
}

@Composable
fun HomeComponent(
    type: NioScreen,
    onClick: (NioScreen) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Grey)
            .clickable { onClick(type) }
    ) {
        Text(
            text = type.screenName,
            fontSize = 24.sp,
            color = Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}