package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.animations

import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import uz.muhammadyusuf.kurbonov.studentsaccounting.ui.states.DetailsCardState

class CardGoInAnimation {
    companion object {
        val offset = FloatPropKey("Y Offset of card")

        val definition = transitionDefinition<DetailsCardState> {
            state(DetailsCardState.Opened) {
                this[offset] = 0f
            }
            state(DetailsCardState.Closed) {
                this[offset] = 1f
            }

            transition(DetailsCardState.Opened to DetailsCardState.Closed) {
                offset using tween(durationMillis = 2000)
            }

            transition(DetailsCardState.Closed to DetailsCardState.Opened) {
                offset using tween(durationMillis = 2000)
            }
        }


    }
}