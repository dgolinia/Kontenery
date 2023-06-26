import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Util {

    private static final String SHIP_PATH = "ships.txt";
    private static final String WAREHOUSE_PATH = "warehouses.txt";
    private static final String SHIPPER_PATH = "shippers.txt";
    private static final String DATE_PATH = "date.txt";

    public static void saveData() {
        try {
            BufferedWriter shipWriter = new BufferedWriter(new FileWriter(SHIP_PATH));
            List<Ship> sorted = Ship.SHIPS.stream()
                    .sorted(Comparator.comparing(Ship::getName))
                    .toList();
            sorted.forEach(ship -> {
                try {
                    shipWriter.write(ship + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            shipWriter.close();


            BufferedWriter wareHouseWriter = new BufferedWriter(new FileWriter(WAREHOUSE_PATH));
            Warehouse.WAREHOUSES.forEach(warehouse -> {
                try {
                    wareHouseWriter.write(warehouse.printSaver() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            wareHouseWriter.close();


            BufferedWriter shipperWriter = new BufferedWriter(new FileWriter(SHIPPER_PATH));
            Shipper.SHIPPERS.forEach(shipper -> {
                try {
                    shipperWriter.write(shipper + "\n" + Util.printList(shipper.getIrresponsibleSenderWithDangerousGoods()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            shipperWriter.close();

            BufferedWriter dateWriter = new BufferedWriter(new FileWriter(DATE_PATH));
            try {
                dateWriter.write(Threads.getDate().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dateWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static double readDoubleFromUser() {
        Scanner scanner = new Scanner(System.in);
        double number;
        do {
            number = scanner.nextDouble();
        } while (number <= 0);
        return number;
    }

    static <T> String printList(List<T> list) {
        StringBuilder toReturn = new StringBuilder();
        for (T t : list) {
            toReturn.append(t.toString()).append("\n");
        }
        return toReturn.toString();
    }
}
