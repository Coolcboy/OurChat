import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeModel extends MessageModel {

    private int totalCount;

    @Inject
    public HomeModel(IDataRepository repository) {
        super(repository);
    }

    /**
     * Get recent message list including friends and group chats.
     */
    public List<Message> queryMessageList(String userId, int count) {
        MessageDao messageDao = getMessageDao();
        GroupMessageDao groupMessageDao = getGroupMessageDao();
        List<RecentChat> recentChats = getRecentChatDao().getRecentChats(userId);
        // Removed group-related code

        List<Message> messageLists = new ArrayList<>();
        totalCount = 0;

        // Iterate through recent chat messages
        for (RecentChat recentChat : recentChats) {
            MessageResp messageResp = messageDao.getLastMessageBySenderId(userId, recentChat.getChatId(), recentChat.getChatId());
            int unreadCount = messageDao.getUnreadList(userId, recentChat.getChatId()).size();
            totalCount += unreadCount;

            Message messageList = new Message();
            messageList.setCount(unreadCount);
            messageList.setId(recentChat.getChatId());
            messageList.setName(recentChat.getShowName());
            messageList.setAvatar(recentChat.getAvatar());
            messageList.setMessageMode(Message.userMode);
            messageList.setDateTime(recentChat.getDateTime());

            if (messageResp != null) {
                messageList.setSenderId(messageResp.getSender());
                messageList.setMessage(messageResp.getMessage());
                messageList.setMessageType(messageResp.getMessageType());
                messageList.setDateTime(messageResp.getDateTime());
            }

            List<User> users = getUserDao().getUsers();
            if (users != null) {
                for (User user : users) {
                    if (user.getUserId().equals(id)) {
                        messageList.setName(user.getShowName());
                        messageList.setAvatar(user.getAvatar());
                        break;
                    }
                }
            }

            messageLists.add(messageList);
        }

        // Sorting by date descending
        Collections.sort(messageLists, new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                return m2.getDateTime().compareTo(m1.getDateTime());
            }
        });

        Timber.d("list:" + messageLists);

        if (count < messageLists.size()) {
            return messageLists.subList(0, count);
        }

        return messageLists;
    }
}