package task.manager.telegram.bot.model;

import java.util.List;

public class Sprint {
    private long chatId;
    private String sprintName;
    private int duration; //days
    private List<Plan> plans;

    public Sprint(long chatId, String sprintName) {
        this.chatId = chatId;
        this.sprintName = sprintName;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }


    @Override
    public String toString() {
        return "Sprint{" +
                "sprintName='" + sprintName + '\'' +
                ", duration=" + duration +
                ", plans=" + plans +
                '}';
    }
}
