package com.example.mathed.Helpers

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment

class MarksDialogFragment(private val marks: Int) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Marks")
            .setMessage("You scored $marks marks!")
            .setPositiveButton("OK") { _, _ ->
                // Dismiss the dialog and finish the activity
                dismiss()
                requireActivity().finish()
            }
            .create()
    }
}