package hadoop2_6.controlledJobsample.example01;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by sungang on 2016/1/14.16:00
 */
public class MovieRatingPair implements Comparable<MovieRatingPair>, Writable {

    String movieId;
    double averageRating;

    public MovieRatingPair() {
        this.movieId = new String("");
        this.averageRating = 0.0;
    }

    public MovieRatingPair(String movieId, double averageRating) {
        super();
        this.movieId = movieId;
        this.averageRating = averageRating;
    }

    public MovieRatingPair(MovieRatingPair pair) {
        this.movieId = pair.movieId;
        this.averageRating = pair.averageRating;
    }

    @Override
    public int compareTo(MovieRatingPair o) {
        if (this.averageRating > o.averageRating) {
            return 1;
        } else if (this.averageRating == o.averageRating) {
            if (this.movieId.equals(o.movieId)) {
                return 0;
            }
        }
        return -1;
    }

    public String toString() {
        return "Movie Id -> " + this.movieId + " Rating -> " + this.averageRating;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.movieId);
        out.writeDouble(this.averageRating);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.movieId = in.readUTF();
        this.averageRating = in.readDouble();
    }
}
