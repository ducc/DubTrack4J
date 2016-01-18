package io.sponges.dubtrack4j;

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
