package DTO.customerDTO.responseDTO;


import java.util.ArrayList;
import java.util.List;

public class FeedbackResponseDTO {
    private String description;
    private int rate;
    private List<String> imageFeedbacks = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<String> getImageFeedbacks() {
        return imageFeedbacks;
    }

    public void setImageFeedbacks(List<String> imageFeedbacks) {
        this.imageFeedbacks = imageFeedbacks;
    }
}
