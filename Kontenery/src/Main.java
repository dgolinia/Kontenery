import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Threads().start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 print ships");
            System.out.println("2 print ships containers");
            System.out.println("3 create new ship");
            System.out.println("4 load ship");
            System.out.println("5 unload ship");
            System.out.println("6 set sail");
            System.out.println("7 view shippers");
            System.out.println("8 create shipper");
            System.out.println("9 create container");
            System.out.println("10 create warehouse");
            System.out.println("11 view warehouse");
            System.out.println("12 put container in the warehouse");
            System.out.println("13 take the container out of the warehouse");
            System.out.println("0 - Save and exit");
            System.out.print("Select choice: ");
            int choice = scanner.nextInt();
            System.out.println();
            switch (choice) {
                case 1 -> Ship.SHIPS.forEach(System.out::println);
                case 2 -> {
                    System.out.println("Ship id:");
                    int shipId = new Scanner(System.in).nextInt();
                    Optional<Ship> founded = Ship.SHIPS.stream()
                            .filter(ship -> ship.getId() == shipId)
                            .findAny();
                    if (founded.isPresent()) {
                        founded.get().shipContainers.forEach(System.out::println);
                    } else {
                        System.out.println("There is no ship with this id!");
                    }
                }
                case 3 -> {
                    System.out.println("maximumQuantityOfToxicAndExplosiveContainers: ");
                    int maximumQuantityOfToxicAndExplosiveContainers = (int) Util.readDoubleFromUser();
                    System.out.println("maximumQuantityOfHeavyContainers: ");
                    int maximumQuantityOfHeavyContainers = (int) Util.readDoubleFromUser();
                    System.out.println("maximumQuantityOfRefrigeratedContainers: ");
                    int maximumQuantityOfRefrigeratedContainers = (int) Util.readDoubleFromUser();
                    System.out.println("maximumQuantityOfContainers: ");
                    int maximumQuantityOfContainers = (int) Util.readDoubleFromUser();
                    System.out.println("maximumDisplacement: ");
                    double maximumDisplacement = Util.readDoubleFromUser();
                    scanner = new Scanner(System.in);
                    System.out.println("name:");
                    String name = scanner.nextLine();
                    System.out.println("port:");
                    String port = scanner.nextLine();
                    System.out.println("sourceLocation:");
                    String sourceLocation = scanner.nextLine();
                    System.out.println("transportDestination:");
                    String transportDestination = scanner.nextLine();

                    new Ship(name, port, sourceLocation, transportDestination, maximumQuantityOfToxicAndExplosiveContainers, maximumQuantityOfHeavyContainers, maximumQuantityOfRefrigeratedContainers, maximumQuantityOfContainers, maximumDisplacement);

                    System.out.println("Ship created");
                }
                case 4 -> {
                    System.out.println("Ship id: ");
                    int shipId = new Scanner(System.in).nextInt();
                    Optional<Ship> founded = Ship.SHIPS.stream()
                            .filter(ship -> ship.getId() == shipId)
                            .findAny();
                    if (founded.isPresent()) {
                        Ship.findContainer(founded.get());
                    } else {
                        System.out.println("There is no ship with this id!");
                    }
                }
                case 5 -> {
                    System.out.println("Ship id: ");
                    Scanner in = new Scanner(System.in);
                    int shipId = in.nextInt();
                    Optional<Ship> founded = Ship.SHIPS.stream()
                            .filter(ship -> ship.getId() == shipId)
                            .findAny();
                    if (founded.isPresent()) {
                        founded.get().unloadShip();
                    } else {
                        System.out.println("There is no ship with this id!");
                    }
                }
                case 6 -> {
                    System.out.println("Which ship do you wanna set sail: ");
                    int shipId = new Scanner(System.in).nextInt();
                    Optional<Ship> founded = Ship.SHIPS.stream()
                            .filter(ship -> ship.getId() == shipId)
                            .findAny();
                    if (founded.isPresent()) {
                        founded.get().setSail();
                    } else {
                        System.out.println("There is no ship with this id!");
                    }
                }
                case 7 -> Shipper.SHIPPERS.forEach(System.out::println);
                case 8 -> {
                    scanner = new Scanner(System.in);
                    System.out.println("name: ");
                    String name = scanner.nextLine();
                    System.out.println("surname: ");
                    String surname = scanner.nextLine();

                    new Shipper(name, surname);
                    System.out.println("Shipper created");
                }
                case 9 -> Container.startCreatingContainer();
                case 10 -> {
                    System.out.println("maximumQuantityOfContainers: ");
                    int maximumQuantityOfContainers = (int) Util.readDoubleFromUser();
                    new Warehouse(maximumQuantityOfContainers);
                    System.out.println("Warehouse created");
                }
                case 11 -> Warehouse.WAREHOUSES.forEach(System.out::println);
                case 12 -> Warehouse.startPuttingContainer();
                case 13 -> {
                    System.out.println("From which warehouse do you want to take out the container: ");
                    int warehouseId = new Scanner(System.in).nextInt();
                    final Optional<Warehouse> founded = Warehouse.WAREHOUSES.stream()
                            .filter(warehouse -> warehouse.getId() == warehouseId)
                            .findAny();
                    if (founded.isPresent()) {
                        founded.get().takeOutContainer();
                    } else {
                        System.out.println("There is not such a warehouse");
                    }
                }
                case 0 -> {
                    Util.saveData();
                    System.exit(0);
                }
                default -> System.out.println("Wrong input!");
            }
        }
    }
}
