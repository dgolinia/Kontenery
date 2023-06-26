public class FluidContainer extends BaseContainer {

    private final String fluid;

    public FluidContainer(Shipper shipper, double massNettoInTons, double massBruttoInTons, String fluid) {
        super(shipper, massNettoInTons, massBruttoInTons);
        this.fluid = fluid;
    }

    String getFluid() {
        return fluid;
    }

    @Override
    public String toString() {
        return "FluidContainer - " +
                "shipper: [" + getShipper() +
                "], massNettoInTons: " + getWeightNetto() +
                ", massBruttoInTons: " + getWeightBrutto() +
                "], fluid: " + getFluid();
    }
}
