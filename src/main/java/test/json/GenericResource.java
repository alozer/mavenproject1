/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.json;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.sql.ResultSet;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

/**
 * REST Web Service
 *
 * @author ali
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;
    private Connection connection; 
    private ConnectionHelper connectionHelper;
    private DbOperations dbOperations;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of test.json.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json; charset=UTF-8")
    public String getJson(@DefaultValue("value1") @QueryParam("value") String value) throws Exception{
    connectionHelper = new ConnectionHelper();
    connection = connectionHelper.getConnection();
    dbOperations = new DbOperations();
    
    JSONObject teamsObj = new JSONObject();
    JSONArray teamsArray = new JSONArray();
    
    //obj.put("teams", "mkyong.com");
    //obj.put("name", "mkyong.com");
    ResultSet rs = dbOperations.getTeams(connection);
    try {
        while(rs.next())
        {
            JSONObject teamObj = new JSONObject();
            teamObj.put("id", rs.getInt(1));
            teamObj.put("name", rs.getString(2));
            teamsArray.add(teamObj);
            //returnValue += rs.getInt(1) + "," + rs.getString(2) + "\n";
        }
        
        teamsObj.put("teams", teamsArray);

        
    } catch(Exception e)
    {
        throw e;
    }
        connectionHelper.closeConnection(connection);
        
        return teamsObj.toString();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json; charset=UTF-8")
    public void putXml(String content) {
    }
}
