/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Joe Burnard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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