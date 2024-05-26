package org.example.agentvanzari.repository;

import org.example.agentvanzari.domain.Entity;
import org.example.agentvanzari.domain.EntityConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class TextFileRepository<T extends Entity> extends MemoryRepository<T>
{
    private String fileName;

    private EntityConverter<T> converter;

    public TextFileRepository(String fileName, EntityConverter<T> converter)
    {
        this.fileName = fileName;
        this.converter = converter;
        try
        {
            loadFile();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(T o) throws RepositoryException {
        super.add(o);
        try
        {
            saveFile();
        } catch (IOException e)
        {
            throw new RepositoryException("Error saving object", e);
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        super.remove(id);
        try
        {
            saveFile();
        } catch (IOException e)
        {
            throw new RepositoryException("Error saving object", e);
        }
    }

    @Override
    public void update(int id, T o) throws RepositoryException {
        super.update(id, o);
        try
        {
            saveFile();
        } catch (IOException e)
        {
            throw new RepositoryException("Error saving object", e);
        }
    }

    @Override
    public T find(int id) {
        return super.find(id);
    }

    @Override
    public Collection<T> getAll() {
        return super.getAll();
    }

    @Override
    public int getSize() {
        return super.getSize();
    }

    @Override
    public T getById(int id) throws RepositoryException {
        return super.getById(id);
    }

    private void saveFile() throws IOException
    {
        try (FileWriter fw = new FileWriter(this.fileName))
        {
            for (T object : this.data)
            {
                fw.write(this.converter.toString(object));
                fw.write("\r\n");
            }
        }
    }

    private void loadFile() throws IOException
    {
        this.data.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName)))
        {
            String line = br.readLine();
            while (line != null && !line.isEmpty())
            {
                this.data.add(this.converter.fromString(line));
                line = br.readLine();
            }
        }
    }

}