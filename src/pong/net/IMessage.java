package pong.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Sean on 09/03/2015.
 *
 * Used to define messages and organize the input/output
 */
public interface IMessage {
    /**
     * Writes bytes to the output stream which will be sent to the other side
     * @param output stream which is connected to the listening socket
     * @throws IOException thrown if stream has been closed
     */
    void toBytes(ObjectOutputStream output) throws IOException;

    /**
     * Reads bytes from the output stream that has been received
     * @param input stream which is connected to the listening socket
     * @throws IOException thrown if stream has been closed
     */
    void fromBytes(ObjectInputStream input) throws IOException;
}
