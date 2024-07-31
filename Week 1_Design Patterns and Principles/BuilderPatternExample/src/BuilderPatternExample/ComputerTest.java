package BuilderPatternExample;

public class ComputerTest {

    public static void main(String[] args) {
        // Create a Computer instance using the Builder pattern
        Computer gamingPC = new Computer.Builder()
                .setCPU("Intel i9")
                .setRAM(32)
                .setStorage(1000)
                .setSSD(true)
                .setGraphicsCard("NVIDIA GeForce RTX 3080")
                .build();

        Computer officePC = new Computer.Builder()
                .setCPU("Intel i5")
                .setRAM(8)
                .setStorage(500)
                .setSSD(false)
                .build();

        // Output the Computer instances
        System.out.println(gamingPC);
        System.out.println(officePC);
    }
}
