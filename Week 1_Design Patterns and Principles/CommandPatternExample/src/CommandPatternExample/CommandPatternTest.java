package CommandPatternExample;

public class CommandPatternTest {

    public static void main(String[] args) {
        // Create a receiver
        Light livingRoomLight = new Light();

        // Create concrete commands
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        // Create an invoker
        RemoteControl remote = new RemoteControl();

        // Turn the light on
        remote.setCommand(lightOn);
        remote.pressButton();

        // Turn the light off
        remote.setCommand(lightOff);
        remote.pressButton();
    }
}
