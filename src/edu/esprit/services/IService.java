/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Reponse;
import static java.nio.file.Files.list;
import static java.util.Collections.list;
import java.util.List;

/**
 *
 * @author HP
 */
public interface IService <T>{
    public void ajouter (T t);
    public void supprimer (int idr);
    public void modifier (T t);
    public  List<T> getAll();
    
}
