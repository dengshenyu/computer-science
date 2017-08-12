/**
 * Created by shenyuan on 17/4/15.
 */
public class SimpleReentrantLock {
    private final Object object = new Object();

    private volatile Thread curThread;
    private volatile int count;

    public void lock() throws InterruptedException {
        synchronized (object) {
            while (curThread != null && curThread != Thread.currentThread())
                object.wait();

            if (curThread == Thread.currentThread()) {
                count++;
            } else {
                curThread = Thread.currentThread();
                count++;
            }
        }
    }

    public void unlock() throws IllegalMonitorStateException {
        synchronized (object) {
            if (curThread != Thread.currentThread())
                throw new IllegalMonitorStateException();

            count--;
            if (count == 0) {
                curThread = null;
                object.notify();
            }
        }
    }

}
