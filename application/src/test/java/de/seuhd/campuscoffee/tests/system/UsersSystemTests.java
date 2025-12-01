package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;
import de.seuhd.campuscoffee.api.dtos.UserDto;
import java.util.List;
import static de.seuhd.campuscoffee.tests.SystemTestUtils.Requests.userRequests;
import static org.junit.Assert.assertEquals;





public class UsersSystemTests extends AbstractSysTest {

    //TODO: Uncomment once user endpoint is implemented

    @Test
    void createUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toDomain(userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).getFirst());

        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }


    @Test
    void getAllUsers() {
        List<User> created = TestFixtures.createUsers(userService);
        List<UserDto> response = userRequests.retrieveAll();
        assertEquals(created.size(), response.size());
    }

    @Test
    void getUserById() {
        List<User> createdUsers = TestFixtures.createUsers(userService);
        User expected = createdUsers.getFirst();
        UserDto response = userRequests.retrieveById(expected.id());
        User returned = userDtoMapper.toDomain(response);
        assertEqualsIgnoringIdAndTimestamps(returned, expected);
    }

    //TODO: Add at least two additional tests for user operations

}