package Repo;

public class RepoException extends Exception{
    String m;
    public RepoException(String message){super(message);m=message;}
    public String toString(){return m;}
}
