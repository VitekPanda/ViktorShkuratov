package viktorsever.viktorshkuratov.model;

public class Stock {
    private String name;
    private String volume;
    private String amount;

    public Stock(String name, String volume, String amount) {
        this.name = name;
        this.volume = volume;
        this.amount = amount;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume (String volume) {
        this.volume = volume;
    }
}
