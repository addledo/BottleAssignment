public enum Material {
    PLASTIC("Plastic"),
    METAL("Metal"),
    GLASS("Glass");

    public final String displayName;   //Made public as field is final and immutable

    Material(String displayName) {
        this.displayName = displayName;
    }

}