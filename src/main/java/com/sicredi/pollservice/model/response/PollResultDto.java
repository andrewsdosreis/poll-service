package com.sicredi.pollservice.model.response;

public class PollResultDto {

    private Integer totalVotes;
    private Integer yesVotes;
    private Integer noVotes;
    private String winningOption;

    public PollResultDto(Integer totalVotes, Integer yesVotes, Integer noVotes, String winningOption) {
        this.totalVotes = totalVotes;
        this.yesVotes = yesVotes;
        this.noVotes = noVotes;
        this.winningOption = winningOption;
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

    public String getWinningOption() {
        return winningOption;
    }

    public void setResult(String winningOption) {
        this.winningOption = winningOption;
    }

    @Override
    public String toString() {
        return "PollResult [noVotes=" + noVotes + ", winningOption=" + winningOption + ", totalVotes=" + totalVotes + ", yesVotes="
                + yesVotes + "]";
    }

}