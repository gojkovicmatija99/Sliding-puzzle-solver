package observer;

public interface IPublisher {
    public void addSubscriber(ISubscriber subscriber);

    public void notifySubscriber(int nodes);
}
