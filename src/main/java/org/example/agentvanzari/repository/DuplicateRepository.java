package org.example.agentvanzari.repository;

public class DuplicateRepository extends RepositoryException
{
    public DuplicateRepository(String message) {
        super(message);
    }

}
