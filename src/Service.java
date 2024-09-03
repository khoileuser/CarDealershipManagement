import java.util.*;

public class Service {
    //Atribute
    private int service_ID;
    private Date service_date;
    private int client_ID;
    private int mechanic_ID;
    private String service_type;
    private AutoPart[] replaced_parts;
    private long service_cost;
    private String additional_note;

    public Service(){
        this.service_ID = 0;
        this.service_date = null;
        this.client_ID = 0;
        this.mechanic_ID = 0;
        this.service_type = "Default";
        this.replaced_parts = null;
        this.service_cost = 0;
        this.additional_note = "Default";
    }

    public Service(int service_ID, Date service_date, int client_ID, int mechanic_ID, String service_type, AutoPart[] replaced_parts, long service_cost, String additional_note) {
        this.service_ID = service_ID;
        this.service_date = service_date;
        this.client_ID = client_ID;
        this.mechanic_ID = mechanic_ID;
        this.service_type = service_type;
        this.replaced_parts = replaced_parts;
        this.service_cost = service_cost;
        this.additional_note = additional_note;
    }

    public Date getService_date() {
        return service_date;
    }

    public void setService_date(Date service_date) {
        this.service_date = service_date;
    }

    public int getService_ID() {
        return service_ID;
    }

    public void setService_ID(int service_ID) {
        this.service_ID = service_ID;
    }

    public int getClient_ID() {
        return client_ID;
    }

    public void setClient_ID(int client_ID) {
        this.client_ID = client_ID;
    }

    public int getMechanic_ID() {
        return mechanic_ID;
    }

    public void setMechanic_ID(int mechanic_ID) {
        this.mechanic_ID = mechanic_ID;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public AutoPart[] getReplaced_parts() {
        return replaced_parts;
    }

    public void setReplaced_parts(AutoPart[] replaced_parts) {
        this.replaced_parts = replaced_parts;
    }

    public long getService_cost() {
        return service_cost;
    }

    public void setService_cost(long service_cost) {
        this.service_cost = service_cost;
    }

    public String getAdditional_note() {
        return additional_note;
    }

    public void setAdditional_note(String additional_note) {
        this.additional_note = additional_note;
    }

    @Override
    public String toString() {
        return "Service{" +
                "service_ID=" + service_ID +
                ", service_date=" + service_date +
                ", client_ID=" + client_ID +
                ", mechanic_ID=" + mechanic_ID +
                ", service_type='" + service_type + '\'' +
                ", replaced_parts=" + Arrays.toString(replaced_parts) +
                ", service_cost=" + service_cost +
                ", additional_note='" + additional_note + '\'' +
                '}';
    }
}
