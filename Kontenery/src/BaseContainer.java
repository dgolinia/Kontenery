public class BaseContainer extends Container {

    BaseContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons) {
        super(shipper, massNettoInTons, massBruttoInTons);
    }

    @Override
    public String toString() {
        return "BaseContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto();
    }
}
