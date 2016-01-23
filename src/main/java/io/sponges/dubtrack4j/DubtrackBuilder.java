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

package io.sponges.dubtrack4j;

import io.sponges.dubtrack4j.internal.DubtrackAPIImpl;
import io.sponges.dubtrack4j.util.Logger;

import java.io.IOException;

public final class DubtrackBuilder {

    private DubtrackBuilder() {
    }

    public static Builder create(String username, String password) {
        return new Builder(username, password);
    }

    public static class Builder {

        private final String username, password;

        private Logger.LoggingMode loggingMode = Logger.LoggingMode.NORMAL;

        private Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public DubtrackAPI build() {
            DubtrackAPIImpl api = new DubtrackAPIImpl(username, password);

            api.setLoggingMode(loggingMode);

            return api;
        }

        public DubtrackAPI buildAndLogin() throws IOException {
            return build().login();
        }

        public Builder setLoggingMode(Logger.LoggingMode loggingMode) {
            this.loggingMode = loggingMode;
            return this;
        }

    }

}
