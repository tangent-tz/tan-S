package semanticAnalyzer.types;

public enum ReferenceType implements Type {
    STRING(4);

    private int sizeInBytes;
    private String infoString;

    private ReferenceType(int size) {
        this.sizeInBytes = size;
        this.infoString = toString();
    }
    private ReferenceType(int size, String infoString) {
        this.sizeInBytes = size;
        this.infoString = infoString;
    }
    public int getSize() {
        return sizeInBytes;
    }
    public String infoString() {
        return infoString;
    }
}
