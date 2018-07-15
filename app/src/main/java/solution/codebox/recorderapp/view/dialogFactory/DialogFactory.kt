package solution.codebox.recorderapp.view.dialogFactory

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.model.DialogOption

class DialogFactory {
    companion object {
        fun showDialogContainOptionsWithTitle(context: Context, title: String, options: List<DialogOption>) {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.vertical_option_confirm_dialog)
            val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
            tvTitle.text = title
//            tvTitle.setTextColor(ResourceUtils.getColorBy(context, R.color.gray))
            val llDialogContainer = dialog.findViewById<LinearLayout>(R.id.llDialogContainer)
            val iterator = options.iterator()
            while (iterator.hasNext()) {
                val dialogButtonInfo = iterator.next()
                val dialogOptionView = createDialogOptionView(context, dialog, dialogButtonInfo)
                llDialogContainer.addView(dialogOptionView)
            }
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.BOTTOM
            dialog.show()
            dialog.window!!.attributes = lp
        }

        fun showDialogContainOptionsWithoutTitle(context: Context, options: List<DialogOption>) {
            val dialog = Dialog(context)
            dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.vertical_option_confirm_dialog_without_title)
            val llDialogContainer = dialog.findViewById<LinearLayout>(R.id.llDialogContainer)
            val closeDialogOption = DialogOption(context.getString(R.string.DialogOptionClose), object : OptionEventListener<Unit> {
                override fun <T> onClick(e: T) {
                    dialog.dismiss()
                }
            })
            val iterator = options.iterator()
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val margin = context.resources.getDimension(R.dimen.spaceSmall).toInt()
            params.setMargins(margin, margin, margin, margin)
            while (iterator.hasNext()) {
                val dialogButtonInfo = iterator.next()
                val dialogOptionView = createDialogOptionView(context, dialog, dialogButtonInfo)
                llDialogContainer.addView(dialogOptionView, params)
            }
            llDialogContainer.addView(createDialogOptionView(context, dialog, closeDialogOption), params)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = Gravity.CENTER
            dialog.show()
            dialog.window!!.attributes = lp
        }

        private fun createDialogOptionView(context: Context, dialog: Dialog, dialogOption: DialogOption): View {
            val dialogOptionView = createViewByLayoutId(context, R.layout.vertical_dialog_option)
            val tvButtonTitle = dialogOptionView.findViewById<TextView>(R.id.tvButtonTitle)
            tvButtonTitle.text = dialogOption.title
            tvButtonTitle.setOnClickListener { _ ->
                dialogOption.onClickEvent()
                dialog.dismiss()
            }
            return dialogOptionView
        }

        private fun createViewByLayoutId(context: Context, layoutId: Int): View {
            return LayoutInflater.from(context).inflate(layoutId, null)
        }
    }
}