package com.enhanceit.android.minimalistcontentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import java.lang.Integer.parseInt
import java.util.*


private const val TAG = "MiniContentProvider"
class MiniContentProvider: ContentProvider() {

    var mData: Array<String> = emptyArray()
    private val sUriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {

        mData = context?.resources?.getStringArray(R.array.words) ?: emptyArray()
        initUriMatching()
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        var id = -1
        when(sUriMatcher.match(p0)){
            0 -> {id = Contract.ALL_ITEMS
                if (p2 != null){
                    id = p3!![0].toInt()
                }
            }
            1 -> id = p0.lastPathSegment?.let { parseInt(it) }!!
            UriMatcher.NO_MATCH -> {id = -1
                Log.d(TAG, "query: No match for this uri in scheme")
            }
            else -> {Log.d(TAG, "INVALID URI - URI NOT RECOGNIZED.");
                id = -1
            }
        }
        Log.d(TAG, "query: $id");
        return populateCursor(id);
    }

    private fun populateCursor(id: Int): Cursor? {
        val cursor = MatrixCursor(arrayOf(Contract.PATH))
        if (id == Contract.ALL_ITEMS){
            for(item in mData) {
                cursor.addRow(arrayOf(item))
            }
        }
        else if(id >= 0){
            val word = mData[id]
            cursor.addRow(arrayOf(word))
        }
        return cursor
    }

    override fun getType(p0: Uri): String? {
        return when (sUriMatcher.match(p0)){
            0 -> Contract.MULTIPLE_RECORD_MIME_TYPE
            1 -> Contract.SINGLE_RECORD_MIME_TYPE
            else -> null
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        Log.e(TAG, "Not implemented: update uri: $p0");
        return p0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        Log.e(TAG, "Not implemented: update uri: $p0");
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        Log.e(TAG, "Not implemented: update uri: $p0");
        return 0
    }

    private fun initUriMatching(){
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.PATH + "/#", 1);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.PATH, 0);
    }

}