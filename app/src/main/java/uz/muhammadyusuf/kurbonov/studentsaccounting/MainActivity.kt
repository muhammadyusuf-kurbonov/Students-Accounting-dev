package uz.muhammadyusuf.kurbonov.studentsaccounting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit.AddEditViewModel
import uz.muhammadyusuf.kurbonov.core.viewmodels.main.MainViewModel
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.AddEditScreen
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.MainScreen
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.StudentsAccountingTheme
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.components.MainScreenLayout
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states.ScreenStates

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private val screenState: MutableState<ScreenStates> =
        mutableStateOf(ScreenStates.MainScreenState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentsAccountingTheme {
                val model = viewModel<MainViewModel>()
                model.initRepository(ContextAmbient.current)

                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MainScreenLayout(onFABClick = {
                            screenState.value = ScreenStates.AddEditScreenState()
                        }) {
                            val listState = model.getAllData().collectAsState()
                            MainScreen(listState = listState)
                        }

                        val addEditViewModel = viewModel<AddEditViewModel>()
                        addEditViewModel.initRepository(ContextAmbient.current)

                        if (screenState.value is ScreenStates.AddEditScreenState)
                            AddEditScreen(
                                showState = mutableStateOf(true),
                                viewModel = addEditViewModel
                            )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (screenState.value != ScreenStates.MainScreenState)
            screenState.value = ScreenStates.MainScreenState
        else
            super.onBackPressed()
    }
}
