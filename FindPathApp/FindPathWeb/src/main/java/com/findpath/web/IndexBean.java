package com.findpath.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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
@ApplicationScoped
@ManagedBean(name = "indexB", eager = true)
public class IndexBean
{
    private final long itsMaxTime = 1800000;

    private List<List<Set<Integer>>> itsStopsMatrix;
    private Map<Integer, TraficStop> itsTraficStop;
    private Map<Integer, Bus> itsBuses;
    private Map<Integer, Route> itsRoutes;
    private List<TraficStop> itsTraficStops;

    private String itsStopFrom;
    private String itsStopTo;
    private Calendar itsCalendar;

    private List<Transfer> itsWithoutTransfer;
    private List<List<Transfer>> itsWithOneTransfer;

    /**
     * Default.
     * 
     * @throws IOException ex
     */
    public IndexBean() throws IOException
    {
        generateStopMatrix();
        generateMapOfTrafficstop();
        generateMapOfBuses();
        generateMapOfRoutes();
        setItsWithoutTransfer(new ArrayList<Transfer>());
        setItsWithOneTransfer(new ArrayList<List<Transfer>>());
        getItsWithOneTransfer().add(new ArrayList<Transfer>());

        itsCalendar = new GregorianCalendar();

        for (List<Transfer> list : findPathWithOneTransfer(1, 326, itsCalendar))
        {
            for (Transfer transfer : list)
            {
                System.out.println(transfer.getItsTimeFrom().getTime() + " " + transfer.getItsTimeTo().getTime());
                System.out.println(transfer.getItsNameFrom() + " " + transfer.getItsNameTo() + " "
                        + transfer.getItsNameBus() + " " + transfer.getItsNameRoute());
            }
            System.out.println();
        }
        /*
         * for (List<Transfer> list : findPathWithTwoTransfer(1, 326, itsCalendar)) { for (Transfer transfer : list) {
         * System.out.println(transfer.getItsTimeFrom().getTime() + " " + transfer.getItsTimeTo().getTime());
         * System.out.println( transfer.getItsIdBus() + " " + transfer.getItsNameFrom() + " " + transfer.getItsNameTo()
         * + " " + transfer.getItsNameBus() + " " + transfer.getItsNameRoute()); } System.out.println(); }
         */
    }

    public final List<TraficStop> getItsTraficStops()
    {
        return itsTraficStops;
    }

    public final void setItsTraficStops(final List<TraficStop> aTraficStops)
    {
        this.itsTraficStops = aTraficStops;
    }

    public final String getItsStopFrom()
    {
        return itsStopFrom;
    }

    public final void setItsStopFrom(final String aStopFrom)
    {
        this.itsStopFrom = aStopFrom;
    }

    public final String getItsStopTo()
    {
        return itsStopTo;
    }

    public final void setItsStopTo(final String aStopTo)
    {
        this.itsStopTo = aStopTo;
    }

    public final Calendar getItsCalendar()
    {
        return itsCalendar;
    }

    public final void setItsCalendar(final Calendar aCalendar)
    {
        this.itsCalendar = aCalendar;
    }

    public final List<Transfer> getItsWithoutTransfer()
    {
        return itsWithoutTransfer;
    }

    public final void setItsWithoutTransfer(final List<Transfer> aWithoutTransfer)
    {
        this.itsWithoutTransfer = aWithoutTransfer;
    }

    public final List<List<Transfer>> getItsWithOneTransfer()
    {
        return itsWithOneTransfer;
    }

    public final void setItsWithOneTransfer(final List<List<Transfer>> aWithOneTransfer)
    {
        this.itsWithOneTransfer = aWithOneTransfer;
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
     * @param aCalendar start time
     * @return list of transfer
     */
    public final List<Transfer> fidnPathWithoutTransfer(final int aFrom, final int aTo, final Calendar aCalendar)
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
                Transfer transfer = new Transfer(aFrom, aTo, aBusRoute, route.getItsId(), from.getItsName(),
                        to.getItsName(), bus.getItsName(), route.getItsName());
                Map<Integer, Calendar> map =
                        DAOBusesRoutes.getTravelsByBusAndStop(transfer.getItsIdBus(), transfer.getItsIdFrom());
                int travel = 0;
                for (Entry<Integer, Calendar> list : map.entrySet())
                {
                    if (list.getValue().get(aCalendar.HOUR_OF_DAY) * 100
                            + list.getValue().get(aCalendar.MINUTE) > aCalendar.get(aCalendar.HOUR_OF_DAY) * 100
                                    + aCalendar.get(aCalendar.MINUTE))
                    {
                        travel = list.getKey();
                        transfer.setItsTimeFrom(list.getValue());
                        break;
                    }
                }
                if (travel != 0)
                {
                    transfer.setItsTimeTo(DAOBusesRoutes.getTimeFromBusOnStop(transfer.getItsIdBus(),
                            transfer.getItsIdTo(), travel));
                    transfers.add(transfer);
                }
            }
        }
        int i = 0, size = transfers.size();
        while (i < size)
        {
            if (transfers.get(i).getItsTimeFrom().get(Calendar.HOUR) == 0)
            {
                transfers.remove(i);
                size--;
            }
            else
            {
                i++;
            }
        }
        return transfers;
    }

    /**
     * Get route with one transfer.
     * 
     * @param aFrom stop id from
     * @param aTo stop id to
     * @param aCalendar start time
     * @return list of list of transfer
     */
    public final List<List<Transfer>> findPathWithOneTransfer(final int aFrom, final int aTo,
            final Calendar aCalendar)
    {
        List<List<Transfer>> lists = new ArrayList<List<Transfer>>();
        for (int i = 0; i < itsStopsMatrix.get(aFrom).size(); i++)
        {
            if (aFrom != i)
            {
                List<Transfer> trans = fidnPathWithoutTransfer(aFrom, i, aCalendar);
                List<Transfer> transfers;
                if (itsStopsMatrix.get(aFrom).get(i).size() > 0)
                {
                    for (Transfer tran : trans)
                    {
                        transfers = fidnPathWithoutTransfer(i, aTo, tran.getItsTimeTo());
                        if (transfers.size() > 0)
                        {
                            for (Transfer transfer : transfers)
                            {
                                if (!(tran.getItsIdBus() == transfer.getItsIdBus()
                                        || tran.getItsNameBus().equals(transfer.getItsNameBus()))
                                        && (transfer.getItsTimeFrom().getTimeInMillis()
                                                - tran.getItsTimeTo().getTimeInMillis()) < itsMaxTime)
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
     * @param aCalendar start time
     * @return list of list of transfer
     */
    public final List<List<Transfer>> findPathWithTwoTransfer(final int aFrom, final int aTo,
            final Calendar aCalendar)
    {
        List<List<Transfer>> lists = new ArrayList<List<Transfer>>();
        for (int i = 0; i < itsStopsMatrix.get(aFrom).size(); i++)
        {
            List<Transfer> trans = fidnPathWithoutTransfer(aFrom, i, aCalendar);
            List<List<Transfer>> transfers;
            if (itsStopsMatrix.get(aFrom).get(i).size() > 0)
            {
                for (Transfer tran : trans)
                {
                    transfers = findPathWithOneTransfer(i, aTo, tran.getItsTimeTo());
                    if (transfers.size() > 0)
                    {
                        for (List<Transfer> transfer : transfers)
                        {
                            if (transfer.get(0).getItsTimeFrom().getTimeInMillis()
                                    - tran.getItsTimeTo().getTimeInMillis() < itsMaxTime)
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
    public final List<List<Transfer>> findPathWithThreeTransfer(final int aFrom, final int aTo,
            final Calendar aCalendar)
    {
        List<List<Transfer>> lists = new ArrayList<List<Transfer>>();
        for (int i = 0; i < itsStopsMatrix.get(aFrom).size(); i++)
        {
            List<Transfer> trans = fidnPathWithoutTransfer(aFrom, i, aCalendar);
            List<List<Transfer>> transfers;
            if (itsStopsMatrix.get(aFrom).get(i).size() > 0)
            {
                for (Transfer tran : trans)
                {
                    transfers = findPathWithTwoTransfer(i, aTo, tran.getItsTimeTo());
                    if (transfers.size() > 0)
                    {
                        for (List<Transfer> transfer : transfers)
                        {
                            if (transfer.get(0).getItsTimeFrom().getTimeInMillis()
                                    - tran.getItsTimeTo().getTimeInMillis() < itsMaxTime)
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
        }
        return lists;
    }

    public final void findPath()
    {
        List<Integer> listFrom = DAOBusesRoutes.getStopsByName(getItsStopFrom());
        List<Integer> listTo = DAOBusesRoutes.getStopsByName(getItsStopTo());

        getItsWithoutTransfer().clear();
        getItsWithOneTransfer().clear();

        for (Integer from : listFrom)
        {
            for (Integer to : listTo)
            {
                List<Transfer> withoutTransferTemp = fidnPathWithoutTransfer(from, to, itsCalendar);
                getItsWithoutTransfer().addAll(withoutTransferTemp);

                List<List<Transfer>> withOneTransfer = findPathWithOneTransfer(from, to, itsCalendar);
                getItsWithOneTransfer().addAll(withOneTransfer);
            }
        }
    }
}
