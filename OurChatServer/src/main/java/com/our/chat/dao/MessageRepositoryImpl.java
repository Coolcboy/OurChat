package com.our.chat.dao;

import com.our.chat.dto.ChatMessageOneDTO;
import com.our.chat.dto.MessageDTO;
import com.our.chat.service.Request.ChatMessageRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDTO> findLatestMessagesByAccount(Integer account) {
        // 以下是一个使用原生SQL查询的示例
        String sql = "SELECT \n" +
                "    u.avatar_url AS avatar,\n" +
                "    u.name AS name,\n" +
                "    CASE \n" +
                "        WHEN cm2.sender_id = :account THEN cm2.receiver_id \n" + // 如果当前账户是发送者，则对方是接收者
                "        ELSE cm2.sender_id \n" + // 否则，当前账户是接收者，对方是发送者
                "    END AS oppositeAccount,\n" +
                "    cm2.content AS last_message,\n" +
                "    cm2.timestamp AS time\n" +
                "FROM \n" +
                "    (\n" +
                "        SELECT \n" +
                "            sender_id, \n" +
                "            receiver_id, \n" +
                "            MAX(timestamp) AS maxTimestamp\n" +
                "        FROM Chat_Messages\n" +
                "        WHERE sender_id = :account OR receiver_id = :account\n" +
                "        GROUP BY \n" +
                "            CASE \n" +
                "                WHEN sender_id = :account THEN receiver_id \n" +
                "                ELSE sender_id \n" +
                "            END\n" +
                "    ) AS latestMsg\n" +
                "JOIN Chat_Messages cm2 ON \n" +
                "    cm2.sender_id = latestMsg.sender_id \n" +
                "    AND cm2.receiver_id = latestMsg.receiver_id \n" +
                "    AND cm2.timestamp = latestMsg.maxTimestamp\n" +
                "JOIN users u ON \n" +
                "    CASE \n" +
                "        WHEN latestMsg.sender_id = :account THEN cm2.receiver_id \n" +
                "        ELSE cm2.sender_id \n" +
                "    END = u.account\n" +
                "ORDER BY cm2.timestamp DESC;";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("account", account);

        List<MessageDTO> messageDTOS = new ArrayList<>();
        List<Object[]> results = query.getResultList();
        for (Object[] result : results) {
            // 确保结果中的元素是Timestamp类型再进行转换
            if (result[4] instanceof Timestamp) {
                Timestamp timestamp = (Timestamp) result[4];
                String formattedTime = timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


                // 注意：这里的时间戳已经被格式化为字符串了，所以对应的MessageDTO构造函数参数也应该调整
                MessageDTO messageDTO = new MessageDTO(
                        (String) result[0], // avatar
                        (String) result[1], // name
                        ((Number) result[2]).intValue(), // oppositeAccount, 注意类型转换
                        (String) result[3], // last_message
                        formattedTime // 已经是字符串格式的时间了
                );
                messageDTOS.add(messageDTO);
            } else {
                // 处理非Timestamp类型的情况，根据实际情况决定如何处理，比如抛出异常或记录日志
                throw new IllegalStateException("Expected Timestamp but got " + result[4].getClass().getName());
            }
        }


        return messageDTOS;
    }

    @Override
    public List<ChatMessageOneDTO> findChatMessageByRequest(ChatMessageRequest chatMessageRequest) {
        Integer senderId = chatMessageRequest.getSender_id();
        Integer receiverId = chatMessageRequest.getReceiver_id();

        String jpql = "SELECT new com.our.chat.dto.ChatMessageOneDTO(cm.senderId, cm.receiverId, cm.content, cm.timestamp) "
                + "FROM Chat_Messages cm "
                + "WHERE (cm.senderId = :senderId AND cm.receiverId = :receiverId) "
                + "   OR (cm.senderId = :receiverId AND cm.receiverId = :senderId) "
                + "ORDER BY cm.timestamp DESC";

        TypedQuery<ChatMessageOneDTO> query = entityManager.createQuery(jpql, ChatMessageOneDTO.class);
        query.setParameter("senderId", senderId);
        query.setParameter("receiverId", receiverId);

        return query.getResultList();
    }

}
