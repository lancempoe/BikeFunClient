package com.googlecode.gwtphonegap.showcase.client.model;

/**
 * Created with IntelliJ IDEA.
 * User: nathanfrost
 * Date: 3/16/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResult extends PGModule{
    String time;
    String location;
    public SearchResult(String name, String time, String location)
    {
        super(name);
        this.time = time;
        this.location = location;
    }
    public String getTime()
    {
        return time;
    }
    public String getLocation()
    {
        return location;
    }
}
