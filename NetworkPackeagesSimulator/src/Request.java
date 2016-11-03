/**
 * Request object that has a pair of time of arrival and the time cost to process.
 * @author Tan Pham
 * @version 2016.11.2
 */
public class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}
