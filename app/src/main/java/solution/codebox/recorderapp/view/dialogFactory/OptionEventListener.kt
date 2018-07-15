package solution.codebox.recorderapp.view.dialogFactory

interface OptionEventListener<T> {
    fun <T> onClick(e: T)
}