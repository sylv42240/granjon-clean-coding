package fr.granjon.template.ui.utils.dialog

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import fr.granjon.template.R

class DialogComponentImpl: DialogComponent {

    private var materialDialog: MaterialDialog? = null

    override fun displayYesNoDialog(
        context: Context,
        title: Int,
        content: Int,
        onPositiveClicked: () -> Unit
    ) {
        dismissDialog()
        materialDialog = MaterialDialog(context).show {
            title(title)
            message(content)
            cancelable(true)
            positiveButton(R.string.text_button_dialog_yes) {
                dismissDialog()
                onPositiveClicked()
            }
            negativeButton(R.string.text_button_dialog_no) {
                dismissDialog()
            }
        }
    }

    override fun displayFilterDialog(
        context: Context,
        onPositiveClicked: (String) -> Unit
    ){
        dismissDialog()
        materialDialog = MaterialDialog(context).show {
            title(R.string.filter_dialog_title)
            listItemsSingleChoice(R.array.filter_array) { dialog, index, text ->
                onPositiveClicked(text)
            }
        }
    }

    override fun dismissDialog() {
        materialDialog?.dismiss()
    }
}