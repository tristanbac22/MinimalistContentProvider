package com.enhanceit.android.minimalistcontentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var textResponseView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textResponseView = findViewById(R.id.tv_response)
    }

    fun onClickDisplayEntries(view: View){

        val queryUri = Contract.content_uri.toString()
        val projection = arrayOf<String?>(Contract.PATH)
        var selectionClause: String?
        var selectionArgs = arrayOf<String?>()
        val sortOrder = null


            Log.d (TAG, "Yay, I was clicked!");

            when (view.id) {
                R.id.btn_list_all_words->{
                    selectionClause = null;
                    selectionArgs = emptyArray<String?>();
                }
                R.id.btn_list_first_word -> {
                    selectionClause = Contract.WORD_ID + " = ?";
                    selectionArgs = arrayOf<String?>("0")
                }
                else -> {
                    selectionClause = null;
                    selectionArgs = emptyArray<String?>();
                }
            }

        var cursor: Cursor? = contentResolver.query(Uri.parse(queryUri),
            projection, selectionClause, selectionArgs, sortOrder)


        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(projection[0])
                do {
                    val word = cursor.getString(columnIndex)
                    textResponseView.append(
                        """
                    $word
                    
                    """.trimIndent()
                    )
                } while (cursor.moveToNext())
            } else {
                d(TAG, "onClickDisplayEntries " + "No data returned.")
                textResponseView.append(
                    """
                No data returned.
                
                """.trimIndent()
                )
            }
            cursor.close()
        } else {
            d(TAG, "onClickDisplayEntries " + "Cursor is null.")
            textResponseView.append(
                """
            Cursor is null.
            
            """.trimIndent()
            )
        }
    }
}