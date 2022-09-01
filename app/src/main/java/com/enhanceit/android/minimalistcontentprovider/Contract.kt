package com.enhanceit.android.minimalistcontentprovider

import android.net.Uri

object Contract {
    const val SCHEME = "content://"
    const val AUTHORITY = "com.android.example.wordcontentprovider.provider"
    const val PATH = "words"
    val content_uri: Uri = Uri.parse("${SCHEME}${AUTHORITY}/${PATH}")

    const val ALL_ITEMS = -2
    const val WORD_ID = "id"

    //MIME Types
    const val SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.com.example.provider.words"
    const val MULTIPLE_RECORD_MIME_TYPE = "vnd.android.cursor.dir/vnd.com.example.provider.words"


}