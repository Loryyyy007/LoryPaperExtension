package me.loryyyy.loryPaperExtensions.gui.component

import me.loryyyy.loryPaperExtensions.gui.Gui
import me.loryyyy.loryPaperExtensions.gui.GuiState
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class MovableButton(
    override val slot: Int,
    private val itemKey: String, // chiave univoca per questo tipo di bottone movibile
    private val validSlots: Set<Int>, // slot dove questo bottone può essere posizionato
    private val itemProvider: (itemKey: String, GuiState) -> ItemStack?, // come renderizzare il bottone
    private val onRightClick: ((itemKey: String, Player, Gui) -> Unit)? = null, // per editare il bottone
    private val onMove: ((itemKey: String, from: Int, to: Int, Player, Gui) -> Unit)? = null // movimento completato
) : GuiComponent {
    
    override fun render(state: GuiState): ItemStack? {
        // Controlla se questo slot contiene il nostro itemKey
        val slotContent = state.get<String>("slot-$slot")
        return if (slotContent == itemKey) {
            itemProvider(itemKey, state)
        } else {
            null
        }
    }
    
    override fun onClick(player: Player, clickType: ClickType, gui: Gui): Boolean {
        when (clickType) {
            ClickType.RIGHT -> {
                // Edit del bottone - solo se questo slot contiene il nostro itemKey
                val slotContent = gui.state.get<String>("slot-$slot")
                if (slotContent == itemKey) {
                    onRightClick?.invoke(itemKey, player, gui)
                    return true
                }
            }
            
            ClickType.LEFT -> {
                return handleMovement(player, gui)
            }
            
            else -> return false
        }
        return false
    }
    
    private fun handleMovement(player: Player, gui: Gui): Boolean {
        val slotContent = gui.state.get<String>("slot-$slot")
        val heldItem = gui.state.get<Pair<String, Int>>("held-movable") // (itemKey, fromSlot)
        
        when {
            // Prendere un bottone movibile
            slotContent != null && heldItem == null -> {
                gui.state.set("held-movable", slotContent to slot)
                gui.state.remove("slot-$slot")
                gui.refresh()
                return true
            }
            
            // Posare un bottone movibile
            heldItem != null -> {
                val (heldKey, fromSlot) = heldItem
                
                // Verifica se questo slot è valido per il bottone che stiamo tenendo
                // Dobbiamo trovare il MovableButton corrispondente per controllare i validSlots
                val isValidSlot = isSlotValidForItem(heldKey, slot, gui)
                
                if (isValidSlot) {
                    // Se c'è già un bottone in questo slot, scambia
                    if (slotContent != null) {
                        gui.state.set("slot-$fromSlot", slotContent)
                    }
                    
                    gui.state.set("slot-$slot", heldKey)
                    gui.state.remove("held-movable")
                    onMove?.invoke(heldKey, fromSlot, slot, player, gui)
                    gui.refresh()
                    return true
                } else {
                    player.sendMessage("§cNon puoi posizionare questo bottone qui!")
                }
                return true
            }
            
            // Click su slot vuoto senza tenere niente
            else -> return false
        }
    }
    
    private fun isSlotValidForItem(itemKey: String, targetSlot: Int, gui: Gui): Boolean {
        // Trova tutti i MovableButton con questo itemKey e controlla se targetSlot è nei loro validSlots
        gui.getComponents().values.filterIsInstance<MovableButton>()
            .firstOrNull { it.itemKey == itemKey }
            ?.let { return targetSlot in it.validSlots }
        return false
    }
}