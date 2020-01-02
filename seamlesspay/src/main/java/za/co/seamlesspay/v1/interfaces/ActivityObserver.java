package za.co.seamlesspay.v1.interfaces;

public interface ActivityObserver {

  interface ReadIntent {

    void doSomethingRead();

  }

  interface RecieveIntent {

    void doSomethingRecieve();

  }

}
