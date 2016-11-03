import java.util.ArrayList;

/**
 * Buffer object that process the coming packets.
 * @author Tan Pham
 * @version 2016.11.2
 *
 */

public class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
    }

    public Response Process(Request request) {
        if(finish_time_.size()==0){
        	finish_time_.add(request.arrival_time+request.process_time);
        	return new Response(false, request.arrival_time);
        }else if(finish_time_.size()<size_){
        	finish_time_.add(finish_time_.get(finish_time_.size()-1)+request.process_time);
        	if(finish_time_.get(0)==request.arrival_time) finish_time_.remove(0);
        	return new Response(false,finish_time_.get(finish_time_.size()-2));
        }else{
        	return new Response(true, -1);
        }
        
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
}
