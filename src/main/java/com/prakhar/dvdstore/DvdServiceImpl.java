package com.prakhar.dvdstore;

public class DvdServiceImpl implements DvdService {

    public static final String REFERENCE_BAD_FORMAT_ERR_MSG = "DVD reference must begin with \'DVD-\'";
    private static final String REFERENCE_PREFIX = "DVD-";
    private final DvdRepository repository;

    @SuppressWarnings("unused")
    private DvdServiceImpl() {
	repository = null;
    }

    public DvdServiceImpl(DvdRepository repository) {
	this.repository = repository;
    }

    @Override
    public Dvd retrieveDvd(String dvdReference) throws DvdNotFoundException,
	    IllegalArgumentException {
	if (null == dvdReference || !dvdReference.startsWith(REFERENCE_PREFIX))
	    throw new IllegalArgumentException(REFERENCE_BAD_FORMAT_ERR_MSG);
	Dvd dvd = repository.retrieveDvd(dvdReference);
	if (null == dvd)
	    throw new DvdNotFoundException();
	return dvd;
    }

    @Override
    public String getDvdSummary(String dvdReference)
	    throws DvdNotFoundException {
	return retrieveDvd(dvdReference).getSummary();
    }

}
