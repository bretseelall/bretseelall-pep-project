package Service;

import Model.Message;
import DAO.MessagesDAO;


public class MessagesService {

    private MessagesDAO messagesDAO;

    public MessagesService(){    // No-args constructor
        messagesDAO = new MessagesDAO();
    }

    public MessagesService(MessagesDAO messagesDAO){   // Constructor for a DAO provided
        this.messagesDAO = messagesDAO;
    }

    public Message addMessage(Message message){
        if(message.message_text.isBlank() || (message.message_text.length() > 255))
        {
            return null;
        }
        else{
            return messagesDAO.insertNewMessage(message);
        }
    }
    
}
