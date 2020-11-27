package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import uz.muhammadyusuf.kurbonov.studentsaccounting.R
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

@Preview(showBackground = true)
@Composable
fun previewMainScreen() {
    MainScreenLayout {
        Text(text = "Hallo")
    }
}