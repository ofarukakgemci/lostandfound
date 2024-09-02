package com.ofa.lostandfound.service;

/**
 * Interface for UserService.
 * <p>
 * This interface defines the contract for the UserService class.
 * It provides methods for getting the current user and user name.
 */
public interface UserService {

    /**
     * Retrieves the user name for the given user ID.
     *
     * @param userId The ID of the user.
     * @return The user name.
     */
    String getUserName(String userId);
}