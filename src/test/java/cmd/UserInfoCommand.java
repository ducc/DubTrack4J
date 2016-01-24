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
import io.sponges.dubtrack4j.framework.ProfileImage;
import io.sponges.dubtrack4j.framework.Room;
import io.sponges.dubtrack4j.framework.User;

import java.io.IOException;

public class UserInfoCommand extends Command {

    public UserInfoCommand() {
        super("shows info about the user", "userinfo", "user", "u");
    }

    @Override
    public void onCommand(Room room, User user, Message message, String[] args) throws IOException {
        User u;

        if (args.length == 0) {
            u = user;
        } else {
            String username = args[0];
            u = room.getUserByUsername(username);
        }

        if (u == null) {
            try {
                room.sendMessage("Could not find that user!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(u.getId()).append(", ");

        ProfileImage image = u.getProfileImage();

        builder.append("image dimensions: ").append(image.getHeight()).append("x").append(image.getWidth()).append(", ");
        builder.append("profile image: ").append(image.getUrl());

        try {
            room.sendMessage(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
