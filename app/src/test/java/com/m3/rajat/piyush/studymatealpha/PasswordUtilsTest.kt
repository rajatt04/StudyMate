package com.m3.rajat.piyush.studymatealpha

import com.m3.rajat.piyush.studymatealpha.database.PasswordUtils
import org.junit.Assert.*
import org.junit.Test

class PasswordUtilsTest {

    @Test
    fun hashPassword_producesNonEmptyResult() {
        val hash = PasswordUtils.hashPassword("myPassword123")
        assertTrue(hash.isNotEmpty())
    }

    @Test
    fun hashPassword_containsSaltSeparator() {
        val hash = PasswordUtils.hashPassword("test")
        assertTrue("Hash should contain a colon separator", hash.contains(":"))
    }

    @Test
    fun hashPassword_producesUniqueSalts() {
        val hash1 = PasswordUtils.hashPassword("samePassword")
        val hash2 = PasswordUtils.hashPassword("samePassword")
        assertNotEquals("Two hashes of the same password should differ (different salts)", hash1, hash2)
    }

    @Test
    fun verifyPassword_correctPassword_returnsTrue() {
        val password = "CorrectPassword!@#"
        val hash = PasswordUtils.hashPassword(password)
        assertTrue(PasswordUtils.verifyPassword(password, hash))
    }

    @Test
    fun verifyPassword_wrongPassword_returnsFalse() {
        val hash = PasswordUtils.hashPassword("rightPassword")
        assertFalse(PasswordUtils.verifyPassword("wrongPassword", hash))
    }

    @Test
    fun verifyPassword_emptyPassword_returnsFalse() {
        val hash = PasswordUtils.hashPassword("nonEmpty")
        assertFalse(PasswordUtils.verifyPassword("", hash))
    }

    @Test
    fun verifyPassword_emptyHash_returnsFalse() {
        assertFalse(PasswordUtils.verifyPassword("test", ""))
    }

    @Test
    fun verifyPassword_malformedHash_returnsFalse() {
        assertFalse(PasswordUtils.verifyPassword("test", "notavalidhash"))
    }

    @Test
    fun hashPassword_specialCharacters() {
        val password = "p@\$\$w0rd!#%^&*()"
        val hash = PasswordUtils.hashPassword(password)
        assertTrue(PasswordUtils.verifyPassword(password, hash))
    }

    @Test
    fun hashPassword_unicodeCharacters() {
        val password = "пароль密码パスワード"
        val hash = PasswordUtils.hashPassword(password)
        assertTrue(PasswordUtils.verifyPassword(password, hash))
    }

    @Test
    fun hashPassword_longPassword() {
        val password = "a".repeat(1000)
        val hash = PasswordUtils.hashPassword(password)
        assertTrue(PasswordUtils.verifyPassword(password, hash))
    }
}
