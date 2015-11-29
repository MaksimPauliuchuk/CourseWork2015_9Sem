package com.findpath.interfaces.model;

import java.util.List;

/**
 * Buses route.
 * 
 * @author Максим
 */
public class BusesRoute
{
    private int itsId;
    private List<TraficStop> itsStops;
    private int itsBusId;

    /**
     * Default.
     * 
     * @param anId id
     * @param aStops list stops
     * @param aBusId bus id
     */
    public BusesRoute(final int anId, final List<TraficStop> aStops, final int aBusId)
    {
        setItsBusId(aBusId);
        setItsId(anId);
        setItsStops(aStops);
    }

    public final int getItsId()
    {
        return itsId;
    }

    public final void setItsId(final int anId)
    {
        this.itsId = anId;
    }

    public final List<TraficStop> getItsStops()
    {
        return itsStops;
    }

    public final void setItsStops(final List<TraficStop> aStops)
    {
        this.itsStops = aStops;
    }

    public final int getItsBusId()
    {
        return itsBusId;
    }

    public final void setItsBusId(final int aBusId)
    {
        this.itsBusId = aBusId;
    }
}
