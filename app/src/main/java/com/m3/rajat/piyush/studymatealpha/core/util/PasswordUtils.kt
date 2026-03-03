package com.m3.rajat.piyush.studymatealpha.core.util

import java.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Password hashing utility using PBKDF2WithHmacSHA256.
 * Passwords are stored as "base64salt:base64hash".
 */
object PasswordUtils {

    private const val ITERATIONS = 65536
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = 16
    private const val ALGORITHM = "PBKDF2WithHmacSHA256"

    /**
     * Hashes a plaintext password with a random salt.
     * @return A string in "salt:hash" format (both Base64-encoded).
     */
    fun hashPassword(password: String): String {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)

        val hash = pbkdf2(password, salt)

        val saltB64 = Base64.getEncoder().encodeToString(salt)
        val hashB64 = Base64.getEncoder().encodeToString(hash)
        return "$saltB64:$hashB64"
    }

    /**
     * Verifies a plaintext password against a stored "salt:hash" string.
     * @return true if the password matches.
     */
    fun verifyPassword(password: String, stored: String): Boolean {
        val parts = stored.split(":")
        if (parts.size != 2) return false

        return try {
            val salt = Base64.getDecoder().decode(parts[0])
            val expectedHash = Base64.getDecoder().decode(parts[1])
            val actualHash = pbkdf2(password, salt)
            expectedHash.contentEquals(actualHash)
        } catch (e: Exception) {
            false
        }
    }

    private fun pbkdf2(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance(ALGORITHM)
        return factory.generateSecret(spec).encoded
    }
}
