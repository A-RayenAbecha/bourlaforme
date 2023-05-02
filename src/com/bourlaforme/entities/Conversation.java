/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bourlaforme.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ahmedsta
 */
public class Conversation {
    int id_convo;
    String id_source;
    String id_dest;
    boolean status_src = false;
    boolean status_dest = false;
    
    public Conversation(int idc, String ids, String idd, boolean stat){
        this.id_convo = idc;
        this.id_source = ids;
        this.id_dest = idd;
        this.status_src = stat;
    }
    
    public int get_id(){
        return id_convo;
    }

    public int getId_convo() {
        return id_convo;
    }

    public void setId_convo(int id_convo) {
        this.id_convo = id_convo;
    }

    public String getId_source() {
        return id_source;
    }

    public void setId_source(String id_source) {
        this.id_source = id_source;
    }

    public String getId_dest() {
        return id_dest;
    }

    public void setId_dest(String id_dest) {
        this.id_dest = id_dest;
    }
    
    public boolean get_status_src() {
        return status_src;
    }

    public void set_status_src(boolean status) {
        this.status_src = status;
    }
   
}
