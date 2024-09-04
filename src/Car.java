import java.util.*;


public class Car {
    //Atriibute
    private String Car_ID;
    private String Make;
    private String Model;
    private Date Year;
    private int Mileage;
    private String Color;
    private Boolean Status;
    private int Price;
    private String Additional_notes;

    public Car(){
        this.Car_ID = null;
        this.Make = null;
        this.Model = null;
        this.Year = null;
        this.Mileage = 0;
        this.Color = null;
        this.Status = true;
        this.Price = 0;
        this.Additional_notes = null;
    }

    public Car (String Make, String Model, Date Year, int Mileage, String Color,
                Boolean Status, int Price, String Additional_notes) {
        this.Car_ID = generateCarID();
        this.Make = Make;
        this.Model = Model;
        this.Year = Year;
        this.Mileage = Mileage;
        this.Color = Color;
        this.Status = Status;
        this.Price = Price;
        this.Additional_notes = Additional_notes;
    }

    // Generate a unique car ID (c-number format)
    private String generateCarID() {
        int number = (int) (Math.random() * 10000);
        return "C-" + number;
    }

    //Getters and Setters'

    public String getCar_ID() {
        return Car_ID;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public Date getYear() {
        return Year;
    }

    public void setYear(Date year) {
        Year = year;
    }


    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int mileage) {
        Mileage = mileage;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getAdditional_notes() {
        return Additional_notes;
    }

    public void setAdditional_notes(String additional_notes) {
        Additional_notes = additional_notes;
    }

    // toString method to display part information
    @Override
    public String toString() {
        return "Car{" +
                "Car_ID='" + Car_ID + '\'' +
                ", Make='" + Make + '\'' +
                ", Model='" + Model + '\'' +
                ", Year='" + Year + '\'' +
                ", Mileage='" + Mileage + '\'' +
                ", Color='" + Color + '\'' +
                ", Status=" + Status +
                ", Price=" + Price +
                ", Additional_notes='" + Additional_notes + '\'' +
                '}';
    }

}


