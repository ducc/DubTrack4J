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

package cmd;

import io.sponges.dubtrack4j.framework.Message;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.SongInfo;
import io.sponges.dubtrack4j.framework.User;

import java.io.IOException;

public class QueueCommand extends Command {

    public QueueCommand() {
        super("queues a song", "queue", "play", "q", "p");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) throws IOException {
        if (args.length < 2) {
            room.sendMessage("Invalid args! !queue <source> <id>");
            return;
        }

        String typeString = args[0].toUpperCase();
        SongInfo.SourceType type = SongInfo.SourceType.valueOf(typeString);

        if (type == null) {
            room.sendMessage("Invalid type " + args[0] + "! Valid types: youtube, soundcloud");
            return;
        }

        String id = args[1];
        room.queueSong(type, id);
        room.sendMessage("Queued the song " + id + "!");
    }

}
