package com.gabrielsantana.letsvote.utils

import java.security.MessageDigest
import java.util.UUID

fun generateNonce(): String {
    val ranNonce: String = UUID.randomUUID().toString()
    val bytes: ByteArray = ranNonce.toByteArray()
    val md: MessageDigest = MessageDigest.getInstance("SHA-256")
    val digest: ByteArray = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}