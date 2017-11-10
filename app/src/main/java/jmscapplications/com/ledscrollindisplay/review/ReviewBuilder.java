package jmscapplications.com.ledscrollindisplay.review;

public class ReviewBuilder {
    private int frequency = 2;
    private int minSession = 2;
    private final String url;

    public ReviewBuilder(String url) {
        this.url = url;
    }

    public ReviewBuilder setMinSession(int min) {
        this.minSession = min;
        return this;
    }

    public ReviewBuilder setFrequency(int days) {
        this.frequency = days;
        return this;
    }

    public Review build() {
        return new Review(this.url, this.minSession, this.frequency);
    }
}
