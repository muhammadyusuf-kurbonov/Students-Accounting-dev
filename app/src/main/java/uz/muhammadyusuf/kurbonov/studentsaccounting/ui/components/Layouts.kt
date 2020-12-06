package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.muhammadyusuf.kurbonov.studentsaccounting.R
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.animations.CardGoInAnimation
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.bottomCardHeight
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultMargin
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.defaultPadding
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states.DetailsCardState

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
                Icon(imageVector = Icons.Default.Add, tint = Color.White)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {

            }
        },
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Box(modifier = Modifier.padding(defaultPadding()).fillMaxSize()) {
            content()
        }
    }
}


@Composable
fun DetailsLayout(
    modifier: Modifier = Modifier,
    showState: MutableState<DetailsCardState>,
    onDismissRequest: () -> Unit = {},
    onOpen: () -> Unit = {},
    onClosed: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {}
) {
    val toState = when (showState.value) {
        is DetailsCardState.Closing -> DetailsCardState.Closed
        is DetailsCardState.Opening -> DetailsCardState.Opened
        is DetailsCardState.Opened,
        DetailsCardState.Closed -> showState.value
    }
    val fromState = when (showState.value) {
        is DetailsCardState.Opened, DetailsCardState.Closed
        -> showState.value
        is DetailsCardState.Opening -> DetailsCardState.Closed
        is DetailsCardState.Closing -> DetailsCardState.Opened
    }
    val offset = transition(
        definition = CardGoInAnimation.definition,
        initState = fromState,
        toState = toState
    ) {
        if (it == DetailsCardState.Closed) {
            showState.value = DetailsCardState.Closed
            onClosed()
        } else if (it == DetailsCardState.Opened) {
            showState.value = DetailsCardState.Opened
            onOpen()
        }
    }

    if (showState.value !is DetailsCardState.Closed) {
        Box(
            modifier = Modifier.fillMaxSize()
                .clickable(onClick = {
                    onDismissRequest()
                })
        ) {
            Card(
                modifier = modifier
                    .padding(defaultPadding(), 0.dp)
                    .fillMaxWidth()
                    .height(bottomCardHeight())
                    .offset(y = bottomCardHeight() * offset[CardGoInAnimation.offset])
                    .align(Alignment.BottomCenter)
                    .clickable(onClick = {}),
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
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreenLayout {
        Text(text = "Hallo")
    }
}

@Composable
fun Padding(paddingValues: PaddingValues, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.padding(paddingValues)) {
        content()
    }
}