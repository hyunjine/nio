package com.hyunjine.common.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunjine.common.R
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black700
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.typography.Roboto
import com.hyunjine.common.ui.theme.typography.typography
import com.hyunjine.common.util.getLoremIpsum

@Composable
fun Appbar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes
    leftIcon: Int = R.drawable.icon_24_arrow_left,
    rightText: String = "",
    onLeftIconClick: () -> Unit = {},
    onRightTextClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .then(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onLeftIconClick),
            painter = painterResource(leftIcon),
            contentDescription = null,
            tint = black900,
        )
        Text(
            text = title,
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = black900,
            modifier = Modifier
                .padding(end = 4.dp)
                .weight(1F),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .padding(end = 6.dp)
                .clickable(onClick = onRightTextClick),
            text = rightText,
            style = typography.titleMedium,
            color = black700,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppbarPreview(
    @PreviewParameter(AppbarProvider::class) state: AppbarState
) {
    NioTheme {
        Appbar(
            title = state.title,
            rightText = state.actionText
        )
    }
}

data class AppbarState(val title: String, val actionText: String)

class AppbarProvider : PreviewParameterProvider<AppbarState> {
    override val values = sequenceOf(
        AppbarState("title", "action"),
        AppbarState(getLoremIpsum(), "action"),
        AppbarState("", "")
    )
}