package com.example.aksarasundakeyboard

import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class AksaraSundaKeyboardService : InputMethodService() {
    private var page = 0
    private lateinit var root: LinearLayout
    private lateinit var content: LinearLayout
    private lateinit var pageLabel: TextView

    private val pages = listOf(
        listOf(
            listOf("ᮊ", "ᮌ", "ᮍ", "ᮎ", "ᮏ", "ᮑ"),
            listOf("ᮒ", "ᮓ", "ᮔ", "ᮕ", "ᮘ", "ᮙ"),
            listOf("ᮚ", "ᮛ", "ᮜ", "ᮝ", "ᮞ", "ᮠ"),
            listOf("ᮖ", "ᮗ", "ᮟ", "ᮐ", "ᮮ", "ᮯ")
        ),
        listOf(
            listOf("ᮃ", "ᮄ", "ᮅ", "ᮆ", "ᮇ", "ᮈ", "ᮉ"),
            listOf("ᮤ", "ᮥ", "ᮦ", "ᮧ", "ᮨ", "ᮩ"),
            listOf("᮪", "ᮁ", "ᮀ", "ᮂ", "ᮡ", "ᮢ", "ᮣ"),
            listOf("᮳", "᮴", "᮵", "᮶", "᮷", "᮸", "᮹")
        ),
        listOf(
            listOf("᮰", "᮱", "᮲", "᮳", "᮴", "᮵"),
            listOf("᮶", "᮷", "᮸", "᮹", "ᮺ", "ᮻ"),
            listOf("ᮼ", "ᮽ", "ᮾ", "ᮿ", "᳀", "᳁"),
            listOf("᮪", "ᮁ", "ᮀ", "ᮂ", "ᮤ", "ᮥ")
        )
    )
    private val pageNames = listOf("Ngalagena", "Swara & Rarangken", "Angka & Tanda")

    override fun onCreateInputView(): View {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(5), dp(5), dp(5), dp(5))
            setBackgroundColor(Color.rgb(235, 239, 236))
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        pageLabel = TextView(this).apply {
            text = pageNames[page]
            textSize = 14f
            setTextColor(Color.rgb(30, 65, 48))
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER_VERTICAL
        }
        header.addView(pageLabel, LinearLayout.LayoutParams(0, dp(34), 1f))
        header.addView(actionButton("⇄") { switchPage() }, LinearLayout.LayoutParams(dp(48), dp(36)))
        header.addView(actionButton("⌨") { requestShowSelf(0) }, LinearLayout.LayoutParams(dp(48), dp(36)))
        root.addView(header)

        val scroll = ScrollView(this).apply { isFillViewport = true }
        content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))

        renderPage()
        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        bottom.addView(actionButton("ABC") { switchToSystemKeyboard() }, weightParams(1f))
        bottom.addView(actionButton("Spasi") { commit(" ") }, weightParams(1.35f))
        bottom.addView(actionButton("⌫") { deleteOne() }, weightParams(1f))
        bottom.addView(actionButton("↵") { enterKey() }, weightParams(1f))
        root.addView(bottom)
        return root
    }

    private fun renderPage() {
        if (!::content.isInitialized) return
        content.removeAllViews()
        pageLabel.text = pageNames[page]
        pages[page].forEach { row ->
            val line = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
            }
            row.forEach { glyph ->
                val button = glyphButton(glyph) { commit(glyph) }
                line.addView(button, LinearLayout.LayoutParams(0, dp(48), 1f).apply {
                    setMargins(dp(2), dp(2), dp(2), dp(2))
                })
            }
            content.addView(line)
        }
    }

    private fun switchPage() {
        page = (page + 1) % pages.size
        renderPage()
    }

    private fun glyphButton(label: String, click: () -> Unit) = Button(this).apply {
        text = label
        textSize = 23f
        isAllCaps = false
        setTextColor(Color.rgb(28, 43, 34))
        typeface = Typeface.DEFAULT
        background = rounded(Color.WHITE, 8)
        setPadding(0, 0, 0, 0)
        setOnClickListener { click() }
    }

    private fun actionButton(label: String, click: () -> Unit) = Button(this).apply {
        text = label
        textSize = 13f
        isAllCaps = false
        setTextColor(Color.rgb(255, 255, 255))
        background = rounded(Color.rgb(36, 91, 69), 8)
        setPadding(dp(3), 0, dp(3), 0)
        setOnClickListener { click() }
    }

    private fun rounded(color: Int, radius: Int) = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radius).toFloat()
    }

    private fun weightParams(weight: Float) =
        LinearLayout.LayoutParams(0, dp(44), weight).apply {
            setMargins(dp(2), dp(3), dp(2), dp(1))
        }

    private fun commit(text: String) {
        currentInputConnection?.commitText(text, 1)
    }

    private fun deleteOne() {
        val ic: InputConnection = currentInputConnection ?: return
        if (!ic.deleteSurroundingText(1, 0)) {
            ic.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_DEL))
            ic.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_DEL))
        }
    }

    private fun enterKey() {
        val ic = currentInputConnection ?: return
        ic.commitText("\n", 1)
    }

    private fun switchToSystemKeyboard() {
        try {
            switchInputMethod(null)
        } catch (_: Exception) {
            requestHideSelf(0)
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
}
