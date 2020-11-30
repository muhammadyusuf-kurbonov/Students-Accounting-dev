package uz.muhammadyusuf.kurbonov.studentsaccounting.ui.animations

import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween

class CardGoInAnimation {
    companion object {
        val top = FloatPropKey("Y Offset of card")

        val definition = transitionDefinition<String> {
            state("start") {
                this[top] = 1f
            }
            state("end") {
                this[top] = 0f
            }

            transition("start" to "end") {
                top using tween(durationMillis = 2000)
            }

            transition("end" to "start") {
                top using tween(durationMillis = 2000)
            }
        }


    }
}