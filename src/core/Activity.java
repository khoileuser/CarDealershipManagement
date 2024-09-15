package core;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity implements Serializable {
    @Serial
    private static final long serialVersionUID = 12L;

    private final Date activityDate;
    private String operation; // add, update or delete
    private Entity entity;
    private Entity updatedEntity;

    // Constructor
    public Activity(String operation, Entity entity, Entity updatedEntity) {
        this.activityDate = setActivityDate();
        this.operation = operation;
        this.entity = entity;
        if (operation.equals("update")) {
            this.updatedEntity = updatedEntity;
        }
    }

    // Getters and Setters
    public Date getActivityDate() {
        return activityDate;
    }

    public String getStringActivityDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(activityDate);
    }

    public Date setActivityDate() {
        return new Date();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getUpdatedEntity() {
        return updatedEntity;
    }

    public void setUpdatedEntity(Entity updatedEntity) {
        this.updatedEntity = updatedEntity;
    }

    // toString
    @Override
    public String toString() {
        return "Activity{" +
                "activityDate=" + activityDate +
                ", operation='" + operation + '\'' +
                ", entity=" + entity +
                '}';
    }
}
