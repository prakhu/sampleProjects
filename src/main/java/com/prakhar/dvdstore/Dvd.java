package com.prakhar.dvdstore;

public class Dvd {

    private final String reference;
    private final String title;
    private final String review;
    private final String summary;

    public Dvd(String reference, String title, String description) {
	this.reference = reference;
	this.title = title;
	this.review = description;
	this.summary = String.format(
		"[%s] %s - %s",
		reference,
		title,
		(isReviewMoreThan10Words() ? shrinkReviewAndRemoveLastCommaCharacter() : review));
    }

    private String shrinkReviewAndRemoveLastCommaCharacter() {
	return shrinkReviewTo10Words()
		.replace(",...", "...");
    }

    private String shrinkReviewTo10Words() {
	return review.substring(0,
		review.indexOf(review.split(" ")[10])).trim() + "...";
    }

    private boolean isReviewMoreThan10Words() {
	return review.split(" ").length > 10;
    }

    public String getReview() {
	return review;
    }

    public String getReference() {
	return reference;
    }

    public String getTitle() {
	return title;
    }

    public String getSummary() {
	return summary;
    }
}
