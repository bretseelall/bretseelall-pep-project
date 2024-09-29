package Service;

import Model.Message;
import DAO.MessagesDAO;

import java.util.*;


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

    public List<Message> getAllMessages(){
        return messagesDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messagesDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){
        Message messCheck = new Message();
        messCheck = messagesDAO.getMessageById(id);
        if(messCheck != null)
        {
            messagesDAO.deleteMessageById(id);
        }
        return messCheck;
            
    }


    
}
