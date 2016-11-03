/**
 * Response object that has a time when it start and a flag to check if its dropped.
 * @author Tan Pham
 * @version 2016.11.2
 *
 */
public class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}
