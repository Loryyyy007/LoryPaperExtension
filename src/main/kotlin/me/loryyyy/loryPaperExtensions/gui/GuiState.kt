package me.loryyyy.loryPaperExtensions.gui

data class GuiState(
    private val _data: MutableMap<String, Any> = mutableMapOf()
) {
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? = _data[key] as? T
    
    fun set(key: String, value: Any) {
        _data[key] = value
    }
    
    fun remove(key: String) = _data.remove(key)
    
    fun copy(): GuiState = GuiState(_data.toMutableMap())
    
    val data: Map<String, Any> get() = this.data.toMap()
}