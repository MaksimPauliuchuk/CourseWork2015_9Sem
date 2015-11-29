package com.findpath.interfaces.model;

/**
 * Route.
 * 
 * @author Максим
 */
public class Route
{
    private int itsId;
    private String itsName;

    /**
     * Default.
     * 
     * @param anId id
     * @param aName name
     */
    public Route(final int anId, final String aName)
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
