package by.openbanking.openbankingservice.dbconnector;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connector and low level functions
 *
 * @author Mario Pascucci
 */
public class DBConnector {

    // database vars
    Connection conn;
    boolean inited = false;
    boolean needUpgrade = false;
    private final String DUMMYTABLE = "dbcdummy";
    private final String DUMMYFIELD = "sometext";
    private final String dbname = "";
    private final String dbuser = "wso2carbon";
    private final String dbpass = "wso2carbon";


    /**
     * @param dbname
     * @param dbuser
     * @param dboptions
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public DBConnector(String dbname, String dbuser, String dboptions) throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        this.conn = DriverManager.getConnection("jdbc:h2:" + dbname +
                ";FILE_LOCK=SOCKET;TRACE_MAX_FILE_SIZE=1;" + dboptions, dbuser, "");

        inited = initDB();
    }


    /**
     * Reads database version constant
     *
     * @param dbver constant name
     * @return version ID as integer or -1 if dbver was not found
     */
    public int getDbVersion(String dbver) {

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT " + dbver);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }


    /**
     * Startup operations for database <br/>
     * Initialize full text engine using Lucene for H2 database
     *
     * @return true if database is ready
     * @throws SQLException if fails
     */
    private boolean initDB() throws SQLException {

        Statement st;
        boolean inited = false;

        st = conn.createStatement();

        // enables full text search with Apache Lucene
        st.execute("CREATE ALIAS IF NOT EXISTS FTL_INIT FOR \"org.h2.fulltext.FullTextLucene.init\";" +
                "CALL FTL_INIT()");

        try {
            // remove any previous table
            st.execute("DROP TABLE IF EXISTS " + DUMMYTABLE);
            // create a dummy table
            st.execute("CREATE TABLE IF NOT EXISTS " + DUMMYTABLE + " (id INT PRIMARY KEY AUTO_INCREMENT, " + DUMMYFIELD + " VARCHAR(32))");
            // create a dummy FTS index
            createFTS(DUMMYTABLE, DUMMYFIELD);
            // this causes a complete FTS index rebuild on all tables that have FTS index, as stated in H2 online manual:
            // http://h2database.com/html/tutorial.html#fulltext par. "Using the Apache Lucene Fulltext Search"
            deleteFTS(DUMMYTABLE);
            st.execute("DROP TABLE IF EXISTS " + DUMMYTABLE);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "[initDB] Unable to refresh full text index.", e);
        }

        inited = true;

        return inited;
    }


    /**
     * Defines database version for tables and data
     *
     * @param dbver   constant name for version storage
     * @param version defines version ID
     * @throws SQLException if fails
     */
    public void setDbVersion(String dbver, int version) throws SQLException {

        Statement st = conn.createStatement();
        st.execute("DROP CONSTANT IF EXISTS " + dbver);
        // replace constant
        st.execute("CREATE CONSTANT IF NOT EXISTS " + dbver + " VALUE " + version);
    }


    public boolean isInited() {
        return inited;
    }


    /**
     * Checks if your db is obsolete
     *
     * @param dbver         db version name
     * @param neededVersion version you need
     * @return true if db needs upgrade
     */
    public boolean needsUpgrade(String dbver, int neededVersion) {

        if (getDbVersion(dbver) < neededVersion) {
            // db is obsolete, we need an update
            needUpgrade = true;
        }
        return needUpgrade;
    }


    /**
     * @param sql
     * @return
     * @throws SQLException
     * @see java.sql.Connection#prepareStatement(java.lang.String)
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }


    /**
     * Checks if a table exists.
     *
     * @param tableName table to check
     * @return true if tableName exists
     */
    public boolean checkTable(String tableName) {

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT count(*) as res FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA='PUBLIC' " +
                    " AND TABLE_NAME='" + tableName.toUpperCase() + "'");
            rs.next();
            return rs.getInt(1) == 1;
        } catch (SQLException e) {
            return false;
        }

    }


    /**
     * @param sql
     * @param autoGeneratedKeys
     * @return
     * @throws SQLException
     * @see java.sql.Connection#prepareStatement(java.lang.String, int)
     */
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        return conn.prepareStatement(sql, autoGeneratedKeys);
    }


    /**
     * Checks if a table have a full text search index
     *
     * @param tableName table to check
     * @param fields    list of fields to index (comma delimited, no whitespaces)
     * @return true if table is indexed via FTS
     */
    public boolean checkFTS(String tableName, String fields) {

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT COLUMNS FROM FTL.INDEXES WHERE " +
                    "SCHEMA='PUBLIC' " +
                    " AND TABLE='" + tableName.toUpperCase() + "'");
            rs.next();
            return rs.getString(1).equalsIgnoreCase(fields);
        } catch (SQLException e) {
            return false;
        }
    }


    /**
     * @return
     * @throws SQLException
     * @see java.sql.Connection#createStatement()
     */
    public Statement createStatement() throws SQLException {
        return conn.createStatement();
    }


    /**
     * Creates Full text search index
     * Deletes any existing index for same table
     *
     * @param table  table name
     * @param fields list of field names to index (comma delimited)
     * @throws SQLException if unable to create index
     */
    public void createFTS(String table, String fields) throws SQLException {

        Statement st;

        deleteFTS(table);
        st = conn.createStatement();
        st.execute("CALL FTL_CREATE_INDEX('PUBLIC','" + table.toUpperCase() + "','" + fields.toUpperCase() + "')");

    }


    /**
     * Deletes any full text index for table
     *
     * @param table index table name to delete
     */
    public void deleteFTS(String table) {

        Statement st;

        try {
            st = conn.createStatement();
            // drop old index, if exists
            st.execute("CALL FTL_DROP_INDEX('PUBLIC','" + table.toUpperCase() + "')");
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.WARNING, "[deleteFTS] Error in delete fulltext search index, table: " + table, e);
        }
    }


    /**
     * Disable autocommit, if supported by DB <br/>
     * NB: transactions are isolated at thread level: so if you disable autocommit on a thread,
     * all other thread can't lock tables for modify, and you get a "timeout lock error"
     *
     * @throws SQLException
     */
    public void autocommitDisable() throws SQLException {

        Statement st;

        st = conn.createStatement();
        st.execute("SET AUTOCOMMIT OFF");
    }


    /**
     * enable autocommit, if supported by DB
     *
     * @throws SQLException
     */
    public void autocommitEnable() throws SQLException {

        Statement st;

        st = conn.createStatement();
        st.execute("SET AUTOCOMMIT ON");
    }


    /**
     * rollbacks a transaction
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {

        Statement st;

        st = conn.createStatement();
        st.execute("ROLLBACK");
    }


    /**
     * Commit pending transaction
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {

        Statement st;

        st = conn.createStatement();
        st.execute("COMMIT");
    }

}