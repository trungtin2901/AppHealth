package com.catjunior.healthbackend.services;

import com.catjunior.healthbackend.dto.EditPasswordDto;
import com.catjunior.healthbackend.dto.EditProfileDto;
import com.catjunior.healthbackend.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface IUserService {

    /**
     * Find a user by its token
     *
     * @param token the token
     * @return the user found
     * @throws NotFoundException No user found
     */
    User getUserFromToken(String token);

    /**
     * Find a user by its id
     *
     * @param userId the user id
     * @return the user found
     * @throws EntityNotFoundException No user found
     */
    public User getUserById(Long userId);

    /**
     * Create a new user.
     *
     * @param user the user to create
     * @return the ID of the created user
     */
    Long create(User user);

    /**
     * Search users by text.
     *
     * @param searchText the search text
     * @return the found user, or null if not found
     */
    List<User> search(String token, String searchText);

    /**
     * Find a user by username.
     *
     * @param username the username
     * @return the found user, or null if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Edit the password of a user.
     *
     * @param token the token associated with the user
     * @param dto the dto with the updated password
     * @return the ID of the user
     */
    Long editPassword(String token, EditPasswordDto dto);

    /**
     * Edit the profile of a user.
     *
     * @param token the token associated with the user
     * @param dto the dto with the updated profile
     * @return the ID of the user
     */
    Long editProfile(String token, EditProfileDto dto);

    /**
     * Delete a user based on the provided token.
     *
     * @param token the token associated with the user to delete
     */
    void delete(String token);

    /**
     * Upload the profile picture of the current user
     *
     * @param token the token of the current user
     * @param file the file to upload as the profile picture
     */
    void uploadProfilePicture(String token, MultipartFile file) throws IOException;

    /**
     * Get profile picture of user of id
     *
     * @param id the user id
     */
    Map<String, Object> getProfilePicture(String id);
}
