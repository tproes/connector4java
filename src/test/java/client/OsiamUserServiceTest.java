package client;

import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osiam.resources.scim.User;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OsiamUserServiceTest {
    final private static String uuidString = "94bbe688-4b1e-4e4e-80e7-e5ba5c4d6db4";
    private UUID existingUserUUID;
    private String access_token;

    @Mock
    private WebResource userServiceMock;

    @Mock
    private WebResource.Builder webResourceBuilderMock;

    OsiamUserService service;

    @Before
    public void setUp() {
        service = new OsiamUserService(userServiceMock);
    }

    @Test
    public void existing_user_is_returned() {
        given_existing_user_UUID();
        given_valid_access_token();
        when_uuid_is_looked_up();
        then_returned_user_has_uuid(existingUserUUID);
    }

    private void given_valid_access_token() {
        access_token = "valid access token";
    }

    private void given_existing_user_UUID() {
        this.existingUserUUID = UUID.fromString(uuidString);
    }

    private void when_uuid_is_looked_up() {
        User result = new User.Builder().setId(uuidString).build();
        when(userServiceMock.header(anyString(), anyObject())).thenReturn(webResourceBuilderMock);
        when(userServiceMock.path(anyString())).thenReturn(userServiceMock);
        when(webResourceBuilderMock.get(User.class)).thenReturn(result);
    }

    private void then_returned_user_has_uuid(UUID uuid) {
        User result = service.getUserByUUID(uuid, access_token);
        verify(userServiceMock).path(uuidString);
        assertEquals(uuidString, result.getId());
    }
}