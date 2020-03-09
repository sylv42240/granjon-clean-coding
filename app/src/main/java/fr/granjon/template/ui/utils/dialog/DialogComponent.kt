package fr.granjon.template.ui.utils.dialog

import android.content.Context
import androidx.annotation.StringRes

interface DialogComponent {
    fun displayYesNoDialog(context: Context, @StringRes title: Int, content: Int, onPositiveClicked: () -> Unit)
    fun dismissDialog()
    fun displayFilterDialog(context: Context,onPositiveClicked: (String) -> Unit)
}