package com.findpath.interfaces.model;

/**
 * Trafic stop.
 * 
 * @author Максим
 */
public class TraficStop
{
    private int itsId;
    private String itsName;

    /**
     * Default.
     * 
     * @param anId id
     * @param aName name
     */
    public TraficStop(final int anId, final String aName)
    {
        setItsId(anId);
        setItsName(aName);
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

}
