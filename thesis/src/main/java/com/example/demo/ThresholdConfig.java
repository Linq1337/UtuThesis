public class ThresholdConfig {
    private double lowThreshold;
    private double mediumThreshold;
    private double highThreshold;

    // initialize thresholds
    public ThresholdConfig(double lowThreshold, double mediumThreshold, double highThreshold) {
        this.lowThreshold = lowThreshold;
        this.mediumThreshold = mediumThreshold;
        this.highThreshold = highThreshold;
    }

    public double getLowThreshold() {
        return lowThreshold;
    }

    public double getMediumThreshold() {
        return mediumThreshold;
    }

    public double getHighThreshold() {
        return highThreshold;
    }
}
