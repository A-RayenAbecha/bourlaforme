/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bourlaforme.gui.front.message;

import com.bourlaforme.MainApp;
import com.bourlaforme.entities.Conversation;
import com.bourlaforme.entities.SessionManager;
import com.bourlaforme.entities.User;
import com.bourlaforme.services.ServiceMessagerie;
import com.bourlaforme.services.MessageService;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MessagerieController implements Initializable {

    @FXML
    private ListView<String> users;
    @FXML
    private ListView<String> conversation;
    @FXML
    private ListView<String> msg_source;
    @FXML
    private TextField message;
    @FXML
    private Button send_button;

    //Entities
    private Conversation c;
    private ListView<String> aux = new ListView();
    List<String> status = new ArrayList();
    List<String> convo = new ArrayList<>();
    ServiceMessagerie sm = ServiceMessagerie.getInstance();
    MessageService su = new MessageService();
    SessionManager sessionManager = SessionManager.getInstance();
    private String current_id = "";
    private User p = null;
    db_buffer dbt;
    int b_index = 0;
    int convo_index = 0;
    Image tick = new Image(getClass().getResource("/resources/tick.png").toString(), true);
    Image cross = new Image(getClass().getResource("/resources/cross.png").toString(), true);

    ImageView viewt = new ImageView(tick);
    ImageView viewc = new ImageView(cross);

    Image notif = new Image(getClass().getResource("/resources/notif.png").toString(), true);
    ImageView viewn = new ImageView(notif);

    List<String> contacts = new ArrayList();
    List<String> last_msg = new ArrayList();
    @FXML
    private Button add_btn;
    @FXML
    private Button del_btn;
    @FXML
    private TextField crud_bar;
    @FXML
    private TextField search_bar;
    @FXML
    private AnchorPane anchor_window;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p = MainApp.session;

        String msg = "Note that the thread ID returned by getId() is a long value, which uniquely identifies the thread within the JVM.";

        current_id = String.valueOf(p.getId());

        contacts = sm.findContacts(current_id);
        contacts.forEach(c -> {
            users.getItems().add(su.getOneById(Integer.valueOf(c)).getPrenom());
            last_msg.add(sm.getLastMessage(sm.findConvo(current_id, c).getId_convo()));

        });

        for (int i = 0; i < last_msg.size(); i++) {
            if (last_msg.get(i) == null) {
                last_msg.set(i, "");
            }
            System.out.println("Messages : " + last_msg.get(i));
        }

        //dbt = new db_buffer(0, "", contacts, msg_source);
        dbt = new db_buffer(current_id, last_msg, contacts, conversation, msg_source, 0, anchor_window);

        dbt.setName("606");
        dbt.start();

        status = sm.getStatus(current_id);

        viewt.setFitHeight(15);
        viewt.setPreserveRatio(true);
        viewc.setFitHeight(15);
        viewc.setPreserveRatio(true);

        /*private Image notif = new Image(getClass().getResource("notif.png").toString(), true);
        private ImageView viewn = new ImageView(notif);
        viewn.setTranslateX(220);
        viewn.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
        anchor_window.getChildren().add(viewn);*/
        for (int i = 0; i < status.size(); i++) {
            if (status.get(i).compareTo("pending") == 0) {
                Button a = new Button();
                Button d = new Button();

                String id_contact = new String();
                id_contact = contacts.get(i);
                System.out.println(id_contact);
                String idc = String.valueOf(sm.findConvo(current_id, id_contact).getId_convo());
                a.setTranslateX(220);
                a.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
                a.setPrefSize(20, 20);
                a.setGraphic(viewt);

                a.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        sm.updateStatus(idc, current_id, "true");
                        anchor_window.getChildren().remove(a);
                        anchor_window.getChildren().remove(d);
                    }

                });

                d.setTranslateX(260);
                d.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
                d.setPrefSize(20, 20);
                d.setGraphic(viewc);

                d.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        sm.updateStatus(idc, current_id, "false");
                        anchor_window.getChildren().remove(a);
                        anchor_window.getChildren().remove(d);
                    }

                });

                anchor_window.getChildren().add(a);
                anchor_window.getChildren().add(d);
            }

        }

        //users.getItems().add("SERVER");
        users.getItems().stream().forEach(u -> aux.getItems().add(u));

    }

    @FXML
    private void send_message(ActionEvent event) {
        String msg = message.getText();
        if (msg.compareTo("") != 0) {
            Conversation c_tmp = sm.findConvo(String.valueOf(p.getId()), contacts.get(convo_index));
            if (c_tmp != null) {
                sm.updateMessage(c_tmp, String.valueOf(p.getId()), msg);
                conversation.getItems().add(message.getText()); //make the last message get updated from (Conversation : c)
                msg_source.getItems().add(p.getPrenom());
                dbt.set_last_indexed(message.getText(), convo_index);
                dbt.set_src(String.valueOf(p.getId()));
                message.clear();
            }
        }
    }

    @FXML
    private void get_convo(MouseEvent event) {
        //String current_user = users.getSelectionModel().getSelectedItem();
        convo_index = users.getSelectionModel().getSelectedIndex();
        String current_user = contacts.get(convo_index);
        System.out.println(current_user);
        Conversation temp = null;
        int i = 0;
        try {
            int val = Integer.parseInt(current_user);
        } catch (NumberFormatException e) {
            temp = sm.findConvo(String.valueOf(p.getId()), String.valueOf(su.getByUsername(current_user).getId()));
            i = 1;
        }
        if (i == 0) {
            temp = sm.findConvo(String.valueOf(p.getId()), current_user);
        }

        System.out.println(temp.getId_convo());
        if (temp != null) {
            String lmi = "";
            String srcc = "";
            ResultSet rs = sm.getMessages(temp.getId_convo());
            msg_source.getItems().clear();
            conversation.getItems().clear();
            try {
                while (rs.next()) {
                    if (rs.getString("message") != null) {
                        msg_source.getItems().add(su.getOneById(Integer.valueOf(rs.getString("id_source"))).getPrenom());
                        conversation.getItems().add(rs.getString("message"));
                        lmi = rs.getString("message");
                        srcc = rs.getString("id_source");
                        dbt.set_last_indexed(lmi, convo_index);
                    }

                }
                //rs.last();
                System.out.println("Index " + convo_index);
                dbt.set_conversation(conversation);
                dbt.set_src(srcc);
                dbt.set_convo_index(convo_index);

                if (anchor_window.lookup("#n" + convo_index) != null) {
                    anchor_window.getChildren().remove(anchor_window.lookup("#n" + convo_index));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            //Create id generating function
            //Create new conversation
            //Add it to SQL
        }
    }

    public void get_convo_index(int tmp_index) {
        convo_index = tmp_index;
        String current_user = contacts.get(convo_index);

        Conversation temp = sm.findConvo(String.valueOf(p.getId()), current_user);
        if (temp != null) {
            String lmi = "";
            String srcc = "";
            ResultSet rs = sm.getMessages(temp.getId_convo());
            msg_source.getItems().clear();
            conversation.getItems().clear();
            try {
                while (rs.next()) {
                    if (rs.getString("message") != null) {
                        msg_source.getItems().add(su.getOneById(Integer.valueOf(rs.getString("id_source"))).getPrenom());
                        conversation.getItems().add(rs.getString("message"));
                        lmi = rs.getString("message");
                        srcc = rs.getString("id_source");
                        dbt.set_last_indexed(lmi, convo_index);

                    }

                }
                //rs.last();
                System.out.println("Index " + convo_index);
                dbt.set_conversation(conversation);
                dbt.set_src(srcc);
                dbt.set_convo_index(convo_index);

                if (anchor_window.lookup("#n" + convo_index) != null) {
                    anchor_window.getChildren().remove(anchor_window.lookup("#n" + convo_index));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            //Create id generating function
            //Create new conversation
            //Add it to SQL
        }
    }

    @FXML
    private void add_convo(ActionEvent event) throws SQLException {
        if (crud_bar.getText().compareTo("") != 0) {
            User u_tmp = su.getByUsername(crud_bar.getText());
            if (u_tmp != null) {
                Conversation c_tmp = new Conversation(1, String.valueOf(p.getId()), String.valueOf(u_tmp.getId()), false);
                sm.ajouter(c_tmp);
                contacts.add(crud_bar.getText());
                users.getItems().add(crud_bar.getText());
                get_convo_index(users.getItems().size());
            }
        }
    }

    @FXML
    private void del_convo(ActionEvent event) {
        if (crud_bar.getText().compareTo("") != 0) {
            User u_tmp = su.getByUsername(crud_bar.getText());
            if (u_tmp != null) {
                String idc = String.valueOf(sm.findConvo(current_id, String.valueOf(u_tmp.getId())).getId_convo());
                sm.updateStatus(idc, current_id, "false");
                contacts.remove(crud_bar.getText());
                users.getItems().remove(crud_bar.getText());
                msg_source.getItems().clear();
                conversation.getItems().clear();
            }
        }
    }

    @FXML
    private void search_convo(ActionEvent event) {
        users.getItems().clear();
        aux.getItems().forEach(u -> {
            users.getItems().add(u);
        });
        List<String> listUsers = users.getItems();
        listUsers.stream().forEach(System.out::println);
        if (search_bar.getText().compareTo("") != 0) {
            List<String> filteredUsers = users.getItems().stream().filter(u -> u.toUpperCase().contains(search_bar.getText().toUpperCase())).collect(Collectors.toList());
            users.getItems().clear();
            users.getItems().forEach(System.out::println);
            filteredUsers.forEach(fu -> users.getItems().add(fu));
        }
    }

    public void addNotif(int i) {
        viewn.setId("n" + i);

        viewn.setTranslateX(220);
        viewn.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
        viewn.setPreserveRatio(true);
        viewn.setFitHeight(15);
        anchor_window.getChildren().add(viewn);
    }

    public void memes() {
    }

}

class db_buffer extends Thread {

    ServiceMessagerie sm = ServiceMessagerie.getInstance();
    private MessageService su = new MessageService();
    private String current_id;
    private List<String> last_m = new ArrayList();
    private String src;
    private List<String> users = new ArrayList();
    private ListView<String> c = new ListView();
    private ListView<String> m = new ListView();

    private Image notif = new Image(getClass().getResource("/resources/notif.png").toString(), true);
    private ImageView viewn = new ImageView(notif);

    int current_convo = 0;

    private AnchorPane anchor_window;

    public db_buffer(String id, List<String> last_m, List<String> c, ListView<String> conversation, ListView<String> source, int i, AnchorPane w) {
        this.current_id = id;
        this.last_m = last_m;
        this.src = "";
        this.users = c;
        this.c = conversation;
        this.m = source;
        this.current_convo = i;
        this.anchor_window = w;

        viewn.setFitHeight(15);
        viewn.setPreserveRatio(true);

    }

    public void set_last(List<String> last_m) {
        this.last_m = last_m;
    }

    public void set_last_indexed(String m, int i) {
        this.last_m.set(i, m);
    }

    public void set_src(String src) {
        this.src = src;
    }

    public void set_users(List<String> c) {
        this.users = c;
    }

    public void set_conversation(ListView<String> c) {
        this.c = c;
    }

    public void set_src(ListView<String> m) {
        this.m = m;
    }

    public void set_convo_index(int i) {
        this.current_convo = i;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            for (int i = 0; i < last_m.size(); i++) {
                if (last_m.get(i).compareTo("") != 0) {
                    Conversation temp = null;
                    int testt = 0;
                    try {
                        int val = Integer.parseInt(users.get(i));
                    } catch (NumberFormatException e) {
                        temp = sm.findConvo(current_id, String.valueOf(su.getByUsername(users.get(i)).getId()));
                        testt = 1;
                    }
                    if (testt == 0) {
                        temp = sm.findConvo(current_id, users.get(i));
                    }
                    if (last_m.get(i).compareTo(sm.getLastMessage(temp.getId_convo())) != 0) {
                        last_m.set(i, sm.getLastMessage(temp.getId_convo()));
                        if (i == current_convo) {
                            c.getItems().add(last_m.get(i));
                            m.getItems().add(su.getOneById(Integer.valueOf(sm.getLastSource(temp.getId_convo()))).getPrenom());
                        }
                        if (i != current_convo) {
                            System.out.println("different index" + i);

                            viewn.setId("n" + i);
                            viewn.setTranslateX(220);
                            viewn.setTranslateY(anchor_window.getChildren().get(0).getLayoutY() + (i * 30));
                            viewn.setPreserveRatio(true);
                            viewn.setFitHeight(15);
                            Platform.runLater(() -> anchor_window.getChildren().add(viewn));
                            //anchor_window.getChildren().remove(anchor_window.lookup("#n2"));
                        }

                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
