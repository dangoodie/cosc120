public enum Purebred {
    YES, NO, NA;

    @Override
    public String toString() {
        String prettified = "NA";
        switch (this) {
            case YES -> prettified = "Yes";
            case NO -> prettified = "No";
        }
        return prettified;
    }
}
