/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author haroun
 */
@ManagedBean(name="cart")
@SessionScoped
public class CartBean {

  private List<String> items;

  public CartBean() {
    items = new ArrayList<>();
    items.add("shirt");
    items.add("skirt");
    items.add("trouser");
  }

  public List<String> getItems() {
    return items;
  }

}