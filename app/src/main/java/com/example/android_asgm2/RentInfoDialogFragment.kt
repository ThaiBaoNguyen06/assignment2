package com.example.android_asgm2

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RentInfoDialogFragment(val onSave: (String, String) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.item_rent_info, null, false)
        val inputOwner = view.findViewById<EditText>(R.id.inputOwner)
        val inputDate  = view.findViewById<EditText>(R.id.inputDate)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Enter Rent Info")
            .setView(view)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .create()
        dialog.setOnShowListener {
            val btn = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
            btn.setOnClickListener {
                val owner = inputOwner.text.toString().trim()
                val date  = inputDate.text.toString().trim()
                var ok = true
                if(owner.isEmpty()) {
                    inputOwner.error = "Required"; ok = false
                }
                if(date.isEmpty())  {
                    inputDate.error  = "Required"; ok = false
                }
                if(ok) {
                    onSave(owner, date)
                    dialog.dismiss()
                }
            }
        }
        return dialog
    }
}
