package sample.UnitTesty;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.java.PrihlasenieController;

import static org.junit.jupiter.api.Assertions.*;

class PrihlasenieControllerTest {

    PrihlasenieController controller;

    @BeforeEach
    void setUp() {
        controller = new PrihlasenieController();
    }

    @Test
    void isAdmin() {
        String meno = "Admin";
        String heslo = "password";
        boolean isAdmin = controller.isAdmin(meno, heslo);
        Assert.assertTrue(isAdmin);
    }
}