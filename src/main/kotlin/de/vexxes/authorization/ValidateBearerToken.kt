package de.vexxes.authorization

class ValidateBearerToken {

    fun validateAdmin(token: String): Boolean {
        return token.equals("Bearer Admin")
    }

    fun validateAll(token: String): Boolean {
        return token.equals("Bearer Admin") || token.equals("Bearer User")
    }
}