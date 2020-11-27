package uz.muhammadyusuf.kurbonov.defaultresources

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun LoadingPage(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(64.dp).align(Alignment.Center)) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(
                text = stringResource(id = R.string.loading_label), modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ), style = MaterialTheme.typography.subtitle1
            )
        }
    }
}


@Composable
fun EmptyPage(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.empty_message),
            modifier = Modifier.padding(64.dp).align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5.copy(Color.DarkGray),
        )
    }
}