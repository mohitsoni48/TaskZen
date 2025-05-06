package com.droidcon.taskzen.models

import androidx.compose.ui.graphics.Color
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.design
import com.droidcon.taskzen.generated.resources.grocery
import com.droidcon.taskzen.generated.resources.social
import com.droidcon.taskzen.generated.resources.sport
import com.droidcon.taskzen.generated.resources.university
import com.droidcon.taskzen.generated.resources.work
import org.jetbrains.compose.resources.DrawableResource

enum class TaskCategory(val color: Color, val res: DrawableResource) {
    GROCERY(Color(0xFFCCFF80), Res.drawable.grocery),
    WORK(Color(0xFFFF9680), Res.drawable.work),
    SPORT(Color(0xFF80FFFF), Res.drawable.sport),
    DESIGN(Color(0xFF80FFD9), Res.drawable.design),
    UNIVERSITY(Color(0xFF809CFF), Res.drawable.university),
    SOCIAL(Color(0xFFFF80EB), Res.drawable.social),
}