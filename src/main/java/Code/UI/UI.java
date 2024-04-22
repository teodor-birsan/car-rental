package Code.UI;


import Code.Domain.Car;
import Code.Domain.Entity;
import Code.Domain.Rent;
import Code.Repo.RepositoryException;
import Code.Service.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class UI {
    private final Service service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);


    public UI(Service service) {
        this.service = service;
    }
    public void runConsole(){
        String opt;
        do{
            menu();
            Scanner scan = new Scanner(System.in);
            opt = scan.nextLine();
            switch (opt){
                case "1":
                    add();
                    break;
                case "2":
                    remove();
                    break;
                case"3":
                    find();
                    break;
                case "4":
                    update();
                    break;
                case "5":
                    getAll();
                    break;
                case "6":
                    mostRented();
                    break;
                case "x":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Optiune invalida!\n");
                    break;
            }
        }while (true);
    }

    private void menu(){
        System.out.println("""
                \tMENIU
                1.Adauga o entitate.
                2.Sterge o entitate.
                3.Cauta o entitate.
                4.Actualizeaza o entitate.
                5.Afiseaza lista entitatilor.
                6.Afiseaza cele mai inchiriate masini.
                x-Inchide""");
    }
    private void mostRented(){
        Set<Car> set = service.mostRented();
        for(Car car: set){
            int nr = 0;
            Car rentedCar = new Car(car);
            while(set.contains(car)){
                nr++;
                set.remove(car);
            }
            if(nr != 0)
                System.out.printf(rentedCar + " a fost inchiriata de " + String.valueOf(nr) + " ori.\n");
        }
    }
    private Car readCar(){
        Scanner scan  = new Scanner(System.in);
        System.out.println("Id-ul masinii:\n");
        String carId = scan.nextLine();
        System.out.println("Marca:\n");
        String mark = scan.nextLine();
        System.out.println("Model:\n");
        String model = scan.nextLine();
        int id = 0;
        try {
            id = Integer.parseInt(carId);
        }
        catch (NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar.\n");
        }
        return new Car(id, mark, model);
    }
    private Rent readRent(){
        int carId;
        Scanner scan = new Scanner(System.in);
        System.out.println("Id-ul masinii: \n");
        try{
            carId = Integer.parseInt(scan.nextLine());
        }
        catch (NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar!\n");
            return null;
        }
        if(!(service.find(carId) instanceof Car car)){
            System.out.println("Id-ul introdus nu apartine unei masini din lista!\n");
            return null;
        }
        System.out.println("Id-ul inchirierii: \n");
        int id;
        try{
            id = Integer.parseInt(scan.nextLine());
        }
        catch (NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar!\n");
            return null;
        }
        System.out.println("Data inceput(dd MMM yyyy): \n");
        LocalDate date1;
        try {
            date1 = LocalDate.parse(scan.nextLine(), formatter);
        }catch (DateTimeParseException dtpe){
            System.out.println("Data este invalida!\n");
            return null;
        }
        Date startDate = Date.valueOf(date1);
        System.out.println("Data sfarsit(dd MMM yyyy): \n");
        LocalDate date2;
        try {
            date2 = LocalDate.parse(scan.nextLine(), formatter);
        }catch (DateTimeParseException dtpe){
            System.out.println("Data este invalida!\n");
            return null;
        }
        Date endDate = Date.valueOf(date2);
        return new Rent(id, car, startDate, endDate);
    }

    private void entityMenu(){
        System.out.println("""
                1.Masina
                2.Inchiriere.
                <Inapoi la meniul principal""");
    }

    private void addCar(){
        Car car = readCar();
        try{
            service.add(car);
        }
        catch (RepositoryException re){
            System.out.println(re.getMessage());
        }
    }
    private void addRent(){
        Rent rent = readRent();
        try{
            service.add(rent);
        }
        catch(RepositoryException re){
            System.out.println(re.getMessage());
        }
    }
    private void add(){
        String opt;
        do {
            entityMenu();
            Scanner scan = new Scanner(System.in);
            opt = scan.nextLine();
            switch (opt) {
                case "1":
                    addCar();
                    break;
                case "2":
                    addRent();
                    break;
                case "<":
                    break;
                default:
                    System.out.println("Optiune invalida");
                    break;
            }
        }while(!opt.equals("<"));
    }

    private void remove(){
        System.out.println("Stergeti dupa id sau dupa obiectul propriu-zis?(id/obicet)");
        Scanner scanner = new Scanner(System.in);
        String rmv = scanner.nextLine();
        if("id".equals(rmv)){
            removeEntityId();
        } else if ("obiect".equals(rmv)) {
            String opt;
            do{
                entityMenu();
                Scanner scan = new Scanner(System.in);
                opt = scan.nextLine();
                switch (opt){
                    case "1":
                        removeCar();
                        break;
                    case "2":
                        removeRent();
                        break;
                    case"<":
                        break;
                    default:
                        System.out.println("Optiune invalida!");
                        break;
                }
            }while(!opt.equals("<"));
        }
    }

    private void removeEntityId(){
        Scanner scan  = new Scanner(System.in);
        System.out.println("Introduceti id-ul: \n");
        int id = 0;
        try{
            id = Integer.parseInt(scan.nextLine());
        }catch(NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar!\n");
        }
        try{
            service.remove(id);
        }catch (RepositoryException re){
            System.out.println(re.getMessage());
        }
    }

    private void removeCar(){
        Car car = readCar();
        try{
            service.remove(car);
        }catch (RepositoryException re){
            System.out.println(re.getMessage());
        }
    }

    private void removeRent(){
        Rent rent = readRent();
        try{
            service.remove(rent);
        } catch (RepositoryException re){
            System.out.println(re.getMessage());
        }
    }

    private void update(){
        String opt;
        do {
            entityMenu();
            Scanner scan = new Scanner(System.in);
            opt = scan.nextLine();
            switch (opt) {
                case "1":
                    updateCar();
                    break;
                case "2":
                    updateRent();
                    break;
                case "<":
                    break;
                default:
                    System.out.println("Optiune invalida");
                    break;
            }
        }while(!opt.equals("<"));
    }

    private void updateCar(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduceti id-ul: \n");
        int id = 0;
        try{
            id = Integer.parseInt(scan.nextLine());
        }catch (NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar!\n");
        }
        if(service.find(id) instanceof Car){
            System.out.println("Masina actualizata: \n");
            Car car = readCar();
            try{
                service.update(id, car);
            }catch (RepositoryException re){
                System.out.println(re.getMessage());
            }
        }else {
            System.out.println("Obiectul nu este o masina!\n");
        }
    }

    private void updateRent(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduceti id-ul: \n");
        int id = 0;
        try{
            id = Integer.parseInt(scan.nextLine());
        }catch (NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar!\n");
        }
        if(service.find(id) instanceof Rent){
            System.out.println("Inchirierea actualizata: \n");
            Rent rent = readRent();
            try{
                service.update(id, rent);
            }catch (RepositoryException re){
                System.out.println(re.getMessage());
            }
        }else {
            System.out.println("Obiectul nu este o inchiriere!\n");
        }
    }


    private void find(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduceti id-ul: \n");
        int id = 0;
        try{
            id = Integer.parseInt(scan.nextLine());
        }catch (NumberFormatException nfe){
            System.out.println("Id-ul trebuie sa fie un numar!\n");
        }
        if(service.find(id) != null){
            System.out.println(service.find(id));
        }
        else System.out.println("Obiectul nu exista!\n");
    }

    private void getAll(){
        String opt;
        do {
            System.out.println("""
                    1.Masina
                    2.Inchiriere.
                    3.Toate.
                    <Inapoi la meniul principal""");
            Scanner scan = new Scanner(System.in);
            opt = scan.nextLine();
            switch (opt) {
                case "1":
                    getCars();
                    break;
                case "2":
                    getRents();
                    break;
                case "3":
                    allEnt();
                    break;
                case "<":
                    break;
                default:
                    System.out.println("Optiune invalida");
                    break;
            }
        }while(!opt.equals("<"));
    }
    private void getCars(){
        for(Car car: service.getAllCars()){
                System.out.println(car + "\n");
        }
    }

    private void getRents(){
        for(Rent rent: service.getAllRents()){
                System.out.println(rent + "\n");
        }
    }

    private void allEnt(){
        for(Entity ent: service.getAll()){
            if(ent instanceof Car)
                System.out.println("Car: " + ent + "\n");
            if(ent instanceof Rent)
                System.out.println("Rent: " + ent + "\n");
        }
    }
}



