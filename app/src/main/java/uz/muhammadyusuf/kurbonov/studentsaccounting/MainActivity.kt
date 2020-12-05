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
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import uz.muhammadyusuf.kurbonov.core.viewmodels.add_edit.AddEditViewModel
import uz.muhammadyusuf.kurbonov.core.viewmodels.main.MainViewModel
import uz.muhammadyusuf.kurbonov.repository.models.AccountingItem
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.AddEditScreen
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.DetailsScreen
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
                model.initRepository(AmbientContext.current)

                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MainScreenLayout(onFABClick = {
                            screenState.value = ScreenStates.AddEditScreenState()
                        }) {
                            val listState = model.getAllData().collectAsState()
                            MainScreen(listState = listState, onItemClick = {
                                screenState.value = ScreenStates.DetailsScreenState(it.id)
                            })
                        }

                        val addEditViewModel = viewModel<AddEditViewModel>()
                        addEditViewModel.initRepository(AmbientContext.current)

                        when (screenState.value) {
                            is ScreenStates.AddEditScreenState -> {
                                AddEditScreen {
                                    screenState.value = ScreenStates.MainScreenState
                                }
                            }
                            is ScreenStates.DetailsScreenState -> {
                                val itemState = mutableStateOf<AccountingItem?>(null)

                                lifecycleScope.launch {
                                    val detailsScreenState = screenState.value
                                            as ScreenStates.DetailsScreenState
                                    itemState.value = model.getItem(detailsScreenState.id)
                                }
                                DetailsScreen(item = itemState.value) {
                                    screenState.value = ScreenStates.MainScreenState
                                }
                            }
                        }
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
