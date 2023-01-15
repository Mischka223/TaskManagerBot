package task.manager.telegram.bot.model;

public class Action {
    private long chatId;
    private String actionName;


    public Action(long chatId, String actionName) {
        this.chatId = chatId;
        this.actionName = actionName;
    }


    public long getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }


    @Override
    public String toString() {
        return "Action{" +
                "chatId=" + chatId +
                ", actionName='" + actionName + '\'' +
                '}';
    }
}
