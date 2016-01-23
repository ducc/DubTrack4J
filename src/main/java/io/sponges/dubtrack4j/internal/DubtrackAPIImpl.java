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

package io.sponges.dubtrack4j.internal;

import com.google.common.collect.ImmutableMap;
import com.pubnub.api.PubnubException;
import io.sponges.dubtrack4j.DubtrackAPI;
import io.sponges.dubtrack4j.event.framework.EventBus;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.internal.impl.RoomImpl;
import io.sponges.dubtrack4j.internal.request.JoinRoomRequest;
import io.sponges.dubtrack4j.internal.request.Requester;
import io.sponges.dubtrack4j.internal.request.RoomInfoRequest;
import io.sponges.dubtrack4j.internal.subscription.Subscribe;
import io.sponges.dubtrack4j.util.Logger;
import okhttp3.OkHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DubtrackAPIImpl implements DubtrackAPI {

    public static final String USER_AGENT = "Mozilla/5.0 Dubtrack4J (github.com/sponges/dubtrack4j)";

    private static Logger.LoggingMode loggingMode = Logger.LoggingMode.NORMAL;

    //              id  room
    private final Map<String, Room> rooms = new HashMap<>();

    private final DubAccount account;
    private final EventBus eventBus;

    private final OkHttpClient httpClient;
    private final Requester requester;

    public DubtrackAPIImpl(String username, String password) {
        this.account = new DubAccount(this, username, password);
        this.eventBus = new EventBus();

        this.httpClient = new OkHttpClient.Builder().build();
        this.requester = new Requester(this);
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public Requester getHttpRequester() {
        return requester;
    }

    @Override
    public DubtrackAPI login() throws IOException {
        account.login();
        return this;
    }

    @Override
    public Room joinRoom(String name) throws IOException, PubnubException {
        Room room = new JoinRoomRequest(this, name, account).request();
        new Subscribe(this, room.getName());

        return room;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public Room getRoom(String id) {
        return rooms.get(id);
    }

    @Override
    public Map<String, Room> getRooms() {
        return ImmutableMap.copyOf(rooms);
    }

    // Method exists because #getRooms is now immutable
    public void addRoom(String id, Room room) {
        rooms.put(id, room);
    }

    // Internal as manages token
    public DubAccount getAccount() {
        return account;
    }

    public RoomImpl loadRoom(String id) throws IOException {
        Room room = getRoom(id);

        if (room == null) {
            JSONObject roomInfo = new RoomInfoRequest(this, id, account).request();
            String name = roomInfo.getJSONObject("data").getString("roomUrl");
            rooms.put(id, new RoomImpl(this, name, id));
            room = getRoom(id);
        }

        return (RoomImpl) room;
    }

    public static Logger.LoggingMode getLoggingMode() {
        return loggingMode;
    }

    public void setLoggingMode(Logger.LoggingMode loggingMode) {
        DubtrackAPIImpl.loggingMode = loggingMode;
    }

}
