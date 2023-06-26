import java.util.ArrayList;
import java.util.List;

public class Shipper {
    public static final List<Shipper> SHIPPERS = new ArrayList<>();
    private static int counter = 1;

    private final int id;
    private final String name;
    private final String surname;
    private final List<IrresponsibleSenderWithDangerousGoods> irresponsibleSenderWithDangerousGoods = new ArrayList<>();

    public Shipper(String name, String surname) {
        this.id = counter++;
        this.name = name;
        this.surname = surname;

        SHIPPERS.add(this);
    }

    public void addWarning(String warning) {
        irresponsibleSenderWithDangerousGoods.add(new IrresponsibleSenderWithDangerousGoods(warning));
    }

    public long getId() {
        return id;
    }

    public List<IrresponsibleSenderWithDangerousGoods> getIrresponsibleSenderWithDangerousGoods() {
        return irresponsibleSenderWithDangerousGoods;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", name: " + name +
                ", surname: " + surname +
                ", warnings: " + irresponsibleSenderWithDangerousGoods.size();
    }
}
