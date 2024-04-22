package Code.Repo;


import Code.Domain.Entity;
import Code.Domain.EntityConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileRepository<T extends Entity> extends MemoryRepository<T> {

    private String fileName;

    private EntityConverter<T> converter;

    public TextFileRepository(String fileName, EntityConverter<T> converter) {
        this.fileName = fileName;
        this.converter = converter;

        try {
            loadFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(T o) throws RepositoryException {
        super.add(o);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving object");
        }
    }

    private void saveFile() throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (T object : data) {
                fw.write(converter.toString(object));
                fw.write("\r\n");
            }
        }
    }

    private void loadFile() throws IOException {
        data.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                data.add(converter.fromString(line));
                line = br.readLine();
            }
        }
    }
    public void remove(int id) throws RepositoryException{
        super.remove(id);
        try{
            saveFile();
        }
        catch (IOException ie){
            throw new RepositoryException("Error deleting object!");
        }
    }
    public void update(int id, T newObj) throws RepositoryException{
        super.update(id, newObj);
        try{
            saveFile();
        }
        catch (IOException ie){
            throw new RepositoryException("Error updating object!");
        }
    }
}
