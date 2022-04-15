package com.fyznur.itunessearchcase.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
internal val localeDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

@SuppressLint("SimpleDateFormat")
internal var parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")