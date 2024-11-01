package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessagesService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessagesService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessagesService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageFromUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        }
        else{
            context.status(400);
        }
    }

    private void postUserLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAccount = accountService.getAccount(account);
        if(loginAccount != null){
            context.json(mapper.writeValueAsString(loginAccount));
        }
        else{
            context.status(401);
        }
    }

    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        Message message = new Message();
        message = messageService.getMessageById(Integer.parseInt(context.pathParam("message_id")));
        if(message != null)
            context.json(message);
    }

    private void deleteMessageByIdHandler(Context context) {
        Message message = messageService.deleteMessageById(Integer.parseInt(context.pathParam("message_id")));
        if(message != null){
            context.json(message);
        }
    }

    private void patchMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(messageId, message.message_text);
        if(updatedMessage != null)
            context.json(updatedMessage);
        else{
            context.status(400);
        }
    }

    private void getMessageFromUserHandler(Context context) {
        int userId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromUser(userId);
        context.json(messages);
    }


}