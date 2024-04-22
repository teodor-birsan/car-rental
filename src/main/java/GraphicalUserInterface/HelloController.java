package GraphicalUserInterface;

import Code.Domain.Car;
import Code.Domain.Entity;
import Code.Domain.Rent;
import Code.Repo.RepositoryException;
import Code.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {
    public ObservableList<Entity> entities = FXCollections.observableList(new ArrayList<>());
    public ListView<Entity> entityListView = new ListView<>(entities);
    private Service service;
    public void setService(Service service){
        this.service = service;
    }
    public TextField carId = null;
    public TextField carBrand = null;
    public TextField carModel= null;
    public TextField rentId = null;
    public TextField carId2 = null;
    public DatePicker startDate = new DatePicker(LocalDate.now());
    public DatePicker endDate = new DatePicker(LocalDate.now());;
    public Button addCar;
    public Button addRent;
    public TextField deleteEntityText;
    public Button deleteEntity;
    public TextField findEntityText;
    public Button findEntity;
    public TextField updateCarId;
    public TextField updateCarBrand;
    public TextField updateCarModel;
    public Button updateCar;
    public TextField updateRentId;
    public TextField newCarId;
    public DatePicker newStart;
    public DatePicker newEnd;
    public Button updateRent;
    public void updateRentBtn(){
        int id = 0;
        try{
            id = Integer.parseInt(updateRentId.getText());
        }catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        int carId = 0;
        try{
            carId = Integer.parseInt(newCarId.getText());
        }catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        Date start = Date.valueOf(newStart.getValue());
        Date end = Date.valueOf(newEnd.getValue());
        try{
            entities.set(entities.indexOf(service.find(id)), new Rent(id, (Car)service.find(carId), start, end));
            service.update(id, new Rent(id, (Car)service.find(carId), start, end));
        }catch (RepositoryException re){
            Alert updateError = new Alert(Alert.AlertType.ERROR, re.getMessage());
            updateError.show();
            return;
        }
        Alert success = new Alert(Alert.AlertType.CONFIRMATION, "The rent was updated successfully!\n");
        success.show();
    }
    public void updateCarBtn(ActionEvent actionEvent){
        int id = 0;
        try{
            id = Integer.parseInt(updateCarId.getText());
        }catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        String newBrand = updateCarBrand.getText();
        String newModel = updateCarModel.getText();

        try{
            entities.set(entities.indexOf(service.find(id)), new Car(id, newBrand, newModel));
            service.update(id, new Car(id, newBrand, newModel));
        }catch (RepositoryException re){
            Alert updateError = new Alert(Alert.AlertType.ERROR, re.getMessage());
            updateError.show();
            return;
        }
        Alert success = new Alert(Alert.AlertType.CONFIRMATION, "The car was updated successfully!");
        success.show();
    }
    public void findEntityBtn(){
        int id = 0;
        try{
            id = Integer.parseInt(findEntityText.getText());
        }catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        if(service.find(id) != null){
            Alert success = new Alert(Alert.AlertType.CONFIRMATION, "The entity you were looking for is " + service.find(id));
            success.show();
        }
        else{
            Alert fail = new Alert(Alert.AlertType.ERROR, "The entity was not found!\n");
            fail.show();
        }
    }
    public void deleteEntityBtn(ActionEvent actionEvent){
        int id = 0;
        try{
            id = Integer.parseInt(deleteEntityText.getText());
        }
        catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        Entity entity = service.find(id);
        try{
            service.remove(id);
            entities.remove(entity);
        }catch (RepositoryException re){
            Alert addError = new Alert(Alert.AlertType.ERROR, re.getMessage());
            addError.show();
            return;
        }
        Alert success = new Alert(Alert.AlertType.CONFIRMATION, "The entity was successfully deleted!\n");
        success.show();
    }
    public void loadEntities(){
        entityListView.setItems(entities);
        entities.addAll(service.getAll());
    }
    public void addCarBtn(ActionEvent actionEvent){
        int id = 0;
        try{
            id = Integer.parseInt(carId.getText());
        }catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        String brand = carBrand.getText();
        String model = carModel.getText();
        Car car = new Car(id, brand, model);
        try{
            service.add(car);
            entities.add(car);
        }catch (RepositoryException re){
            Alert addError = new Alert(Alert.AlertType.ERROR, re.getMessage());
            addError.show();
            return;
        }
        Alert success = new Alert(Alert.AlertType.CONFIRMATION, "The car was added successfully!\n");
        success.show();
    }

    public void addRentBtn(ActionEvent actionEvent){
        int rntId = 0;
        try{
            rntId = Integer.parseInt(rentId.getText());
        } catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        int idCar = 0;
        try{
            idCar = Integer.parseInt(carId2.getText());
        } catch (NumberFormatException nfe){
            Alert idError = new Alert(Alert.AlertType.ERROR, "The id must be a positive number!\n");
            idError.show();
            return;
        }
        Date start = Date.valueOf(startDate.getValue());
        Date end = Date.valueOf(endDate.getValue());
        Rent rent = new Rent(rntId, (Car)service.find(idCar), start, end);
        try{
            service.add(rent);
            entities.add(rent);
        }catch (RepositoryException re){
            Alert addError = new Alert(Alert.AlertType.ERROR, re.getMessage());
            addError.show();
            return;
        }
        Alert success = new Alert(Alert.AlertType.CONFIRMATION, "The car was rented successfully!\n");
        success.show();

    }


}