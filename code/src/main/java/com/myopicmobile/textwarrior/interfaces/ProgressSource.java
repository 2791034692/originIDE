package com.myopicmobile.textwarrior.interfaces;

public interface ProgressSource {
    /* Nature of computation tasks */
    static final public int NONE = 0;
    static final public int READ = 1;
    static final public int WRITE = 2;
    static final public int FIND = 4;
    static final public int FIND_BACKWARDS = 8;
    static final public int REPLACE_ALL = 16;
    static final public int ANALYZE_TEXT = 32;
    /* Error codes */
    static final public int ERROR_UNKNOWN = 0;
    static final public int ERROR_OUT_OF_MEMORY = 1;
    static final public int ERROR_INDEX_OUT_OF_RANGE = 2;

    /**
     * Minimum progress value
     */
    public abstract int getMin();

    /**
     * Maximum progress value
     */
    public abstract int getMax();

    /**
     * Current progress value
     */
    public abstract int getCurrent();

    /**
     * Whether computation is done
     */
    public abstract boolean isDone();

    /**
     * Aborts computation
     */
    public abstract void forceStop();

    /**
     * Registers observers that will be informed of changes to the progress state
     */
    public abstract void registerObserver(ProgressObserver obsv);

    /**
     * Removes all attached observers
     */
    public abstract void removeObservers();
}
