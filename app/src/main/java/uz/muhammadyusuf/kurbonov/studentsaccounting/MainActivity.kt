package uz.muhammadyusuf.kurbonov.studentsaccounting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.muhammadyusuf.kurbonov.core.viewmodels.main.MainViewModel
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.MainScreen
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.StudentsAccountingTheme
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.MainScreenLayout
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states.ScreenStates

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentsAccountingTheme {
                val model = viewModel<MainViewModel>()
                model.initRepository(ContextAmbient.current)

                val screenState =
                    remember { mutableStateOf<ScreenStates>(ScreenStates.MainScreenState) }

                Surface(color = MaterialTheme.colors.background) {
                    when (screenState.value) {
                        ScreenStates.MainScreenState -> {
                            MainScreenLayout(onFABClick = {
                                screenState.value = ScreenStates.AddEditScreenState()
                            }) {
                                val listState = model.getAllData().collectAsState()
                                MainScreen(listState = listState)
                            }
                        }
                        is ScreenStates.AddEditScreenState -> {
                            Text(text = "Add Screen")
                        }
                    }
                }
            }
        }
    }
}
