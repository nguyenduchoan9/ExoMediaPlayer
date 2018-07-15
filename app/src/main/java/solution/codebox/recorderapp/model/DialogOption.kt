package solution.codebox.recorderapp.model

import solution.codebox.recorderapp.view.dialogFactory.OptionEventListener

class DialogOption(val title: String = "Option label", private val optionEventListener: OptionEventListener<Unit>) {
    fun onClickEvent() = optionEventListener?.onClick(null)
}