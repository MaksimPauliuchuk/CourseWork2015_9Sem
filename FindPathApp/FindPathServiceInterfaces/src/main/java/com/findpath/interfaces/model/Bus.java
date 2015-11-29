package com.findpath.interfaces.model;

/**
 * Bus.
 * 
 * @author Максим
 */
public class Bus
{
    private int itsId;
    private String itsName;
    private int itsRouteId;

    /**
     * Default.
     * 
     * @param anId id
     * @param aName name
     * @param aRouteId route id
     */
    public Bus(final int anId, final String aName, final int aRouteId)
    {
        setItsId(anId);
        setItsName(aName);
        setItsRouteId(aRouteId);
    }

    public final int getItsId()
    {
        return itsId;
    }

    public final void setItsId(final int anId)
    {
        this.itsId = anId;
    }

    public final String getItsName()
    {
        return itsName;
    }

    public final void setItsName(final String aName)
    {
        this.itsName = aName;
    }

    public final int getItsRouteId()
    {
        return itsRouteId;
    }

    public final void setItsRouteId(final int aRouteId)
    {
        this.itsRouteId = aRouteId;
    }
}
