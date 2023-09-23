package com.example.mydiary.utils.extensions


fun String?.orDefault(defValue: String = "") = this ?: defValue


fun <T> T?.orDefaultAny(defValue: T): T = this ?: defValue


fun Int?.orDefault(defValue: Int = 0) = this ?: defValue


fun Long?.orDefault(defValue: Long = 0) = this ?: defValue


fun Float?.orDefault(defValue: Float = 0f) = this ?: defValue


fun Double?.orDefault(defValue: Double = 0.0) = this ?: defValue


fun Boolean?.orFalse() = this ?: false


fun Boolean?.orTrue() = this ?: true



fun Long?.isNullOrEmpty(): Boolean = (this == null || this == 0L)


fun Int?.isNullOrZero(): Boolean = (this == null || this == 0)


fun Long?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()


fun Int?.isNotNullOrEmpty(): Boolean = !this.isNullOrZero()



fun CharSequence?.isNotNullOrEmpty() = !this.isNullOrEmpty()


fun List<Any>?.isNotNullOrEmpty() = !this.isNullOrEmpty()



fun Any?.isNotNull(): Boolean = (this != null)