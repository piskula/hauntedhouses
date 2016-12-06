package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.UserAuthenticateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.UserDTO;
import java.util.List;

/*
 * @author Filip Petrovic (422334)
 */
public interface UserFacade {
    /**
     * Creates new user with given unencrypted password.
     * @param userCreateDTO User to be created.
     * @param unencryptedPassword Password of the new user.
     * @return Id of the created user.
     * @throws IllegalArgumentException if userCreateDTO is null.
     */
    Long createUser(UserCreateDTO userCreateDTO, String unencryptedPassword);

    /**
     * Deletes existing user.
     * @param id Id of a user to be deleted.
     * @throws IllegalArgumentException if id is null.
     */
    void deleteUser(Long id);

    /**
     * Returns user with given id.
     * @param id Id of a user to be returned
     * @return User with given id
     * @throws IllegalArgumentException if id is null.
     */
    UserDTO getUserWithId(Long id);

    /**
     * Returns all users.
     * @return All users
     */
    List<UserDTO> getAllUsers();

    /**
     * Authenticates a user. Return true if the passwords match.
     * @param userAuthenticateDTO User to be authenticated.
     * @return True if authentication succeeds, false otherwise.
     * @throws IllegalArgumentException if userAuthenticateDTO is null.
     */
    boolean authenticate(UserAuthenticateDTO userAuthenticateDTO);
}
