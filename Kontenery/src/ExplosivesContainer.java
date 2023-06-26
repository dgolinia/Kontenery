public class ExplosivesContainer extends HeavyContainer {

    public ExplosivesContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, Boolean over100Tons) {
        super(shipper, massNettoInTons, massBruttoInTons, over100Tons);
    }

    @Override
    public String toString() {
        return "ExplosiveContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto();
    }
}
