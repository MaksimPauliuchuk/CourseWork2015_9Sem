package com.findpath.services.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.findpath.interfaces.model.Bus;
import com.findpath.interfaces.model.BusesRoute;
import com.findpath.interfaces.model.Route;
import com.findpath.interfaces.model.TraficStop;

/**
 * DAOBusesRoutes.
 * 
 * @author Максим
 */
public final class DAOBusesRoutes
{
    private DAOBusesRoutes()
    {
    }

    /**
     * get TraficStop By Id.
     * 
     * @param anId id
     * @return TraficStop
     */
    public static TraficStop getTraficStopById(final int anId)
    {
        // System.out.println("getTraficStopById " + anId);
        TraficStop traficStop = null;

        String sql = "CALL `find_path`.`get_stop_by_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get list of TraficStop.
     * 
     * @return list of TraficStop
     */
    public static List<TraficStop> getTraficStops()
    {
        // System.out.println("getTraficStops");
        List<TraficStop> traficStops = new ArrayList<TraficStop>();

        String sql = "CALL `find_path`.`get_stops`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get route by id.
     * 
     * @param anId id route.
     * @return Route
     */
    public static Route getRouteById(final int anId)
    {
        // System.out.println("getRouteById " + anId);
        Route route = null;

        String sql = "CALL `find_path`.`get_rout_by_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get Routes.
     * 
     * @return List of Route.
     */
    public static List<Route> getRoutes()
    {
        // System.out.println("getRoutes");
        List<Route> routes = new ArrayList<Route>();

        String sql = "CALL `find_path`.`get_routes`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get Bus by id.
     * 
     * @param anId bus id
     * @return Bus
     */
    public static Bus getBusById(final int anId)
    {
        // System.out.println("getBusById " + anId);
        Bus bus = null;

        String sql = "CALL `find_path`.`get_bus_by_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get List of Buses.
     * 
     * @return List of Buses.
     */
    public static List<Bus> getBuses()
    {
        // System.out.println("getBuses");
        List<Bus> buses = new ArrayList<Bus>();

        String sql = "CALL `find_path`.`get_buses`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get List of BusesRoute.
     * 
     * @return list of BusesRoute
     */
    public static List<BusesRoute> getBusesRoutes()
    {
        // System.out.println("getBusesRoutes");
        List<BusesRoute> busesRoutes = new ArrayList<BusesRoute>();
        List<TraficStop> traficStops = getTraficStops();
        Map<Integer, String> traficStopsMap = new HashMap<Integer, String>();

        for (TraficStop traficStop : traficStops)
        {
            traficStopsMap.put(traficStop.getItsId(), traficStop.getItsName());
        }

        String sql = "CALL `find_path`.`get_buses_routes`()";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
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

    /**
     * Get BusesRoute by Bus id.
     * 
     * @param anId Bus id
     * @return BusesRoute
     */
    public static BusesRoute getBusesRouteByBusId(final int anId)
    {
        // System.out.println("getBusesRouteByBusId");
        BusesRoute busesRoute = null;
        List<TraficStop> traficStops = getTraficStops();
        Map<Integer, String> traficStopsMap = new HashMap<Integer, String>();

        for (TraficStop traficStop : traficStops)
        {
            traficStopsMap.put(traficStop.getItsId(), traficStop.getItsName());
        }

        String sql = "CALL `find_path`.`get_buses_route_by_bus_id`(?)";
        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, anId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            List<TraficStop> routeTraficStops = new ArrayList<TraficStop>();
            routeTraficStops.clear();
            String stops = resultSet.getString(2);
            String[] massStop = stops.split(";");
            for (int i = 0; i < massStop.length; i++)
            {
                int id = Integer.parseInt(massStop[i]);
                TraficStop traficStop = new TraficStop(id, traficStopsMap.get(id));
                routeTraficStops.add(traficStop);
            }
            busesRoute = new BusesRoute(resultSet.getInt(1), routeTraficStops, anId);
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getBusesRouteByBusId");
            e.printStackTrace();
        }
        return busesRoute;
    }

    /**
     * Get map of travels (Travel, Time).
     * 
     * @param aBusId bus id
     * @param aStopId stop id
     * @return map of travels (Travel, Time)
     */
    public static Map<Integer, Calendar> getTravelsByBusAndStop(final int aBusId, final int aStopId)
    {
        Map<Integer, Calendar> map = new HashMap<Integer, Calendar>();

        String sql = "CALL `find_path`.`get_travels_by_bus_and_stop`(?,?)";

        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, aBusId);
            statement.setInt(2, aStopId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(resultSet.getTime(2));
                map.put(resultSet.getInt(1), calendar);
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getTravelsByBusAndStop");
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Get time, when bus go to stop on concrete travel.
     * 
     * @param aBusId bus id
     * @param aStopId stop id
     * @param aTravelId travel id
     * @return time, when bus go to stop on travel.
     */
    public static Calendar getTimeFromBusOnStop(final int aBusId, final int aStopId, final int aTravelId)
    {
        Calendar calendar = new GregorianCalendar();
        String sql = "CALL `find_path`.`get_time_from_bus_on_stop`(?,?,?)";

        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, aBusId);
            statement.setInt(2, aStopId);
            statement.setInt(3, aTravelId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            calendar.setTime(resultSet.getTime(1));
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getTimeFromBusOnStop");
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Get stop number.
     * 
     * @param aStopName stop name
     * @return list of stop id
     */
    public static List<Integer> getStopsByName(final String aStopName)
    {
        List<Integer> list = new ArrayList<Integer>();
        String sql = "CALL `find_path`.`get_stops_by_name`(?)";

        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, aStopName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                list.add(resultSet.getInt(1));
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getStopsByName");
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Get list string of name stops.
     * 
     * @return list of name stops
     */
    public static List<String> getNameStops()
    {
        List<String> list = new ArrayList<String>();
        String sql = "CALL `find_path`.`get_name_stops`()";

        try (Connection connection = MySQLConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                list.add(resultSet.getString(1));
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL error in getNameStops");
            e.printStackTrace();
        }

        return list;
    }
}
