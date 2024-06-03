package com.qwe7002.telegram_rc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.qwe7002.telegram_rc.static_class.CONST


class ScannerActivity : Activity() {
    private var mCodeScanner: CodeScanner? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_scanner)
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        mCodeScanner = CodeScanner(this, scannerView)
        mCodeScanner!!.formats = object : ArrayList<BarcodeFormat?>() {
            init {
                add(BarcodeFormat.QR_CODE)
            }
        }
        mCodeScanner!!.decodeCallback = DecodeCallback { result: Result ->
            runOnUiThread {
                val TAG = "activity_scanner"
                Log.d(TAG, "format: " + result.barcodeFormat + " content: " + result.text)
                if (!jsonValidate(result.text)) {
                    Toast.makeText(this, "The QR code is not legal", Toast.LENGTH_SHORT).show()
                    mCodeScanner!!.startPreview()
                    return@runOnUiThread
                }
                val intent = Intent().putExtra("config_json", result.text)
                setResult(CONST.RESULT_CONFIG_JSON, intent)
                finish()
            }
        }
        scannerView.setOnClickListener { view: View? -> mCodeScanner!!.startPreview() }
    }


    public override fun onResume() {
        super.onResume()
        mCodeScanner!!.startPreview()
    }

    public override fun onPause() {
        mCodeScanner!!.releaseResources()
        super.onPause()
    }

    fun jsonValidate(jsonStr: String?): Boolean {
        val jsonElement: JsonElement?
        try {
            jsonElement = JsonParser.parseString(jsonStr)
        } catch (e: Exception) {
            return false
        }
        return jsonElement != null
    }
}
