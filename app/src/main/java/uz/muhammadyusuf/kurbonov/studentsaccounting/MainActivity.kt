package uz.muhammadyusuf.kurbonov.studentsaccounting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.ui.tooling.preview.Preview
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.StudentsAccountingTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentsAccountingTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        "Hello $name!",
        Modifier,
        Color.Unspecified,
        TextUnit.Inherit,
        null,
        null,
        null,
        TextUnit.Inherit,
        null,
        null,
        TextUnit.Inherit,
        TextOverflow.Clip,
        true,
        Int.MAX_VALUE,
        {},
        AmbientTextStyle.current
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StudentsAccountingTheme {
        Greeting("Android")
    }
}