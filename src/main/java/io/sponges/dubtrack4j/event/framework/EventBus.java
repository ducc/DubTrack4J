package io.sponges.dubtrack4j.event.framework;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * A simple, thread safe and reflectionless event handling system.
 * 
 * @author Connor Spencer Harries
 */
public final class EventBus {

    private final Multimap<Class<? extends Event>, Consumer<Event>> consumerMap;
    private final ReadWriteLock lock;

    public EventBus() {
        this.consumerMap = ArrayListMultimap.create();
        this.lock = new ReentrantReadWriteLock();
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> boolean register(Class<T> clazz, Consumer<T> consumer) {
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkNotNull(clazz, "clazz cannot be null");
        lock.writeLock().lock();
        try {
            Consumer<Event> handler = (Consumer<Event>) consumer;
            return consumerMap.put(clazz, handler);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public <T extends Event> boolean unregister(Consumer<T> consumer) {
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        lock.writeLock().lock();
        try {
            boolean removed = false;
            for(Class<? extends Event> clazz : consumerMap.keySet()) {
                Collection<Consumer<Event>> consumers = consumerMap.get(clazz);
                for (Iterator<Consumer<Event>> iterator = consumers.iterator(); iterator.hasNext(); ) {
                    if (iterator.next() == consumer) {
                        iterator.remove();
                        removed = true;
                    }
                }
            }
            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public <T extends Event> T post(T event) {
        Preconditions.checkNotNull(event, "event cannot be null");
        lock.readLock().lock();
        try {
            Class<? extends Event> clazz = event.getClass();
            Collection<Consumer<Event>> consumers = consumerMap.get(clazz);
            for (Consumer<Event> consumer : consumers) {
                consumer.accept(event);
            }
            event.postEvent();
            return event;
        } finally {
            lock.readLock().unlock();
        }
    }
}