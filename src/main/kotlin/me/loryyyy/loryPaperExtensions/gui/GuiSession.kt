package me.loryyyy.loryPaperExtensions.gui

import me.loryyyy.loryPaperExtensions.gui.gui.Gui
import java.util.UUID

data class GuiSession(
    val playerId: UUID,
    val gui: Gui,
    val state: Map<String, Any>
)
