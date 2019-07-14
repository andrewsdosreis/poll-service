package com.sicredi.pollservice.model;

public class PollResultDto {

    private Integer totalVotes;
    private Integer yesVotes;
    private Integer noVotes;
    private String result;

    public PollResultDto(Integer totalVotes, Integer yesVotes, Integer noVotes, String result) {
        this.totalVotes = totalVotes;
        this.yesVotes = yesVotes;
        this.noVotes = noVotes;
        this.result = result;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getYesVotes() {
        return yesVotes;
    }

    public void setYesVotes(Integer yesVotes) {
        this.yesVotes = yesVotes;
    }

    public Integer getNoVotes() {
        return noVotes;
    }

    public void setNoVotes(Integer noVotes) {
        this.noVotes = noVotes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}