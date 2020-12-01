package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import uz.muhammadyusuf.kurbonov.studentsaccounting.R
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.animations.CardGoInAnimation
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.bottomCardHeight
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultMargin
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultPadding

@Composable
fun MainScreenLayout(
    title: String = stringResource(id = R.string.app_name),
    onFABClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = title) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFABClick) {
                Icon(asset = Icons.Default.Add, tint = Color.White)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {

            }
        }
    ) {
        Box(modifier = Modifier.padding(defaultPadding())) {
            content()
        }
    }
}


@Composable
fun DetailsLayout(
    modifier: Modifier = Modifier,
    showState: State<Boolean>,
    content: @Composable ColumnScope.() -> Unit = {}
) {

    val currentState = remember { mutableStateOf("start") }
    var draw = true
    val offset = transition(
        definition = CardGoInAnimation.definition,
        initState = currentState.value,
        toState = if (showState.value) "end" else "start"
    ) {
        if (it == "start") draw = false
    }



    if (draw || !showState.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = modifier
                    .padding(defaultPadding(), 0.dp)
                    .fillMaxWidth()
                    .height(bottomCardHeight())
                    .offset(y = bottomCardHeight() * offset[CardGoInAnimation.top])
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp, 16.dp),
                elevation = 25.dp
            ) {
                ScrollableColumn(
                    modifier = Modifier.padding(
                        top = defaultMargin(),
                        start = defaultMargin(),
                        end = defaultMargin(),
                        bottom = 0.dp
                    )
                ) {

                    content()

                }
            }
        }
        currentState.value = if (showState.value) "end" else "start"
    }


}

@Preview(showBackground = true)
@Composable
fun previewMainScreen() {
    MainScreenLayout {
        Text(text = "Hallo")
    }
}