package edu.byu.cs.tweeter.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.util.Pair;

class StatusServiceTest {

    ArrayList<Status> arrayList1 = new ArrayList<>(Arrays.asList(
            new Status("hi", "@allen", "", null, null),
            new Status("hello", "@allen", "fake", null, null)));
    ArrayList<Status> arrayList2 = new ArrayList<>(Collections.singletonList(
            new Status("howdy", "@allen", "", null, null)));

    GetStoryRequest request1 = new GetStoryRequest(new AuthToken("@allen"), "@allen", 2, null);
    GetStoryRequest request2 = new GetStoryRequest(new AuthToken("@allen"), "@allen", 2, "fake");

    StatusService statusService;

    DAOFactory mockFactory;
    StoryDAO mockStoryDAO;
    AuthDAO mockAuthDAO;

    @BeforeEach
    void setUp() {
        mockStoryDAO = Mockito.mock(StoryDAO.class);
        mockAuthDAO = Mockito.mock(AuthDAO.class);
        mockFactory = Mockito.mock(DAOFactory.class);

        Mockito.doReturn(new Pair<>(arrayList1, true))
                .when(mockStoryDAO).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.eq(null));
        Mockito.doReturn(new Pair<>(arrayList2, true))
                .when(mockStoryDAO).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.eq("fake"));

        Mockito.doReturn(mockStoryDAO).when(mockFactory).getStoryDAO();
        Mockito.doReturn(mockAuthDAO).when(mockFactory).getAuthDAO();

        statusService = new StatusService(mockFactory);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getStory_succeeds() {
        Mockito.doReturn(true).when(mockAuthDAO).isAuthorized(Mockito.any());

        GetStoryResponse response1 = statusService.getStory(request1);
        validate(response1, arrayList1);

        GetStoryResponse response2 = statusService.getStory(request2);
        validate(response2, arrayList2);
    }

    private void validate(GetStoryResponse response1, List<Status> expectedArr) {
        assertTrue(response1.isSuccess());
        assertNull(response1.getMessage());
        assertEquals(expectedArr, response1.getItems());
        assertTrue(response1.isHasMorePages());
    }


    @Test
    void getStory_fails() {
        Mockito.doReturn(false).when(mockAuthDAO).isAuthorized(Mockito.any());

        GetStoryResponse response = statusService.getStory(request1);

        assertFalse(response.isSuccess());
        assertEquals(StatusService.NOT_AUTHORIZED, response.getMessage());
        assertNull(response.getItems());
        assertFalse(response.isHasMorePages());
    }
}