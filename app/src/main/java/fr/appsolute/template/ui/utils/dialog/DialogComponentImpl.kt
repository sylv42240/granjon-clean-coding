package fr.appsolute.template.ui.utils.dialog

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import fr.appsolute.template.R

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

    override fun dismissDialog() {
        materialDialog?.dismiss()
    }
}