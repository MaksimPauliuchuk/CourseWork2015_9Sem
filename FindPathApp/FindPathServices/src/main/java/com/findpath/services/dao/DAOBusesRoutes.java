package com.findpath.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.findpath.interfaces.model.Bus;
import com.findpath.interfaces.model.BusesRoute;
import com.findpath.interfaces.model.Route;
import com.findpath.interfaces.model.TraficStop;

/**
 * DAOBusesRoutes.
 * 
 * @author Максим
 */
public class DAOBusesRoutes
{
    DAOBusesRoutes()
    {
    }

    /**
     * get TraficStop By Id.
     * 
     * @param anId id
     * @return TraficStop
     */
    public final static TraficStop getTraficStopById(final int anId)
    {
        TraficStop traficStop = null;

        String sql = "CALL `find_path`.`get_stop_by_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getTraficStopById " + anId);
            statement.setInt(1, anId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String aName = resultSet.getString(1);
            traficStop = new TraficStop(anId, aName);
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getTraficStopById " + anId);
            e.printStackTrace();
        }
        return traficStop;
    }

    public final static List<TraficStop> getTraficStops()
    {
        List<TraficStop> traficStops = new ArrayList<TraficStop>();

        String sql = "CALL `find_path`.`get_stops`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getTraficStops");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                traficStops.add(new TraficStop(resultSet.getInt(1), resultSet.getString(2)));
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getTraficStops");
            e.printStackTrace();
        }
        return traficStops;
    }

    public final static Route getRouteById(final int anId)
    {
        Route route = null;

        String sql = "CALL `find_path`.`get_rout_by_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getRouteById " + anId);
            statement.setInt(1, anId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String aName = resultSet.getString(1);
            route = new Route(anId, aName);
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getRouteById " + anId);
            e.printStackTrace();
        }
        return route;
    }

    public final static List<Route> getRoutes()
    {
        List<Route> routes = new ArrayList<Route>();

        String sql = "CALL `find_path`.`get_routes`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getRoutes");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                routes.add(new Route(resultSet.getInt(1), resultSet.getString(2)));
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getRoutes");
            e.printStackTrace();
        }
        return routes;
    }

    public final static Bus getBusById(final int anId)
    {
        Bus bus = null;

        String sql = "CALL `find_path`.`get_bus_by_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getBusById " + anId);
            statement.setInt(1, anId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String aName = resultSet.getString(1);
            int aRouteId = resultSet.getInt(2);
            bus = new Bus(anId, aName, aRouteId);
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getBusById " + anId);
            e.printStackTrace();
        }
        return bus;
    }

    public final static List<Bus> getBuses()
    {
        List<Bus> buses = new ArrayList<Bus>();

        String sql = "CALL `find_path`.`get_buses`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getBuses");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                buses.add(new Bus(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getBuses");
            e.printStackTrace();
        }
        return buses;
    }

    public final static List<BusesRoute> getBusesRoutes()
    {
        List<BusesRoute> busesRoutes = new ArrayList<BusesRoute>();
        List<TraficStop> traficStops = getTraficStops();
        Map<Integer,String> traficStopsMap = new HashMap<Integer, String>();

        for (TraficStop traficStop : traficStops)
        {
            traficStopsMap.put(traficStop.getItsId(), traficStop.getItsName());
        }

        String sql = "CALL `find_path`.`get_buses_routes`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            System.out.println("getBusesRoutes");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                List<TraficStop> routeTraficStops = new ArrayList<TraficStop>();
                routeTraficStops.clear();
                String stops = resultSet.getString(2);
                String[] massStop = stops.split(";");
                for (int i = 0; i < massStop.length; i++)
                {
                    int anId = Integer.parseInt(massStop[i]);
                    TraficStop traficStop = new TraficStop(anId, traficStopsMap.get(anId));
                    routeTraficStops.add(traficStop);
                }
                busesRoutes.add(new BusesRoute(resultSet.getInt(1), routeTraficStops, resultSet.getInt(3)));
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getBusesRoutes");
            e.printStackTrace();
        }
        return busesRoutes;
    }

}
