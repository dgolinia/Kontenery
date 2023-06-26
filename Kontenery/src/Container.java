import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class Container {
    public static final List<Container> CONTAINERS = new ArrayList<>();
    private static int counter = 1;

    private final int id;
    private final Shipper shipper;
    private final double weightNetto;
    private final double weightBrutto;
    private LocalDate date;
    private boolean loaded;
    private Warehouse warehouse;

    Container(Shipper shipper, double weightNetto, double weightBrutto) {
        this.id = counter++;
        this.shipper = shipper;
        this.weightNetto = weightNetto;
        this.weightBrutto = weightBrutto;
        this.loaded = false;
        this.warehouse = null;

        CONTAINERS.add(this);
    }

    static void startCreatingContainer() {
        System.out.println("What type of container do you want to create: ");
        System.out.println("1 base");
        System.out.println("2 heavy");
        System.out.println("3 refrigerated");
        System.out.println("4 fluid");
        System.out.println("5 explosive");
        System.out.println("6 fluid toxins");
        System.out.println("7 solid toxins");
        int caseEightChoice = new Scanner(System.in).nextInt();
        System.out.println("massNettoInTons: ");
        double massNettoInTons = Util.readDoubleFromUser();
        System.out.println("massBruttoInTons: ");
        double massBruttoInTons = Util.readDoubleFromUser();
        System.out.println("shipper (enter id): ");
        int shipperId = new Scanner(System.in).nextInt();
        Optional<Shipper> founded = Shipper.SHIPPERS.stream()
                .filter(shipper -> shipper.getId() == shipperId)
                .findAny();
        if (founded.isEmpty()) {
            System.out.println("There is no such shipper");
            return;
        }
        Shipper shipper = founded.get();
        boolean created = true;
        Container container = null;
        switch (caseEightChoice) {
            case 1 -> {
                container = new BaseContainer(shipper, massNettoInTons, massBruttoInTons);
            }
            case 2 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("over 100 tons (true/false):");
                String over100Tons = scanner.nextLine();
                container = new HeavyContainer(shipper, massNettoInTons, massBruttoInTons, Boolean.parseBoolean(over100Tons));
            }
            case 3 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("over 100 tons (true/false):");
                String over100Tons = scanner.nextLine();
                System.out.println("Temperature (in Celsius):");
                Double temparature = Util.readDoubleFromUser();
                container = new RefrigeratedContainer(shipper, massNettoInTons, massBruttoInTons, Boolean.parseBoolean(over100Tons), temparature);
            }
            case 4 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("fluid name:");
                String fluidName = scanner.nextLine();

                container = new FluidContainer(shipper, massNettoInTons, massBruttoInTons, fluidName);
            }
            case 5 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("over 100 tons (true/false):");
                String over100Tons = scanner.nextLine();
                container = new ExplosivesContainer(shipper, massNettoInTons, massBruttoInTons, Boolean.parseBoolean(over100Tons));
            }
            case 6 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("fluid name:");
                String fluidName = scanner.nextLine();
                scanner = new Scanner(System.in);
                System.out.println("volume:");
                String volume = scanner.nextLine();
                scanner = new Scanner(System.in);
                System.out.println("over 100 tons (true/false):");
                String over100Tons = scanner.nextLine();
                container = new FluidToxinsContainer(shipper, massNettoInTons, massBruttoInTons, fluidName, volume, Boolean.parseBoolean(over100Tons));
            }
            case 7 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("over 100 tons (true/false):");
                String over100Tons = scanner.nextLine();
                container = new SolidToxinsContainer(shipper, massNettoInTons, massBruttoInTons, Boolean.parseBoolean(over100Tons));
            }
            default -> {
                System.out.println("There is not such a choice option");
                created = false;
            }
        }
        if (created) {
            loadContainer(container);
        }
    }

    static void loadContainer(Container container) {
        System.out.println("Where to load: ");
        System.out.println("1 warehouse");
        System.out.println("2 ship");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Which warehouseId: ");
                int warehouseId = scanner.nextInt();
                final Optional<Warehouse> founded = Warehouse.WAREHOUSES.stream()
                        .filter(warehouse -> warehouse.getId() == warehouseId)
                        .findAny();
                if (founded.isPresent()) {
                    founded.get().putContainer(container);
                } else {
                    System.out.println("There is no such warehouse");
                }
            }
            case 2 -> {
                System.out.println("Which shipId ");
                int shipId = scanner.nextInt();
                Optional<Ship> founded = Ship.SHIPS.stream()
                        .filter(ship -> ship.getId() == shipId)
                        .findAny();
                if (founded.isPresent()) {
                    founded.get().loadShip(container);
                } else {
                    System.out.println("There is not such a ship");
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public double getWeightNetto() {
        return weightNetto;
    }

    public double getWeightBrutto() {
        return weightBrutto;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Shipper getShipper() {
        return shipper;
    }
}
