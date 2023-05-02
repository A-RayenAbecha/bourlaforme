/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bourlaforme.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Firas
 */
public interface IService <T>{
    public void ajouter(T p);
    public void supprimer(int id);
    public void supprimer(T t);
    public void modifier(T p);
    public List<T> afficher();
    public List<T> getAll();
    public T getOneById(int id);
}