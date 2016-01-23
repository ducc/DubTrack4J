# DubTrack4J
A Java API for Dubtrack.

## THIS IS A PROTOTYPE

### Maven
Will add soon

### Usage
##### Creating your DubtrackAPI Builder instance:
```java
DubtrackBuilder builder = DubtrackBuilder.create(username, password);
DubtrackAPI dubtrack = builder.buildAndLogin();
```

##### Joining a room:
This method takes a String room name, which is found at the end of the join url. http://dubtrack.fm/join/RoomName
```java
Room room = dubtrack.joinRoom(roomName);
```

##### Registering a listener:
Listeners implement the `Listener` interface.
```java
dubtrack.getEventManager().registerListener(new Listener() {
    @Override
    public void onChat(UserChatEvent event) {
        System.out.println("Got message " + event.getMessage().getContent());
    }
});
```
See [Listener.java](https://github.com/Sponges/DubTrack4J/blob/master/src/main/java/io/sponges/dubtrack4j/event/framework/Listener.java) for the interface containing all the event methods.

##### Sending a message
```java
room.sendMessage("This is a message!");
```

##### Other stuff
Check out the javadocs @ placeholder.exe

### TODOs
https://github.com/Sponges/DubTrack4J/issues/1
