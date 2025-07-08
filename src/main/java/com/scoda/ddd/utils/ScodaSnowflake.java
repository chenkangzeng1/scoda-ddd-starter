package com.scoda.ddd.utils;

/**
 * Snowflake ID generator utility class.
 * Generates unique, time-ordered IDs for distributed systems.
 * 
 * @author kangzeng.ckz
 * @since 2024/10/29
 */
public class ScodaSnowflake {

    private final long twepoch = 1751544514482L;

    // Bit allocation for each part
    private final long workerBits = 5L;
    private final long datacenterBits = 5L;
    private final long maxWorkerId = ~(-1L << workerBits); // 31
    private final long maxDatacenterId = ~(-1L << datacenterBits); // 31

    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerBits;
    private final long timestampLeftShift = sequenceBits + workerBits + datacenterBits;
    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long workerId;
    private final long datacenterId;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    /**
     * Constructor
     *
     * @param workerId     Worker node ID (0~31)
     * @param datacenterId Data center ID (0~31)
     */
    public ScodaSnowflake(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("workerId out of range");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId out of range");
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * Get the next unique ID
     * 
     * @return the next unique ID
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            // Clock rollback handling: wait until time catches up
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) { // Tolerate rollback within 5ms
                try {
                    Thread.sleep(offset);
                    timestamp = System.currentTimeMillis();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException("Clock still rolling back, please check server time synchronization");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Clock rollback interrupted", e);
                }
            } else {
                throw new RuntimeException("Clock rollback exceeds tolerance range: " + offset + "ms");
            }
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return (timestamp - twepoch) << timestampLeftShift //
                | datacenterId << datacenterIdShift //
                | workerId << workerIdShift //
                | sequence;
    }

    /**
     * Wait for the next millisecond in a loop
     * 
     * @param lastTimestamp the last timestamp
     * @return the next timestamp
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * Return base36 encoded string ID for shorter display
     * 
     * @return base36 encoded string ID
     */
    public String nextIdStr() {
        return Long.toString(nextId(), 36);
    }

    /**
     * Generate event ID with prefix (e.g., eid-x8z1a)
     * 
     * @return event ID with prefix
     */
    public String genEventUid() {
        return "eid-" + nextIdStr();
    }

    // ------------------ Test entry point ------------------

    /**
     * Test entry point for generating sample IDs.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ScodaSnowflake snowflake = new ScodaSnowflake(1, 1);

        for (int i = 0; i < 10; i++) {
            System.out.println(snowflake.genEventUid());
        }
    }
}