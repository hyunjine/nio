package com.hyunjine.common.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.hyunjine.common.R
import com.hyunjine.common.ui.theme.NioTheme
import com.hyunjine.common.ui.theme.black700
import com.hyunjine.common.ui.theme.black900
import com.hyunjine.common.ui.theme.typography.Roboto
import com.hyunjine.common.ui.theme.typography.typography
import com.hyunjine.common.util.getLoremIpsum
import com.hyunjine.common.util.spToDp

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
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {
        val (leftBox, titleText, rightBox) = createRefs()
        TouchBox(
            modifier = Modifier
                .size(48.dp)
                .constrainAs(leftBox) {
                    start.linkTo(parent.start, margin = 2.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            onClick = onLeftIconClick
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(leftIcon),
                contentDescription = null,
                tint = black900,
            )
        }
        TouchBox(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 80.spToDp)
                .constrainAs(rightBox) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            onClick = onRightTextClick
        ) {
            Text(
                text = rightText,
                style = typography.titleMedium,
                color = black700,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 4.dp, end = 18.dp)
            )
        }
        Text(
            text = title,
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = black900,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 80.spToDp)
                .constrainAs(titleText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun TouchBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
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
        AppbarState("title", "actionaction"),
        AppbarState(getLoremIpsum(), "action"),
        AppbarState("", "")
    )
}