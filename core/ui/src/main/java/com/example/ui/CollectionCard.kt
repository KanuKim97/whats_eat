package com.example.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.EatCard
import com.example.designsystem.component.EatIconButton
import com.example.designsystem.component.EatImageLoader
import com.example.designsystem.icons.EatIcons
import com.example.designsystem.theme.Typography

@Composable
fun CollectionCard(
    placeName: String,
    placeImgUrl: String,
    modifier: Modifier = Modifier
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotateIconState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f,
        label = "rotateIconState"
    )

    EatCard(
        onClick = { expandedState = !expandedState },
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ),
        content = {
            Column(
                modifier = modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        Text(
                            text = placeName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Typography.headlineSmall
                        )
                        EatIconButton(
                            onClick = { expandedState = !expandedState },
                            modifier = modifier.rotate(rotateIconState),
                            icon = EatIcons.extendedMoreBordered
                        )
                    }
                )
                if (expandedState) {
                    EatImageLoader(
                        imageModel = placeImgUrl,
                        modifier = modifier.fillMaxWidth().height(200.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCollectionCard() {
    CollectionCard(
        modifier = Modifier,
        placeName = "00상회",
        placeImgUrl = ""
    )
}