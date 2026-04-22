package org.w2105627_coursework;

public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String message){
        super(message);
    }
}
