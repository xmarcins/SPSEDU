package sample.UnitTesty;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.java.DBConnector;

class DBConnectorTest {

    private DBConnector connector;

    @BeforeEach
    void setUp() {
        connector = new DBConnector();
    }

    @Test
    void getUcitelIDByName() {
        String ucitelName = "Daniel Cucvara";
        int ucitelId = connector.getUcitelIDByName(ucitelName);
        Assert.assertEquals(1, ucitelId);
    }

    @Test
    void getRodicIDByName() {
        String rodicName = "Sebastian Marcin";
        int rodicId = connector.getRodicIDByName(rodicName);
        Assert.assertEquals(1, rodicId);
    }

    @Test
    void getZiakIdByName() {
        String ziakName = "Dominik Borbuliak";
        int ziakId = connector.getZiakIdByName(ziakName);
        Assert.assertEquals(1, ziakId);

    }

    @Test
    void getTriedaIDByName() {
        String triedaName = "3.SB";
        int triedaId = connector.getTriedaIDByName(triedaName);
        Assert.assertEquals(1, triedaId);
    }

    @Test
    void getZiakIDByRodicID() {
        int rodicId = 1;
        int ziakId = connector.getZiakIDByRodicID(rodicId);
        Assert.assertEquals(1, ziakId);
    }
}