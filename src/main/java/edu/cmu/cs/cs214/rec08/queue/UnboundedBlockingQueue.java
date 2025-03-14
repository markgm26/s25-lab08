package edu.cmu.cs.cs214.rec08.queue;

import java.util.ArrayDeque;
import java.util.Deque;

import net.jcip.annotations.ThreadSafe;
import net.jcip.annotations.GuardedBy;

/**
 * Modify this class to be thread-safe and be an UnboundedBlockingQueue.
 */
@ThreadSafe
public class UnboundedBlockingQueue<E> implements SimpleQueue<E> {
    @GuardedBy("this")
    private Deque<E> queue = new ArrayDeque<>();

    public UnboundedBlockingQueue() { }

    public synchronized boolean isEmpty() { return queue.isEmpty(); }

    public synchronized int size() { return queue.size(); }

    public synchronized E peek() { return queue.peek(); }

    //blocks on this because of synchronized keyword, notifies all threads waiting on this after adding element
    public synchronized void enqueue(E element) {
        queue.add(element);
        notifyAll();
    }

    //blocks on this because of synchronized keyword, waits for queue to be non-empty
    public synchronized E dequeue() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.remove();
    }

    public String toString() { return queue.toString(); }
}
