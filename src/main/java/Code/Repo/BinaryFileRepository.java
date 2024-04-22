package Code.Repo;

import Code.Domain.Entity;

import java.io.*;
import java.util.List;

public class BinaryFileRepository<T extends Entity> extends MemoryRepository<T> {
    private String fileName;

    public BinaryFileRepository(String fileName) {
        this.fileName = fileName;
        try {
            loadFile();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("NU MERGE");
        }
    }


    @Override
    public void add(T o) throws RepositoryException {
        super.add(o);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving file " + e.getMessage());
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        super.remove(id);
    }

    private void loadFile() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            this.data = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Repo starting a new file");
        } finally {
            if (ois != null)
                ois.close();
        }


    }


    private void saveFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        }
    }

}