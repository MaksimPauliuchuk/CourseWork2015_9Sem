package com.findpath.interfaces.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Transfer object.
 * 
 * @author Максим
 */
public class Transfer
{
    private int itsIdFrom;
    private int itsIdTo;
    private int itsIdBus;
    private int itsIdRoute;
    private String itsNameFrom;
    private String itsNameTo;
    private String itsNameBus;
    private String itsNameRoute;
    private Calendar itsTimeFrom;
    private Calendar itsTimeTo;

    /**
     * Create transfer.
     * 
     * @param aFrom id from
     * @param aTo id to
     * @param aBus id bus
     * @param anIdRoute id route
     * @param aNameFrom name stop from
     * @param aNameTo name stop to
     * @param aNameBus name bus
     * @param aNameRoute name route
     */
    public Transfer(final int aFrom, final int aTo, final int aBus, final int anIdRoute, final String aNameFrom,
            final String aNameTo, final String aNameBus, final String aNameRoute)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        setItsIdFrom(aFrom);
        setItsIdTo(aTo);
        setItsIdBus(aBus);
        setItsIdRoute(anIdRoute);
        setItsNameFrom(aNameFrom);
        setItsNameTo(aNameTo);
        setItsNameBus(aNameBus);
        setItsNameRoute(aNameRoute);
        try
        {
            setItsTimeFrom(Calendar.getInstance());
            getItsTimeFrom().setTime(sdf.parse("1970:01:01:00:00:00"));
            setItsTimeTo(Calendar.getInstance());
            getItsTimeTo().setTime(sdf.parse("1970:01:01:00:00:00"));
        }
        catch (ParseException e)
        {
            System.out.println("Error in Transfer constructor on converte date");
            e.printStackTrace();
        }
    }

    public final int getItsIdFrom()
    {
        return itsIdFrom;
    }

    public final void setItsIdFrom(final int anIdFrom)
    {
        this.itsIdFrom = anIdFrom;
    }

    public final int getItsIdTo()
    {
        return itsIdTo;
    }

    public final void setItsIdTo(final int anIdTo)
    {
        this.itsIdTo = anIdTo;
    }

    public final int getItsIdBus()
    {
        return itsIdBus;
    }

    public final void setItsIdBus(final int anIdBus)
    {
        this.itsIdBus = anIdBus;
    }

    public final int getItsIdRoute()
    {
        return itsIdRoute;
    }

    public final void setItsIdRoute(final int anIdRoute)
    {
        this.itsIdRoute = anIdRoute;
    }

    public final String getItsNameFrom()
    {
        return itsNameFrom;
    }

    public final void setItsNameFrom(final String aNameFrom)
    {
        this.itsNameFrom = aNameFrom;
    }

    public final String getItsNameTo()
    {
        return itsNameTo;
    }

    public final void setItsNameTo(final String aNameTo)
    {
        this.itsNameTo = aNameTo;
    }

    public final String getItsNameBus()
    {
        return itsNameBus;
    }

    public final void setItsNameBus(final String aNameBus)
    {
        this.itsNameBus = aNameBus;
    }

    public final String getItsNameRoute()
    {
        return itsNameRoute;
    }

    public final void setItsNameRoute(final String aNameRoute)
    {
        this.itsNameRoute = aNameRoute;
    }

    public final Calendar getItsTimeFrom()
    {
        return itsTimeFrom;
    }

    public final void setItsTimeFrom(final Calendar aTimeFrom)
    {
        this.itsTimeFrom = aTimeFrom;
    }

    public final Calendar getItsTimeTo()
    {
        return itsTimeTo;
    }

    public final void setItsTimeTo(final Calendar aTimeTo)
    {
        this.itsTimeTo = aTimeTo;
    }
}
