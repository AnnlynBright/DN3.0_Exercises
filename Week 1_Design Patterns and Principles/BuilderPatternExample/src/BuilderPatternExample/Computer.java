package BuilderPatternExample;

public class Computer {

    // Attributes of the Computer class
    private final String CPU;
    private final int RAM;
    private final int storage;
    private final boolean isSSD;
    private final String graphicsCard;

    // Private constructor to be used by the Builder class
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.isSSD = builder.isSSD;
        this.graphicsCard = builder.graphicsCard;
    }

    // Getters for the attributes
    public String getCPU() {
        return CPU;
    }

    public int getRAM() {
        return RAM;
    }

    public int getStorage() {
        return storage;
    }

    public boolean isSSD() {
        return isSSD;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    // Static nested Builder class
    public static class Builder {
        private String CPU;
        private int RAM;
        private int storage;
        private boolean isSSD;
        private String graphicsCard;

        // Builder methods for setting attributes
        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }

        public Builder setRAM(int RAM) {
            this.RAM = RAM;
            return this;
        }

        public Builder setStorage(int storage) {
            this.storage = storage;
            return this;
        }

        public Builder setSSD(boolean isSSD) {
            this.isSSD = isSSD;
            return this;
        }

        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        // Build method to create the Computer instance
        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer [CPU=" + CPU + ", RAM=" + RAM + "GB, Storage=" + storage + "GB, SSD=" + isSSD + ", GraphicsCard=" + graphicsCard + "]";
    }
}

