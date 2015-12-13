package com.findpath.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.omg.CORBA.TRANSACTION_MODE;

import com.findpath.interfaces.model.Bus;
import com.findpath.interfaces.model.BusesRoute;
import com.findpath.interfaces.model.Route;
import com.findpath.interfaces.model.TraficStop;
import com.findpath.interfaces.model.Transfer;
import com.findpath.services.dao.DAOBusesRoutes;

/**
 * Test.
 * 
 * @author Максим
 */
@ManagedBean(name = "indexB")
public class IndexBean
{
    private List<List<Set<Integer>>> itsStopsMatrix;
    private Map<Integer, TraficStop> itsTraficStop;
    private Map<Integer, Bus> itsBuses;
    private Map<Integer, Route> itsRoutes;

    private String itsTest;

    /**
     * Default.
     */
    public IndexBean() throws IOException
    {
        generateStopMatrix();
        generateMapOfTrafficstop();
        generateMapOfBuses();
        generateMapOfRoutes();
        setItsTest("asd");

        for (Transfer transfer : fidnPathWithoutTransfer(1, 23))
        {
            System.out.println(transfer.getItsNameFrom() + " " + transfer.getItsNameTo() + " "
                    + transfer.getItsNameBus() + " " + transfer.getItsNameRoute());
        }

        for (List<Transfer> list : findPathWithOneTransfer(1, 23))
        {
            for (Transfer transfer : list)
            {
                System.out.println(transfer.getItsNameFrom() + " " + transfer.getItsNameTo() + " "
                        + transfer.getItsNameBus() + " " + transfer.getItsNameRoute());
            }
            System.out.println();
        }

        for (List<Transfer> list : findPathWithTwoTransfer(1, 23))
        {
            for (Transfer transfer : list)
            {
                System.out.println(
                        transfer.getItsIdBus() + " " + transfer.getItsNameFrom() + " " + transfer.getItsNameTo() + " "
                                + transfer.getItsNameBus() + " " + transfer.getItsNameRoute());
            }
            System.out.println();
        }
    }

    public final String getItsTest()
    {
        return itsTest;
    }

    public final void setItsTest(final String aTest)
    {
        this.itsTest = aTest;
    }

    /**
     * Method to generate matrix of stops.
     */
    private void generateStopMatrix()
    {
        List<TraficStop> traficStops = DAOBusesRoutes.getTraficStops();
        int maxIdStop = 0;
        for (TraficStop traficStop : traficStops)
        {
            if (traficStop.getItsId() > maxIdStop)
            {
                maxIdStop = traficStop.getItsId();
            }
        }
        itsStopsMatrix = new ArrayList<List<Set<Integer>>>();
        for (int i = 0; i <= maxIdStop; i++)
        {
            itsStopsMatrix.add(new ArrayList<Set<Integer>>());
            for (int j = 0; j <= maxIdStop; j++)
            {
                itsStopsMatrix.get(i).add(new HashSet<Integer>());
            }
        }
        List<BusesRoute> busesRoutes = DAOBusesRoutes.getBusesRoutes();
        for (BusesRoute busesRoute : busesRoutes)
        {
            for (int i = 0; i < busesRoute.getItsStops().size() - 1; i++)
            {
                for (int j = i + 1; j < busesRoute.getItsStops().size(); j++)
                {
                    itsStopsMatrix.get(busesRoute.getItsStops().get(i).getItsId())
                            .get(busesRoute.getItsStops().get(j).getItsId()).add(busesRoute.getItsBusId());
                }
            }
        }
    }

    /**
     * Generate map of traffic stop.
     */
    private void generateMapOfTrafficstop()
    {
        List<TraficStop> list = DAOBusesRoutes.getTraficStops();
        itsTraficStop = new HashMap<Integer, TraficStop>();

        for (TraficStop traficStop : list)
        {
            itsTraficStop.put(traficStop.getItsId(), traficStop);
        }
    }

    /**
     * Generate map of bus.
     */
    private void generateMapOfBuses()
    {
        List<Bus> list = DAOBusesRoutes.getBuses();
        itsBuses = new HashMap<Integer, Bus>();

        for (Bus bus : list)
        {
            itsBuses.put(bus.getItsId(), bus);
        }
    }

    /**
     * Generate map of route.
     */
    private void generateMapOfRoutes()
    {
        List<Route> list = DAOBusesRoutes.getRoutes();
        itsRoutes = new HashMap<Integer, Route>();

        for (Route route : list)
        {
            itsRoutes.put(route.getItsId(), route);
        }
    }

    /**
     * Get route without transfers.
     * 
     * @param aFrom stop id from
     * @param aTo stop id to
     * @return list of transfer
     */
    public final List<Transfer> fidnPathWithoutTransfer(final int aFrom, final int aTo)
    {
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();
        if (itsStopsMatrix.get(aFrom).get(aTo).size() > 0)
        {
            TraficStop from = itsTraficStop.get(aFrom);
            TraficStop to = itsTraficStop.get(aTo);
            for (Integer aBusRoute : itsStopsMatrix.get(aFrom).get(aTo))
            {
                Bus bus = itsBuses.get(aBusRoute);
                Route route = itsRoutes.get(bus.getItsRouteId());
                transfers.add(new Transfer(aFrom, aTo, aBusRoute, route.getItsId(), from.getItsName(),
                        to.getItsName(), bus.getItsName(), route.getItsName()));
            }
        }
        return transfers;
    }

    /**
     * Get route with one transfer.
     * 
     * @param aFrom stop id from
     * @param aTo stop id to
     * @return list of list of transfer
     */
    public final List<List<Transfer>> findPathWithOneTransfer(final int aFrom, final int aTo)
    {
        List<List<Transfer>> lists = new ArrayList<List<Transfer>>();
        for (int i = 0; i < itsStopsMatrix.get(aFrom).size(); i++)
        {
            if (aFrom != i)
            {
                List<Transfer> trans = fidnPathWithoutTransfer(aFrom, i);
                List<Transfer> transfers;
                if (itsStopsMatrix.get(aFrom).get(i).size() > 0)
                {
                    transfers = fidnPathWithoutTransfer(i, aTo);
                    if (transfers.size() > 0)
                    {
                        for (Transfer tran : trans)
                        {
                            for (Transfer transfer : transfers)
                            {
                                if (!(tran.getItsIdBus() == transfer.getItsIdBus()
                                        || tran.getItsNameBus().equals(transfer.getItsNameBus())))
                                {
                                    List<Transfer> list = new ArrayList<Transfer>();
                                    list.add(tran);
                                    list.add(transfer);
                                    lists.add(list);
                                }
                            }
                        }
                    }
                }
            }
        }
        return lists;
    }

    /**
     * Get route with two transfer.
     * 
     * @param aFrom stop id from
     * @param aTo stop id to
     * @return list of list of transfer
     */
    public final List<List<Transfer>> findPathWithTwoTransfer(final int aFrom, final int aTo)
    {
        List<List<Transfer>> lists = new ArrayList<List<Transfer>>();
        for (int i = 0; i < itsStopsMatrix.get(aFrom).size(); i++)
        {
            List<Transfer> trans = fidnPathWithoutTransfer(aFrom, i);
            List<List<Transfer>> transfers;
            if (itsStopsMatrix.get(aFrom).get(i).size() > 0)
            {
                transfers = findPathWithOneTransfer(i, aTo);
                if (transfers.size() > 0)
                {
                    for (Transfer tran : trans)
                    {
                        for (List<Transfer> transfer : transfers)
                        {
                            List<Transfer> list = new ArrayList<Transfer>();
                            list.add(tran);
                            for (Transfer transfer2 : transfer)
                            {
                                if (!(transfer2.getItsIdBus() == tran.getItsIdBus()
                                        || transfer2.getItsNameBus().equals(tran.getItsNameBus())))
                                {
                                    list.add(transfer2);
                                }
                            }
                            if (list.size() == 3)
                            {
                                lists.add(list);
                            }
                        }
                    }
                }
            }
        }
        return lists;
    }

    /**
     * Get route with three transfer.
     * 
     * @param aFrom stop id from
     * @param aTo stop id to
     * @return list of list of transfer
     */
    public final List<List<Transfer>> findPathWithThreeTransfer(final int aFrom, final int aTo)
    {
        List<List<Transfer>> lists = new ArrayList<List<Transfer>>();
        for (int i = 0; i < itsStopsMatrix.get(aFrom).size(); i++)
        {
            List<Transfer> trans = fidnPathWithoutTransfer(aFrom, i);
            List<List<Transfer>> transfers;
            if (itsStopsMatrix.get(aFrom).get(i).size() > 0)
            {
                transfers = findPathWithTwoTransfer(i, aTo);
                if (transfers.size() > 0)
                {
                    for (Transfer tran : trans)
                    {
                        for (List<Transfer> transfer : transfers)
                        {
                            List<Transfer> list = new ArrayList<Transfer>();
                            list.add(tran);
                            for (Transfer transfer2 : transfer)
                            {
                                if (!(transfer2.getItsIdBus() == tran.getItsIdBus()
                                        || transfer2.getItsNameBus().equals(tran.getItsNameBus())))
                                {
                                    list.add(transfer2);
                                }
                            }
                            if (list.size() == 4)
                            {
                                lists.add(list);
                            }
                        }
                    }
                }
            }
        }
        return lists;
    }
}
