package com.srl.labelspinnerlibrary

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ListPopupWindow
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap
import com.srl.labelspinnerlibrary.ViewUtils.isVisibleForUser

class LabelSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    var mode: Int = -1
) :
    RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val DEF_STYLE_RES = com.google.android.material.R.styleable.TextInputLayout

    val TAG=this.javaClass.simpleName

    var mOnItemSelectedListener: OnItemSelectedListener? = null
    var mAdapter: BaseAdapter? = null

    /** Context used to inflate the popup window or dialog.  */
    private var mPopupContext: Context

    /**
     * Use a dialog window for selecting spinner options.
     */
    val MODE_DIALOG = 0

    /**
     * Use a dropdown anchored to the Spinner for selecting spinner options.
     */
    val MODE_DROPDOWN = 1

    var selectedPossition=-1

    /**
     * Use the theme-supplied value to select the dropdown mode.
     */
    private val MODE_THEME = -1

    /** Temporary holder for setAdapter() calls from the super constructor.  */
    private var mTempAdapter: BaseAdapter? = null


    private var mPopup: SpinnerPopup? = null
    var mDropDownWidth = 0

    private var mGravity = 0
    private var mDisableChildrenWhenDisabled = false

    lateinit var textInputEditText:TextInputEditText
    lateinit var textInputLayout:TextInputLayout

    init {
        createView(context, attrs, defStyleAttr, defStyleRes)
//        inflate(context, R.layout.lable_spinner_layout, this)
//        textInputLayout=text_input_layout
//        textInputEditText=text_input_edit_text
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LabelSpinner,
            defStyleAttr,
            defStyleRes
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveAttributeDataForStyleable(
                context,
                R.styleable.LabelSpinner,
                attrs,
                typedArray,
                defStyleAttr,
                defStyleRes
            )
        }
        val popupThemeResId = typedArray.getResourceId(R.styleable.LabelSpinner_popupTheme, 0)
        mPopupContext = if (popupThemeResId != 0) {
            ContextThemeWrapper(context, popupThemeResId)
        } else {
            context
        }
        if (mode == MODE_THEME) {
            mode = typedArray.getInt(R.styleable.LabelSpinner_spinnerMode, MODE_DIALOG)
        }
        when (mode) {
            MODE_DIALOG -> {
                mPopup =DialogPopup()
                mPopup?.setPromptText(typedArray.getString(R.styleable.LabelSpinner_prompt))
            }
            MODE_DROPDOWN -> {
                val popup = DropdownPopup(mPopupContext, attrs, defStyleAttr, defStyleRes)
                val popTypedArray = mPopupContext.obtainStyledAttributes(
                    attrs,
                    R.styleable.LabelSpinner,
                    defStyleAttr,
                    defStyleRes
                )
                mDropDownWidth = popTypedArray.getLayoutDimension(
                    R.styleable.LabelSpinner_dropDownWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (popTypedArray.hasValueOrEmpty(R.styleable.LabelSpinner_dropDownSelector)) {
                        popup.setListSelector(popTypedArray.getDrawable(R.styleable.LabelSpinner_dropDownSelector))
                    }
                } else {
                    val drawable =
                        popTypedArray.getDrawable(R.styleable.LabelSpinner_dropDownSelector)
                    if (drawable != null) {
                        popup.setListSelector(drawable)
                    }
                }
                popup.setBackgroundDrawable(popTypedArray.getDrawable(R.styleable.LabelSpinner_popupBackground))
                popup.setPromptText(typedArray.getString(R.styleable.LabelSpinner_prompt))
                popTypedArray.recycle()
                mPopup = popup
            }
        }
        mGravity = typedArray.getInt(R.styleable.LabelSpinner_gravity, Gravity.CENTER)
        mDisableChildrenWhenDisabled =
            typedArray.getBoolean(R.styleable.LabelSpinner_disableChildrenWhenDisabled, false)

        textInputLayout.hint = typedArray.getString(R.styleable.LabelSpinner_label)

        typedArray.recycle()
        if (mTempAdapter != null) {
            setAdapter(mTempAdapter!!)
            mTempAdapter = null
        }

        textInputEditText.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if(event?.action==MotionEvent.ACTION_UP)
                    mPopup?.show(textDirection, textAlignment)
                return true
            }
        })
    }

    fun setAdapter(adapter: BaseAdapter) {
        // The super constructor may call setAdapter before we're prepared.
        // Postpone doing anything until we've finished construction.
        if (mPopup == null) {
            mTempAdapter = adapter
            return
        }
        this.mAdapter = adapter
        mPopup?.setAdapter(adapter)
    }


    private fun setSelection(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedPossition=position
        textInputEditText.setText(parent?.adapter?.getItem(position).toString())
    }

    fun createView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    {
        var typedArray = context.obtainStyledAttributes(
                attrs,
        R.styleable.LabelSpinner,
        defStyleAttr,
        defStyleRes
        )
        val labelStyle=typedArray.getResourceId(R.styleable.LabelSpinner_labelStyle, 0)
        if(labelStyle>0)
        {
            typedArray = context.theme.obtainStyledAttributes(labelStyle, DEF_STYLE_RES)
        }
        textInputLayout= TextInputLayout(context)
        textInputLayout
//        textInputLayout= TextInputLayout(context)
        val params= LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        addView(textInputLayout,params)
        val inParam=LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        textInputEditText=TextInputEditText(context)
        textInputLayout.addView(textInputEditText,inParam)
        typedArray.recycle()
    }


    /**
     * Implements some sort of popup selection interface for selecting a spinner option.
     * Allows for different spinner modes.
     */
    private interface SpinnerPopup {
        fun setAdapter(adapter: ListAdapter?)

        /**
         * Show the popup
         */
        fun show(textDirection: Int, textAlignment: Int)

        /**
         * Dismiss the popup
         */
        fun dismiss()

        /**
         * @return true if the popup is showing, false otherwise.
         */
        fun isShowing(): Boolean

        /**
         * Set hint text to be displayed to the user. This should provide
         * a description of the choice being made.
         * @param hintText Hint text to set.
         */
        fun setPromptText(hintText: CharSequence?)

        fun getHintText(): CharSequence?

        fun setBackgroundDrawable(bg: Drawable?)
        fun setVerticalOffset(px: Int)
        fun setHorizontalOffset(px: Int)
        fun getBackground(): Drawable?
        fun getVerticalOffset(): Int
        fun getHorizontalOffset(): Int
    }

    inner class DropdownPopup(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0, defStyleRes: Int = 0
    ) : ListPopupWindow(context, attrs, defStyleAttr, defStyleRes), SpinnerPopup {
        private var mHintText: CharSequence? = null
        private var mAdapter: ListAdapter? = null

        init {
            anchorView = this@LabelSpinner
            isModal = true
            promptPosition = android.widget.ListPopupWindow.POSITION_PROMPT_ABOVE
            setOnItemClickListener { parent, view, position, id ->
                setSelection(parent, view, position, id)
                mOnItemSelectedListener?.onItemSelected(parent, view, position, id)
                dismiss()
            }
        }

        override fun setAdapter(adapter: ListAdapter?) {
            super.setAdapter(adapter)
            mAdapter = adapter
        }

        override fun show(textDirection: Int, textAlignment: Int) {
            val wasShowing = isShowing
            width = this@LabelSpinner.measuredWidth
            inputMethodMode = INPUT_METHOD_NOT_NEEDED
            super.show()
            val listView = listView
            listView!!.choiceMode = ListView.CHOICE_MODE_SINGLE
            listView.textDirection = textDirection
            listView.textAlignment = textAlignment
            if (wasShowing) {
                return
                // Skip setting up the layout/dismiss listener below. If we were previously
                // showing it will still stick around.
            }
            // Make sure we hide if our anchor goes away.
            // TODO: This might be appropriate to push all the way down to PopupWindow,
            // but it may have other side effects to investigate first. (Text editing handles, etc.)
            val vto = viewTreeObserver
            if (vto != null) {
                val layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                    if (!this@LabelSpinner.isVisibleForUser()) {
                        dismiss()
                    } else {
                        width = this@LabelSpinner.measuredWidth
                        // Use super.show here to update; we don't want to move the selected
                        // position or adjust other things that would be reset otherwise.
                        super@DropdownPopup.show()
                    }
                }
                vto.addOnGlobalLayoutListener(layoutListener)

                setOnDismissListener {
                    val vtoDismiss = viewTreeObserver
                    vtoDismiss?.removeOnGlobalLayoutListener(layoutListener)
                }
            }
        }

        override fun setPromptText(hintText: CharSequence?) {
            mHintText = hintText
        }

        override fun getHintText(): CharSequence? {
            return mHintText
        }
    }

    inner class DialogPopup:SpinnerPopup,DialogInterface.OnClickListener{
        private var mPopupAlert: AlertDialog? = null
        private var mListAdapter: ListAdapter? = null
        private var mPrompt: CharSequence? = null

        override fun setAdapter(adapter: ListAdapter?) {
            mListAdapter=adapter
        }

        override fun show(textDirection: Int, textAlignment: Int) {
            if(mListAdapter==null)
            {
                return
            }
            val builder=MaterialAlertDialogBuilder(mPopupContext)
            if(mPrompt!=null)
            {
                builder.setTitle(mPrompt)
            }
            builder.setSingleChoiceItems(mListAdapter,selectedPossition,this)
            mPopupAlert=builder.create()
            val listView=mPopupAlert?.listView
            listView?.textDirection=textDirection
            listView?.textAlignment=textAlignment
            mPopupAlert?.show()
            postDelayed({
                mPopupAlert?.dismiss()
            },3*1000)
        }

        override fun dismiss() {
            mPopupAlert?.dismiss()
            mPopupAlert = null
        }

        override fun isShowing(): Boolean {
            return mPopupAlert?.isShowing?:false
        }

        override fun setPromptText(hintText: CharSequence?) {
            mPrompt=hintText
        }

        override fun getHintText(): CharSequence? {
            return mPrompt
        }

        override fun setBackgroundDrawable(bg: Drawable?) {
            Log.e(TAG, "Cannot set popup background for MODE_DIALOG, ignoring")
        }

        override fun setVerticalOffset(px: Int) {
            Log.e(TAG, "Cannot set vertical offset for MODE_DIALOG, ignoring")
        }

        override fun setHorizontalOffset(px: Int) {
            Log.e(TAG, "Cannot set horizontal offset for MODE_DIALOG, ignoring")
        }

        override fun getBackground(): Drawable? {
            return null
        }

        override fun getVerticalOffset(): Int {
            return 0
        }

        override fun getHorizontalOffset(): Int {
            return 0
        }

        override fun onClick(dialog: DialogInterface?, which: Int) {
            setSelection(mPopupAlert?.listView, mPopupAlert?.listView?.selectedView, which, mListAdapter!!.getItemId(which))
            mOnItemSelectedListener?.onItemSelected(mPopupAlert?.listView, mPopupAlert?.listView?.selectedView, which, mListAdapter!!.getItemId(which))
            dismiss()
        }

    }
}