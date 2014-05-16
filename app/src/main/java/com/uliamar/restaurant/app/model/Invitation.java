package com.uliamar.restaurant.app.model;

import java.util.List;

/**
 * Created by Yamazoa on 2014/5/13.
 */
public class Invitation {
    private int inv_id;
    private String created_time;
    private List<User>  participants;
    private String pic = "";

    private Order order;

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getiID() {
        return inv_id;
    }

}
