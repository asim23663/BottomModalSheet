package com.asim.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.asim.bottomsheet.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var mBinding: FragmentBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
//        bottomSheetBehavior = BottomSheetBehavior.from(binding.coordinatorLayout.rootView)
        //hiding app bar at the start
        hideAppBar(mBinding!!.appBarLayout)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        //inflating layout
        val view = View.inflate(context, R.layout.fragment_bottom_sheet, null) as View
        //setting layout with bottom sheet
        bottomSheet.setContentView(view);
        val bottomSheetLayout = bottomSheet.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheetLayout != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        }

        //setting Peek at the 16:9 ratio key line of its parent.
        bottomSheetBehavior?.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO;

        bottomSheetBehavior?.addBottomSheetCallback(mBottomSheetBehaviorCallback)


        return bottomSheet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        binding.btnEasyApply.setOnClickListener {
            showSnackBar("Easy Apply")
        }

        binding.btnSave.setOnClickListener {
            showSnackBar("Save Job")

        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(binding.coordinatorLayout, msg, Snackbar.LENGTH_LONG)
            .show();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }



    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                showView(mBinding!!.appBarLayout, getActionBarSize());
                hideAppBar(mBinding!!.ivServiceTitle);
                hideAppBar(mBinding!!.viewLine);

            }
            if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                hideAppBar(mBinding!!.appBarLayout);
                showView(mBinding!!.ivServiceTitle, resources.getDimension(R.dimen._20sdp).toInt())
                showView(mBinding!!.viewLine, resources.getDimension(R.dimen._4sdp).toInt());
            }

            if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                dismiss();
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme;
    }

    private fun hideAppBar(view: View) {
        val params = view.layoutParams
        params.height = 0
        view.layoutParams = params
    }

    private fun showView(view: View, size: Int) {
        val params = view.layoutParams
        params.height = size
        view.layoutParams = params
    }

    private fun getActionBarSize(): Int {
        val array = context!!.theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        return array.getDimension(0, 0f).toInt()
    }

}