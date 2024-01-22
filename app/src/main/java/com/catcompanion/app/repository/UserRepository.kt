package com.catcompanion.app.repository

import com.catcompanion.app.db.UserDao
import com.catcompanion.app.model.User
import java.util.Date
import java.util.UUID

class UserRepository(private val userDao: UserDao) {

    suspend fun getCurrentUser(): User {
        // Get All Users
        val users = userDao.getAll()

        // Evaluate
        if (users.isNotEmpty()) {
            // Ensure data
            val updatedUser = initializeUser(users.first())

            // Evaluate a need for an update
            if (updatedUser.second) {
                // Update it on the database
                userDao.update(updatedUser.first)
            }

            // Return the first one
            return updatedUser.first
        } else {
            // Create a new user here
            val newUser = initializeUser(null)

            // Evaluate a need for an insert
            if (newUser.second) {
                // Insert it on the database
                userDao.insert(newUser.first)
            }

            // Return it
            return newUser.first
        }
    }

    private fun initializeUser(user: User?): Pair<User, Boolean> {
        // Init default info
        val defaultUser = User(0, Date(), UUID.randomUUID().toString())

        // Flag to track if the user info has changed
        var userChanged = false

        // Eval
        return if (user == null) {
            // Return default and indicate that user info has changed
            Pair(defaultUser, true)
        } else {
            // Make sure info is updated
            if (user.catApiUserId == null) {
                // Update user info and set the flag to true
                user.catApiUserId = defaultUser.catApiUserId
                userChanged = true
            }

            // Return the user (either the original or updated) and the flag
            Pair(user, userChanged)
        }
    }
}