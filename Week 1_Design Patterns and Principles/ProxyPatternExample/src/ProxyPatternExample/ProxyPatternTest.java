package ProxyPatternExample;


public class ProxyPatternTest {

    public static void main(String[] args) {
        Image image1 = new ProxyImage("image1.jpg");
        Image image2 = new ProxyImage("image2.jpg");

        // Image will be loaded and displayed on the first call
        image1.display();
        
        // Image is already loaded and only displayed on subsequent calls
        image1.display();

        // Image will be loaded and displayed on the first call
        image2.display();
    }
}

