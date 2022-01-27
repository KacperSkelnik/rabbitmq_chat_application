public class Client {
    static public void main(String[] args) throws Exception {
        new Thread(new Receive()).start();
        new Thread(new Send()).start();
    }
}