package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.prakhar.dvdstore.Dvd;
import com.prakhar.dvdstore.DvdNotFoundException;
import com.prakhar.dvdstore.DvdRepository;
import com.prakhar.dvdstore.DvdRepositoryStub;
import com.prakhar.dvdstore.DvdServiceImpl;

public class DvdServiceImplTest {

    private static final String REFERENCE_PREFIX = "DVD-";
    private static final String TOP_GUN_DVD = REFERENCE_PREFIX + "TG423";
    private static final String NOT_EXISTENT_DVD = REFERENCE_PREFIX + "999";
    private static final String SHREK_DVD = REFERENCE_PREFIX + "S765";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private DvdServiceImpl service;
    private DvdRepository repository;

    @Before
    public void createService() {
	repository = new DvdRepositoryStub();
	service = new DvdServiceImpl(repository);
    }

    @Test
    public void retrieveDvdWhenInputIsNull() throws Exception {
	exception.expect(IllegalArgumentException.class);
	exception.expectMessage(DvdServiceImpl.REFERENCE_BAD_FORMAT_ERR_MSG);
	service.retrieveDvd(null);
    }

    @Test
    public void retrieveDvdWhenInputHasBadFormat() throws Exception {
	exception.expect(IllegalArgumentException.class);
	exception.expectMessage(DvdServiceImpl.REFERENCE_BAD_FORMAT_ERR_MSG);
	service.retrieveDvd("badReference");
    }

    @Test
    public void retrieveDvdWhenInputHasBadFormatIsCaseSensitive()
	    throws Exception {
	exception.expect(IllegalArgumentException.class);
	exception.expectMessage(DvdServiceImpl.REFERENCE_BAD_FORMAT_ERR_MSG);
	service.retrieveDvd(REFERENCE_PREFIX.toLowerCase());
    }

    @Test(expected = DvdNotFoundException.class)
    public void retrieveDvdWhenDvdNotExists() throws Exception {
	service.retrieveDvd(NOT_EXISTENT_DVD);
    }

    @Test
    public void retrieveDvdWhenDvdExist() throws Exception {
	assertEquals("Topgun", service.retrieveDvd(TOP_GUN_DVD).getTitle());
    }

    @Test
    public void getSummaryWhenInputIsBadFormatted() throws Exception {
	exception.expect(IllegalArgumentException.class);
	exception.expectMessage(DvdServiceImpl.REFERENCE_BAD_FORMAT_ERR_MSG);
	service.getDvdSummary("badReference");
    }

    @Test
    public void getSummaryWhenInputIsNull() throws Exception {
	exception.expect(IllegalArgumentException.class);
	exception.expectMessage(DvdServiceImpl.REFERENCE_BAD_FORMAT_ERR_MSG);
	service.getDvdSummary(null);
    }

    @Test(expected = DvdNotFoundException.class)
    public void getSummaryWhenDvdNotFound() throws Exception {
	service.getDvdSummary(NOT_EXISTENT_DVD);
    }

    @Test
    public void getSummaryWhenReviewIsLessEqualThan10Characters()
	    throws Exception {
	String expectedSummary = "[DVD-TG423] Topgun - All action film";
	String actualSummary = service.getDvdSummary(TOP_GUN_DVD);
	assertEquals(expectedSummary, actualSummary);
    }

    @Test
    public void getSummaryWhenReviewIsMoreThan10Characters() throws Exception {
	String expectedSummary = "[DVD-S765] Shrek - Big green monsters, they're just all the rage these days...";
	String actualSummary = service.getDvdSummary(SHREK_DVD);
	assertEquals(expectedSummary, actualSummary);
    }

}
